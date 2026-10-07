package ru.loginov.dev.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.loginov.dev.entities.Category;
import ru.loginov.dev.entities.Manufacturer;
import ru.loginov.dev.entities.Product;
import ru.loginov.dev.services.CategoryService;
import ru.loginov.dev.services.ManufacturerService;
import ru.loginov.dev.services.ProductService;

@Controller
@RequestMapping("/dashboard")
public class AdminProductController {
    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;

    public AdminProductController(ProductService productService,
                                  ManufacturerService manufacturerService,
                                  CategoryService categoryService) {
        this.productService = productService;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products")
    public String productsPage(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "dashboard/products/products";
    }

    @GetMapping("/products/{id}")
    public String productDetailsPage(@PathVariable("id") long id, Model model){
        model.addAttribute("product", productService.findProductById(id));
        return "dashboard/products/product-details";
    }

    @GetMapping("/product/new-product")
    public String createProductPage(Model model){
        model.addAttribute("product", new Product());
        return "dashboard/products/new-product";
    }

    @PostMapping("/products")
    public String createProduct(
            @RequestParam("categoryId") long categoryId,
            @RequestParam("manufacturerId") long manufacturerId,
            @ModelAttribute("product") Product product) {

        Category category = categoryService.findCategoryById(categoryId);
        Manufacturer manufacturer =
                manufacturerService.findManufacturerById(manufacturerId);

        productService.createProduct(
                product.getProductName(),
                product.getProductDescription(),
                category,
                manufacturer,
                product.getPrice(),
                product.getUnit(),
                product.getQuantity());
        return "redirect:/dashboard/products";
    }

    @GetMapping("/products/edit-product/{id}")
    public String updateProductPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productService.findProductById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
        return "dashboard/products/edit-product";
    }

    @PatchMapping("/products/edit-product/{id}")
    public String updateProduct(@RequestParam("categoryId") long categoryId,
                                 @RequestParam("manufacturerId") long manufacturerId,
                                @ModelAttribute("product") Product product,
                                @PathVariable("id") long id){
        Category category = categoryService.findCategoryById(categoryId);
        Manufacturer manufacturer =
                manufacturerService.findManufacturerById(manufacturerId);

        productService.updateProduct(id,
                product.getProductName(),
                product.getProductDescription(),
                category,
                manufacturer,
                product.getPrice(),
                product.getUnit(),
                product.getQuantity(),
                product.isActive());
        return "redirect:/dashboard/products";
    }

    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable("id") long id){
        productService.deleteProduct(id);
        return "redirect:/dashboard/products";
    }
}