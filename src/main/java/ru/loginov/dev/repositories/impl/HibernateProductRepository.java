package ru.loginov.dev.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.loginov.dev.entities.Product;
import ru.loginov.dev.repositories.ProductRepository;

import java.util.List;

@Repository
public class HibernateProductRepository implements ProductRepository {
    private final SessionFactory sessionFactory;

    public HibernateProductRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Product> getAllProducts() {
        return sessionFactory.getCurrentSession().createQuery("select p from Product p", Product.class).getResultList();
    }

    @Override
    public List<Product> getProductsByCategoryId(long categoryId) {
        return sessionFactory.getCurrentSession().createQuery("select p from Product p where p.category.id = :categoryId", Product.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public List<Product> getProductsByManufacturerId(long manufacturerId) {
        return sessionFactory.getCurrentSession().createQuery("select p from Product p where p.manufacturer.id = :manufacturerId", Product.class)
                .setParameter("manufacturerId", manufacturerId)
                .getResultList();
    }

    @Override
    public Product findProductById(long id) {
        return sessionFactory.getCurrentSession().get(Product.class, id);
    }

    @Override
    public Product findProductByName(String productName) {
        return sessionFactory.getCurrentSession().createQuery("select p from Product p where p.productName = :productName", Product.class)
                .setParameter("productName", productName)
                .uniqueResult();
    }

    @Override
    public void saveProduct(Product product) {
        sessionFactory.getCurrentSession().persist(product);
    }

    @Override
    public void updateProduct(Product product) {
        sessionFactory.getCurrentSession().merge(product);
    }

    @Override
    public void deleteProduct(long id) {
        Product product = findProductById(id);
        if(product != null) sessionFactory.getCurrentSession().remove(product);
    }
}
