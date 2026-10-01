package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.Category;

import java.util.List;

public interface CategoryRepository {
    public List<Category> getAllCategories();
    public Category findCategoryById(long id);
    public Category findCategoryByName(String categoryName);
    public void saveCategory(Category category);
    public void updateCategory(Category category);
    public void deleteCategory(long id);
}
