package ru.loginov.dev.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.loginov.dev.entities.Category;
import ru.loginov.dev.services.CategoryService;

@Controller
@RequestMapping("/dashboard")
public class AdminCategoryController {
    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String categoriesPage(Model model){
        model.addAttribute("categories", categoryService.getAllCategories());
        return "dashboard/categories/categories";
    }

    @GetMapping("/categories/{id}")
    public String categoriesDetailsPage(@PathVariable("id") long id, Model model){
        model.addAttribute("category", categoryService.findCategoryById(id));
        return "dashboard/categories/category_details";
    }

    @GetMapping("/categories/new-category")
    public String createCategoryPage(Model model){
        model.addAttribute("category", new Category());
        return "dashboard/categories/new_category";
    }

    @PostMapping("/categories/new-category")
    public String createCategory(@ModelAttribute("category") Category category){
        categoryService.createCategory(category.getCategoryName());
        return "redirect:/dashboard/categories";
    }

    @GetMapping("/categories/edit-category/{id}")
    public String editCategoryPage(@PathVariable("id") long id, Model model){
        model.addAttribute("category", categoryService.findCategoryById(id));
        return "dashboard/categories/edit_category";
    }

    @PatchMapping("/categories/edit-category/{id}")
    public String editCategory(@PathVariable("id") long id, @ModelAttribute("category") Category category){
        categoryService.updateCategory(id, category.getCategoryName());
        return "redirect:/dashboard/categories";
    }

    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable("id") long id){
        categoryService.deleteCategory(id);
        return "redirect:/dashboard/categories";
    }
}