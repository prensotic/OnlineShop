package ru.loginov.dev.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.loginov.dev.entities.Category;
import ru.loginov.dev.repositories.CategoryRepository;

import java.util.List;

@Repository
public class HibernateCategoryRepository implements CategoryRepository {
    public final SessionFactory sessionFactory;

    public HibernateCategoryRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Category> getAllCategories() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from Category c", Category.class).list();
    }

    @Override
    public Category findCategoryById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Category.class, id);
    }

    @Override
    public Category findCategoryByName(String categoryName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from Category c where c.categoryName = :categoryName", Category.class)
                .setParameter("categoryName", categoryName)
                .getSingleResult();
    }

    @Override
    public void saveCategory(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(category);
    }

    @Override
    public void updateCategory(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(category);
    }

    @Override
    public void deleteCategory(long id) {
        Session session = sessionFactory.getCurrentSession();
        Category category = findCategoryById(id);
        if(category != null) session.remove(category);
    }
}
