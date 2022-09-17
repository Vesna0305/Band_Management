package com.BandManagement.service;

import com.BandManagement.persistence.model.Product;
import com.BandManagement.persistence.repositories.CategoriesRepository;
import com.BandManagement.persistence.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    public void deleteProductById(UUID id) {
        productRepository.deleteById(id);
    }

    public void assignCategoryToProduct(UUID productId, UUID categoryId) {
        var product = productRepository.findById(productId).orElse(null);
        var category = categoriesRepository.findById(categoryId).orElse(null);

        product.setCategories(category);

        productRepository.save(product);
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
    }

    public List<Product> findByCategoryNameContainingIgnoreCase(@Pattern(regexp = "[A-Za-z]+") String keyword) {
        var products = productRepository.findAll();
        var productList = new ArrayList<Product>();

        products.forEach(i -> {
            if(i.getCategories().getCategoryName().toLowerCase().contains(keyword.toLowerCase())) {
                productList.add(i);
            }
        });
        return productList;
    }

    //PAGING i SORTING:
    public Page<Product> listAll(int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by("productName");
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 2, sort);
        return productRepository.findAll(pageable);
    }

    /*public void addToCart(UUID productId, OrdersProduct ordersProduct){

        Product product = productRepository.findById(productId).orElse(null);
        ordersProduct.setProduct(product);

        ordersProduct.setTotalAmount((int) product.getUnitPrice());;

    }*/


}
