package ru.loginov.dev.services;

import org.springframework.transaction.annotation.Transactional;
import ru.loginov.dev.entities.Category;
import ru.loginov.dev.exceptions.categoryExceptions.CategoryAlreadyExistsException;
import ru.loginov.dev.exceptions.categoryExceptions.CategoryNotFoundException;
import ru.loginov.dev.repositories.CategoryRepository;

import java.util.List;
import java.util.Set;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public List<Category> getAllCategories(){
        return categoryRepository.getAllCategories();
    }

    @Transactional
    public Category findCategoryById(long id){
        if(id <= 0) throw new IllegalArgumentException("ID должен быть положительным");

        Category category = categoryRepository.findCategoryById(id);

        if(category == null) throw new CategoryNotFoundException("Категория с ID: " + id + " не найдена");

        return category;
    }

    @Transactional
    public Category findCategoryByName(String categoryName){
        if(categoryName == null || categoryName.isBlank())
            throw new IllegalArgumentException("Имя категории указано некорректно");

        Category category = categoryRepository.findCategoryByName(categoryName);

        if(category == null)
            throw new CategoryNotFoundException("Категория с именем: " + categoryName + " не найдена");

        return category;
    }

    @Transactional
    public void createCategory(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("Имя категории указано некорректно");
        }

        if (categoryRepository.existsByName(categoryName))
            throw new CategoryAlreadyExistsException("Категория с таким именем уже существует");

        Category category = new Category();
        category.setCategoryName(categoryName);

        categoryRepository.saveCategory(category);
    }

    @Transactional
    public void updateCategory(long id, String categoryName) {
        Category category = findCategoryById(id);

        if (categoryName == null || categoryName.isBlank())
            throw new IllegalArgumentException("Имя категории указано некорректно");

        if (categoryRepository.existsByNameAndIdNot(categoryName, id))
            throw new CategoryAlreadyExistsException("Категория с таким именем уже существует");

        category.setCategoryName(categoryName);
    }

    @Transactional
    public void deleteCategory(long id){
        findCategoryById(id);
        categoryRepository.deleteCategory(id);
    }
}