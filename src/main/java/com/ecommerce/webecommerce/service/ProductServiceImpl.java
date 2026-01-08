package com.ecommerce.webecommerce.service;

import com.ecommerce.webecommerce.common.errors.ResourceNotFoundException;
import com.ecommerce.webecommerce.entity.Category;
import com.ecommerce.webecommerce.entity.Product;
import com.ecommerce.webecommerce.entity.ProductCategory;
import com.ecommerce.webecommerce.model.CategoryResponse;
import com.ecommerce.webecommerce.model.ProductRequest;
import com.ecommerce.webecommerce.model.ProductResponse;
import com.ecommerce.webecommerce.repository.CategoryRepository;
import com.ecommerce.webecommerce.repository.ProductCategoryRepository;
import com.ecommerce.webecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream().map(product -> {
                    List<CategoryResponse> productCategories = getProductCategories(product.getProductId());
                    return ProductResponse.fromProductAndCategories(product, productCategories);
                })
                .toList();
    }

    @Override
    public ProductResponse findById(Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        List<CategoryResponse> productCategories = getProductCategories(productId);
        return ProductResponse.fromProductAndCategories(existingProduct, productCategories);
    }


    @Override
    @Transactional
    public ProductResponse create(ProductRequest productRequest) {
        List<Category> categories = getCategoriesByIds(productRequest.getCategoryIds());

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .weight(productRequest.getWeight())
                .build();

        Product createdProduct = productRepository.save(product);
        List< ProductCategory> productCategories = categories.stream()
                .map(category -> {
                    ProductCategory productCategory = ProductCategory.builder().build();
                    ProductCategory.ProductCategoryId productCategoryId = new ProductCategory.ProductCategoryId();
                    productCategoryId.setCategoryId(category.getCategoryId());
                    productCategoryId.setProductId(createdProduct.getProductId());
                    productCategory.setId(productCategoryId);
                    return productCategory;
                })
                .toList();

        productCategoryRepository.saveAll(productCategories);

        List< CategoryResponse> categoryResponseList = categories.stream().map(
                        CategoryResponse::fromCategory)
                .toList();

        return ProductResponse.fromProductAndCategories(createdProduct, categoryResponseList);
    }

    @Override
    @Transactional
    public ProductResponse update(Long productId, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        List<Category> categories = getCategoriesByIds(productRequest.getCategoryIds());

        existingProduct.setProductId(productId);
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setStockQuantity(productRequest.getStockQuantity());
        existingProduct.setWeight(productRequest.getWeight());
        productRepository.save(existingProduct);

        List<ProductCategory> existingProductCategories = productCategoryRepository.findCategoriesByProductId(productId);
        productCategoryRepository.deleteAll(existingProductCategories);

        List<ProductCategory> productCategories = categories.stream()
                .map(category -> {
                    ProductCategory productCategory = ProductCategory.builder().build();
                    ProductCategory.ProductCategoryId productCategoryId = new ProductCategory.ProductCategoryId();
                    productCategoryId.setCategoryId(category.getCategoryId());
                    productCategoryId.setProductId(productId);
                    productCategory.setId(productCategoryId);
                    return productCategory;
                })
                .toList();

        productCategoryRepository.saveAll(productCategories);

        List<CategoryResponse> categoryResponseList = categories.stream().map(
                        CategoryResponse::fromCategory)
                .toList();

        return ProductResponse.fromProductAndCategories(existingProduct, categoryResponseList);

    }

    @Override
    public void delete(Long id) {

    }

    private List<Category> getCategoriesByIds(List<Long> categoryIds) {
        return categoryIds.stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found for id : "+categoryId)))
                .toList();
    }

    private List<CategoryResponse> getProductCategories(Long productId) {
        List<ProductCategory> productCategories = productCategoryRepository.findCategoriesByProductId(productId);
        List<Long> categoryIds = productCategories.stream()
                .map(productCategory -> productCategory.getId().getCategoryId())
                .toList();
        return categoryRepository.findAllById(categoryIds)
                .stream().map(CategoryResponse::fromCategory)
                .toList();
    }
}
