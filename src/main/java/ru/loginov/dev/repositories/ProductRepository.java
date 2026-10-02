package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
    List<Product> getProductsByCategoryId(long categoryId);
    List<Product> getProductsByManufacturerId(long manufacturerId);
    Product findProductById(long id);
    Product findProductByName(String productName);
    void saveProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(long id);
}
