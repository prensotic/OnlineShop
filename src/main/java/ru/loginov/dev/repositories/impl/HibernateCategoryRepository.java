package ru.loginov.dev.repositories.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.loginov.dev.entities.Category;
import ru.loginov.dev.repositories.CategoryRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class HibernateCategoryRepository implements CategoryRepository {
    private final SessionFactory sessionFactory;

    public HibernateCategoryRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Category> getAllCategories() {
        return sessionFactory
                        .getCurrentSession()
                        .createQuery("FROM Category", Category.class)
                        .getResultList();
    }

    @Override
    public Category findCategoryById(long id) {
        return sessionFactory.getCurrentSession().get(Category.class, id);
    }

    @Override
    public Category findCategoryByName(String categoryName) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("select c from Category c where c.categoryName = :categoryName", Category.class)
                .setParameter("categoryName", categoryName)
                .uniqueResult();
    }

    @Override
    public boolean existsByName(String categoryName) {
        Long count = sessionFactory
                .getCurrentSession()
                .createQuery(
                        "select count(c) from Category c " +
                                "where c.categoryName = :categoryName",
                        Long.class
                )
                .setParameter("categoryName", categoryName)
                .uniqueResult();

        return count > 0;
    }

    @Override
    public boolean existsByNameAndIdNot(String categoryName, long id) {
        Long count = sessionFactory
                .getCurrentSession()
                .createQuery(
                        "select count(c) from Category c " +
                                "where c.categoryName = :categoryName " +
                                "and c.id <> :id",
                        Long.class
                )
                .setParameter("categoryName", categoryName)
                .setParameter("id", id)
                .uniqueResult();

        return count > 0;
    }

    @Override
    public void saveCategory(Category category) {
        sessionFactory.getCurrentSession().persist(category);
    }

    @Override
    public void updateCategory(Category category) {
        sessionFactory.getCurrentSession().merge(category);
    }

    @Override
    public void deleteCategory(long id) {
        Category category = findCategoryById(id);
        if(category != null) sessionFactory.getCurrentSession().remove(category);
    }
}
