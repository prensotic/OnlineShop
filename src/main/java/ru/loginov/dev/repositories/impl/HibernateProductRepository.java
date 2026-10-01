package ru.loginov.dev.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.loginov.dev.entities.Product;
import ru.loginov.dev.repositories.ProductRepository;

import java.util.List;

@Repository
public class HibernateProductRepository implements ProductRepository {
    public SessionFactory sessionFactory;

    public HibernateProductRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Product> getAllProducts() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Product p", Product.class).list();
    }

    @Override
    public List<Product> getProductsByCategoryId(long categoryId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Product p where p.category.id = :categoryId", Product.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public List<Product> getProductsByManufacturerId(long manufacturerId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Product p where p.manufacturer.id = :manufacturerId", Product.class)
                .setParameter("manufacturerId", manufacturerId)
                .getResultList();
    }

    @Override
    public Product findProductById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Product.class, id);
    }

    @Override
    public Product findProductByName(String productName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Product p where p.productName = :productName", Product.class)
                .setParameter("productName", productName)
                .getSingleResult();
    }

    @Override
    public void saveProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(product);
    }

    @Override
    public void updateProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(product);
    }

    @Override
    public void deleteProduct(long id) {
        Session session = sessionFactory.getCurrentSession();
        Product product = findProductById(id);
        if(product != null) session.remove(product);
    }
}
