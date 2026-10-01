package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.Product;

import java.util.List;

public interface ProductRepository {
    public List<Product> getAllProducts();
    public List<Product> getProductsByCategoryId(long categoryId);
    public List<Product> getProductsByManufacturerId(long manufacturerId);
    public Product findProductById(long id);
    public Product findProductByName(String productName);
    public void saveProduct(Product product);
    public void updateProduct(Product product);
    public void deleteProduct(long id);
}
