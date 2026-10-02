package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> getAllCategories();
    Category findCategoryById(long id);
    Category findCategoryByName(String categoryName);
    void saveCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(long id);
}
