package com.example.demo.servise;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductImage;
import com.example.demo.entity.create.CreateProduct;
import com.example.demo.entity.dto.ProductDto;
import com.example.demo.entity.update.UpdateProduct;
import com.example.demo.excaption.BadRequest;
import com.example.demo.repo.ProductImageRepo;
import com.example.demo.repo.ProductRepo;
import com.example.demo.type.ProductStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class ProductService {
    private final ProductRepo productRepo;
    private final ImageService imageService;
    private final ProfileService profileService;
    private final ProductImageRepo productImageRepo;

    public ProductService(ProductRepo productRepo, ImageService imageService, ProfileService profileService, ProductImageRepo productImageRepo) {
        this.productRepo = productRepo;
        this.imageService = imageService;
        this.profileService = profileService;
        this.productImageRepo = productImageRepo;
    }
public Boolean existProduct(Integer id){
       Optional<Product>optionalProduct = productRepo.findById(id);
       if (optionalProduct.isEmpty()){
           throw new BadRequest("Product not found");
       }
       else return optionalProduct.get().getProductStatus().equals(ProductStatus.PUBLISHED);
}
    public ProductDto create(CreateProduct createProduct) {
            Product product = new Product();
        product.setCreateAt(LocalDateTime.now());
        product.setProductStatus(ProductStatus.CREATED);
        product.setName(createProduct.getName());
        product.setDescription(createProduct.getDescription());
        product.setPrice(createProduct.getPrice());
        product.setProductType(createProduct.getProductType());
        productRepo.save(product);
        return returnDto(product);
    }

    private ProductDto returnDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setDescription(product.getDescription());
        dto.setName(product.getName());
        dto.setRate(product.getRate());
        dto.setPrice(product.getPrice());
        dto.setProductType(product.getProductType());
        return dto;
    }

    public Object getAll() {
        List<ProductDto>dtos = new ArrayList<>();
        List<Product> products = productRepo.findProductByVisibleIsTrue();
        for (Product p:products) {
            if (p.getProductStatus().equals(ProductStatus.PUBLISHED)){
                dtos.add(returnDto(p));
            }
        }
        return dtos;
    }
    private Product getProduct(Integer id){
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()){
            throw new BadRequest("Product not found!");
        }
        return product.get();
    }
    public Object update(Integer id, UpdateProduct product) {
        Product oldProduct = getProduct(id);
        if (product.getName() != null || !product.getName().isEmpty()){
            oldProduct.setName(product.getName());
        }
        if (!product.getDescription().isEmpty()){
            oldProduct.setDescription(product.getDescription());
        }
        if (product.getPrice() != null) {
            oldProduct.setPrice(product.getPrice());
        }
        if (product.getRate() != null){
            oldProduct.setRate(product.getRate());
        }
        if (product.getVisible() != null){
            oldProduct.setVisible(product.getVisible());
        }
        if (product.getProductType() != null){
            oldProduct.setProductType(product.getProductType());
        }
        if (product.getProductStatus() != null){
            oldProduct.setProductStatus(product.getProductStatus());
        }
        productRepo.save(oldProduct);
        return returnDto(oldProduct);
    }
    public Object getBuId(Integer id) {
        return returnDto(getProduct(id));
    }

    public Object getAllAdmin() {
        if (profileService.isAdmin()){
            return productRepo.findAll();
        }
        throw new BadRequest("Not found");
    }

    public Object visible(Integer id) {
        if (!profileService.isAdmin()){
            throw new BadRequest("Not found :(");
        }
        Product product = getProduct(id);
        product.setProductStatus(ProductStatus.PUBLISHED);
        product.setVisible(true);
        productRepo.save(product);
        return product;
    }
    public Object hidden(Integer id) {
        if (!profileService.isAdmin()){
            throw new BadRequest("Not found :(");
        }
        Product product = getProduct(id);
        product.setProductStatus(ProductStatus.BLOCKED);
        product.setVisible(false);
        productRepo.save(product);
        return product;
    }

    public ProductImage saveImage(MultipartFile file, Integer productId) {
        getProduct(productId);
        Integer imageId = imageService.create(file);
        ProductImage productImage = new ProductImage();
        productImage.setProductId(productId);
        productImage.setImageId(imageId);
        productImageRepo.save(productImage);
        return productImageRepo.findByProductIdAndImageId(productId,imageId);
    }
}
