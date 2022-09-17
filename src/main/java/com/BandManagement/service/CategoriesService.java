package com.BandManagement.service;

import com.BandManagement.persistence.model.Categories;
import com.BandManagement.persistence.model.Track;
import com.BandManagement.persistence.repositories.CategoriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
@Service
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public Categories createCategories(Categories categories) {
        return categoriesRepository.save(categories);
    }

    public List<Categories> getCategories() {
        return categoriesRepository.findAll();
    }

    public void deleteCategoriesById(UUID id) {
        categoriesRepository.deleteById(id);
    }

    public Categories getCategoriesById(UUID id) {
        return categoriesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
    }

}
