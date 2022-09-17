package com.BandManagement.api;

import com.BandManagement.persistence.model.Band;
import com.BandManagement.persistence.model.Product;
import com.BandManagement.persistence.repositories.BandRepository;
import com.BandManagement.persistence.repositories.CategoriesRepository;
import com.BandManagement.persistence.repositories.ProductRepository;
import com.BandManagement.service.CategoriesService;
import com.BandManagement.service.ProductService;
import com.BandManagement.util.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Controller
@RequestMapping("/products/")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CategoriesService categoriesService;
    private final CategoriesRepository categoriesRepository;
    private final BandRepository bandRepository;

    @PostMapping("/create")
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }

    @GetMapping("/get-all-products")
    public List<Product> getProduct() {
        return productService.getProduct();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@RequestBody Product product, @PathVariable UUID productId) {
        productService.getProductById(productId);
    }

    @GetMapping("delete/{id}")
    public String deleteProduct(@PathVariable("id") UUID id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        productRepository.delete(product);
        model.addAttribute("product", productRepository.findAll());
        return "redirect:/";
    }

    @PostMapping("/assign/{productId}/{categoryId}")
    public void assignCategoryToProduct(@PathVariable UUID productId, @PathVariable UUID categoryId) {
        productService.assignCategoryToProduct(productId, categoryId);
    }

    //CREATE FORMA NEW PRODUCT:
    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("band", bandRepository.findAll());

        return "create-product";
    }

    @PostMapping("/addProduct")
    public String addProduct(@Valid Product product, @RequestParam("image") MultipartFile multipartFile, BindingResult result) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        System.out.println("\nfile: " + fileName);
        product.setPhoto(fileName);
        if (result.hasErrors()) {
            return "create-product";
        }

        //band.getBandDescription().replaceAll("\\r\\n", "<br>");
        Product newProduct = productRepository.save(product);
        String uploadDir = "product-photos/" + newProduct.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/";
    }

    //EDIT:
    @PostMapping("update/{id}")
    public String updateProduct(@PathVariable("id") UUID id, @Valid Product product, @RequestParam("image") MultipartFile multipartFile, BindingResult result,
                                Model model, String photo) throws IOException {
        if (result.hasErrors()) {
            product.setId(id);
            return "redirect:viewPage";
        }
        if(product.getPhoto() != null) {
            product.getPhoto();
            product.setPhoto(product.getPhoto());
            productRepository.save(product);
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        System.out.println("\nfile: " + fileName);
        product.setPhoto(fileName);
        String uploadDir = "product-photos/" + product.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        productRepository.save(product);
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("category", categoriesRepository.findAll());
        model.addAttribute("band", bandRepository.findAll());

        return "redirect:http://localhost:8080/";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("category", categoriesRepository.findAll());
        model.addAttribute("band", bandRepository.findAll());

        return "product-edit";
    }

    //SEARCH:
    @RequestMapping("/search/{id}")
    public String findByCategoryNameIgnoreCase(@PathVariable("id") UUID id, Model model, @Param("keyword") String keyword) {
        List<Product> products = productService.findByCategoryNameContainingIgnoreCase(keyword);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);

        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid band Id:" + id));
        model.addAttribute("band", band);

        return "search-products";
    }

}
