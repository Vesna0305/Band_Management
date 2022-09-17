package com.BandManagement.api;

import com.BandManagement.persistence.model.Categories;
import com.BandManagement.persistence.repositories.CategoriesRepository;
import com.BandManagement.service.CategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Controller
@RequestMapping("/categories")
public class CategoriesController {

    private final CategoriesRepository categoriesRepository;
    private final CategoriesService categoriesService;

    @PostMapping("/create")
    public void createCategory(@RequestBody Categories category) {
        categoriesService.createCategories(category);
    }

    @GetMapping("/get-all-categories")
    public List<Categories> getCategories() {
        return categoriesService.getCategories();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@RequestBody Categories category, @PathVariable UUID categoryId) {
        categoriesService.getCategoriesById(categoryId);
    }

}
