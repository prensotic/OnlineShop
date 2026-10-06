package ru.loginov.dev.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.loginov.dev.entities.Category;
import ru.loginov.dev.entities.Manufacturer;
import ru.loginov.dev.entities.Product;
import ru.loginov.dev.exceptions.productExceptions.ProductNotFoundExceptions;
import ru.loginov.dev.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Transactional
    public List<Product> getAllProducts(){
        return productRepository.getAllProducts();
    }

    @Transactional
    public List<Product> getProductsByCategoryId(long categoryId){
        if(categoryId <= 0) throw new IllegalArgumentException("ID категории должен быть положительным");
        return productRepository.getProductsByCategoryId(categoryId);
    }

    @Transactional
    public List<Product> getProductsByManufacturerId(long manufacturerId){
        if(manufacturerId <= 0) throw new IllegalArgumentException("ID производителя должен быть положительным");
        return productRepository.getProductsByManufacturerId(manufacturerId);
    }

    @Transactional
    public Product findProductById(long id){
        if(id <= 0) throw new IllegalArgumentException("ID должен быть положительным");

        Product product = productRepository.findProductById(id);

        if(product == null) throw new ProductNotFoundExceptions("Товар с ID: " + id + " не найден");

        return product;
    }

    @Transactional
    public List<Product> findProductsByName(String productName){
        if(productName == null || productName.isBlank())
            throw new IllegalArgumentException("Название товара указано некорректно");

        return productRepository.findProductsByName(productName);
    }

    @Transactional
    public void createProduct(String productName, String productDescription, Category category,
                              Manufacturer manufacturer, BigDecimal price, String unit, int quantity){
        if(productName == null || productName.isBlank())
            throw new IllegalArgumentException("Название товара указано некорректно");

        if(productDescription == null || productDescription.isBlank())
            throw new IllegalArgumentException("Описание товара указано некорректно");

        if(category == null)
            throw new IllegalArgumentException("Категория товара указана некорректно");

        if(manufacturer == null)
            throw new IllegalArgumentException("Производитель товара указан некорректно");

        if(price == null || price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Цена товара указана некорректно");

        if(unit == null || unit.isBlank())
            throw new IllegalArgumentException("Единицы товара указаны некорректно");

        if(quantity <= 0)
            throw new IllegalArgumentException("Количество товара указано некорректно");

        productRepository
                .saveProduct(
                        new Product(productName, productDescription, category, manufacturer, price, unit, quantity));
    }

    @Transactional
    public void updateProduct(long id, String productName, String productDescription, Category category,
                              Manufacturer manufacturer, BigDecimal price, String unit, int quantity, boolean isActive){
        Product product = findProductById(id);

        if(productName == null || productName.isBlank())
            throw new IllegalArgumentException("Название товара указано некорректно");

        if(productDescription == null || productDescription.isBlank())
            throw new IllegalArgumentException("Описание товара указано некорректно");

        if(category == null)
            throw new IllegalArgumentException("Категория товара указана некорректно");

        if(manufacturer == null)
            throw new IllegalArgumentException("Производитель товара указан некорректно");

        if(price == null || price.doubleValue() <= 0)
            throw new IllegalArgumentException("Цена товара указана некорректно");

        if(unit == null || unit.isBlank())
            throw new IllegalArgumentException("Единицы товара указаны некорректно");

        if(quantity <= 0)
            throw new IllegalArgumentException("Количество товара указано некорректно");

        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setCategory(category);
        product.setManufacturer(manufacturer);
        product.setPrice(price);
        product.setUnit(unit);
        product.setQuantity(quantity);
        product.setActive(isActive);
    }

    @Transactional
    public void deleteProduct(long id){
        findProductById(id);
        productRepository.deleteProduct(id);
    }
}