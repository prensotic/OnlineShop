package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.Category;

import java.util.Set;

public interface CategoryRepository {
    Set<Category> getAllCategories();
    Category findCategoryById(long id);
    Category findCategoryByName(String categoryName);
    boolean existsByName(String categoryName);
    boolean existsByNameAndIdNot(String categoryName, long id);
    void saveCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(long id);
}
