package com.BandManagement.api;

import com.BandManagement.persistence.model.*;
import com.BandManagement.persistence.repositories.*;
import com.BandManagement.service.*;
import com.BandManagement.util.FileUploadUtil;
import com.BandManagement.util.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/bands")
public class BandController {

    private final BandService bandService;
    private final BandRepository bandRepository;
    private final GenreRepository genreRepository;
    private final MemberRepository memberRepository;
    private final PositionRepository positionRepository;
    private final AlbumService albumService;
    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;
    private final NewsRepository newsRepository;
    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;
    private final UsersRepository usersRepository;
    private final OrdersRepository ordersRepository;
    private final OrdersProductRepository ordersProductRepository;
    private final OrdersProductService ordersProductService;

    @PostMapping
    public void createBand(@RequestBody Band band) {
        bandService.createBand(band);
    }

    @GetMapping("/get-all-bands")
    public List<Band> getBands() {
        return bandService.getBands();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBand(@RequestBody Band band, @PathVariable UUID bandId) {
        bandService.deleteBandById(bandId);
    }

    @GetMapping("delete/{id}")
    public String deleteBand(@PathVariable("id") UUID id, Model model) {
        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid band Id:" + id));
        bandRepository.delete(band);
        model.addAttribute("bands", bandRepository.findAll());
        return "redirect:/";
    }

    @PostMapping("/assign/{bandId}/{genreId}")
    public void assignGenreToBand(@PathVariable UUID bandId, @PathVariable UUID genreId) {
        bandService.assignGenreToBand(bandId, genreId);
    }

    @GetMapping("/band/{id}")
    public String showLandingPage(@PathVariable("id") UUID id, Model model) {
        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
        model.addAttribute("band", band);
        model.addAttribute("genre", genreRepository.findAll());
        return "band-landing-page";
    }

    //ABOUT:
    @GetMapping("/about/{id}")
    public String aboutPage(@PathVariable("id") UUID id, Model model) {
        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid band Id:" + id));
        model.addAttribute("band", band);
        model.addAttribute("bandsList", bandService.getBands());
        model.addAttribute("genre", genreRepository.findAll());
        return "about";
    }

    //MEMBERS:
    @GetMapping("/member/{id}")
    public String showMembersPage(@PathVariable("id") UUID id, Model model, Member member) {
        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid band Id:" + id));
        model.addAttribute("band", band);
        model.addAttribute("bandList", bandService.getBandById(id));

        model.addAttribute("member", member);
        model.addAttribute("members", memberRepository.getMemberByBandId(id));

        model.addAttribute("positions", positionRepository.findAll());
        return "members";
    }


    //CREATE FORMA NEW BAND:
    @GetMapping("/addBand")
    public String addBand(Model model) {
        model.addAttribute("band", new Band());
        model.addAttribute("genres", genreRepository.findAll());
        return "create-band";
    }

    @PostMapping("/addBand")
    public String addBand(@Valid Band band, @RequestParam("image") MultipartFile multipartFile, BindingResult result) throws IOException{
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        System.out.println("\nfile: " + fileName);
        band.setPhoto(fileName);
        if (result.hasErrors()) {
            return "create-band";
        }
        Band newBand = bandRepository.save(band);
        String uploadDir = "band-photos/" + newBand.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/" ;
    }

    //ALBUMS:
    @GetMapping("/albums/{id}")
    public String showAlbumsPage(@PathVariable("id") UUID id, Model model, Album album, Track track) {
        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid band Id:" + id));
        model.addAttribute("band", band);
        model.addAttribute("bandList", bandService.getBandById(id));
        model.addAttribute("albumList", albumRepository.findAllByBandIdOrderByYearDesc(id));
        model.addAttribute("album", album);


        model.addAttribute("track", track);
        model.addAttribute("tracks", trackRepository.getTrackByAlbumId(id));

        return "albums";
    }

    //TRACKS:
    @GetMapping("/tracks/{id}")
    public String showTracksPage(@PathVariable("id") UUID id, Model model, Track track, Album album) {
        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
        model.addAttribute("album", album);
        model.addAttribute("albumList", albumService.getAlbumById(id));

        return "albums";
    }

    //NEWS:
    @GetMapping("/news/{id}")
    public String showNewsPage(@PathVariable("id") UUID id, Model model, News news, Track track) {
        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid band Id:" + id));
        model.addAttribute("band", band);
        model.addAttribute("bandList", bandService.getBandById(id));
        model.addAttribute("newsList", newsRepository.findAllByBandIdOrderByNewsDateDesc(id));
        model.addAttribute("news", news);

        return "news";
    }

    //EDIT:
    @PostMapping("update/{id}")
    public String updateBand(@PathVariable("id") UUID id, @Valid Band band, @RequestParam("image") MultipartFile multipartFile, BindingResult result,
                             Model model) throws IOException {
        if (result.hasErrors()) {
            band.setId(id);
            return "redirect:viewPage";
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        System.out.println("\nfile: " + fileName);
        band.setPhoto(fileName);
        String uploadDir = "band-photos/" + band.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        bandRepository.save(band);
        model.addAttribute("bands", bandRepository.findAll());
        model.addAttribute("genre", genreRepository.findAll());
        return "redirect:http://localhost:8080/";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID id, Model model) {
        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid band Id:" + id));
        model.addAttribute("band", band);
        model.addAttribute("genre", genreRepository.findAll());

        return "band-edit";
    }

    //STORE:
    @GetMapping("/store/{id}")
    public String storePage(@PathVariable("id") UUID id, Model model, Product product) {
        Band band = bandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid band Id:" + id));
        model.addAttribute("band", band);
        model.addAttribute("bandsList", bandService.getBands());
        model.addAttribute("products", productRepository.getProductByBandId(id));
        model.addAttribute("product", product);
        model.addAttribute("category", categoriesRepository.findAll());

        return "shop-landing-page";
    }

    @PostMapping("/addToCart/{bandId}/{id}")
    public String addToCart(@PathVariable("bandId") UUID bandId, @PathVariable("id") UUID id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        String name = UserUtils.getUsername();
        Users user = usersRepository.findByEmail(name);

        Orders orders = ordersRepository.findOrdersByUserIdAndOrderStatus(user.getId(), true);
        if(orders == null ){
            Orders orders1 = new Orders();
            orders1.setOrderDate();
            orders1.setUser(user);
            orders1.setOrderStatus(true);
            orders1.setTotalOrderPrice(product.getUnitPrice());
            ordersRepository.save(orders1);
            OrdersProduct ordersProduct = new OrdersProduct();
            ordersProduct.setProduct(product);
            ordersProduct.setOrder(orders1);
            ordersProduct.setAmount(1);

            if (product.getQuantity() == 0) {
                Band band = bandRepository.findById(bandId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid band Id:" + bandId));
                model.addAttribute("band", band);
                model.addAttribute("bandsList", bandService.getBands());
                model.addAttribute("products", productRepository.getProductByBandId(id));
                model.addAttribute("product", product);
                model.addAttribute("category", categoriesRepository.findAll());
                model.addAttribute("errorMsg", String.format("Not enough item (%s) in stock, items currently in stock: %s", product.getProductName(), product.getQuantity()));
                return "error_message";
            }
            product.setQuantity(product.getQuantity()-1);
            productRepository.save(product);
            ordersProduct.setTotalPrice(product.getUnitPrice());
            ordersProductRepository.save(ordersProduct);
            List<OrdersProduct> ordersProducts = new ArrayList<>();
            ordersProducts.add(ordersProduct);
            orders1.setTotalOrderPrice(product.getUnitPrice());
            orders1.setOrdersProduct(ordersProducts);
        } else { //if, dohvati medu op di je orderid=orders di je prod.id =product,
            OrdersProduct postojeca = ordersProductRepository.findByOrderIdAndProductId(orders.getId(), product.getId());
            if(postojeca != null){
                int kolicina = postojeca.getAmount();
                kolicina++;

                postojeca.setTotalPrice(product.getUnitPrice() * kolicina);

                if (product.getQuantity() == 0) {
                    Band band = bandRepository.findById(bandId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid band Id:" + bandId));
                    model.addAttribute("band", band);
                    model.addAttribute("bandsList", bandService.getBands());
                    model.addAttribute("products", productRepository.getProductByBandId(id));
                    model.addAttribute("product", product);
                    model.addAttribute("category", categoriesRepository.findAll());
                    model.addAttribute("errorMsg", String.format("Not enough item (%s) in stock, items currently in stock: %s", product.getProductName(), product.getQuantity()));
                    return "error_message";
                }
                product.setQuantity(product.getQuantity()-1);

                productRepository.save(product);
                postojeca.setAmount(kolicina);
                ordersProductRepository.save(postojeca);

            } else {
                OrdersProduct ordersProduct = new OrdersProduct();
                ordersProduct.setProduct(product);
                ordersProduct.setOrder(orders);
                ordersProduct.setAmount(1);

                if (product.getQuantity() == 0) {
                    Band band = bandRepository.findById(bandId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid band Id:" + bandId));
                    model.addAttribute("band", band);
                    model.addAttribute("bandsList", bandService.getBands());
                    model.addAttribute("products", productRepository.getProductByBandId(id));
                    model.addAttribute("product", product);
                    model.addAttribute("category", categoriesRepository.findAll());
                    model.addAttribute("errorMsg", String.format("Not enough item (%s) in stock, items currently in stock: %s", product.getProductName(), product.getQuantity()));
                    return "error_message";
                }
                product.setQuantity(product.getQuantity()-1);
                productRepository.save(product);
                ordersProduct.setTotalPrice(product.getUnitPrice());
                ordersProductRepository.save(ordersProduct);
                List<OrdersProduct> ordersProducts = new ArrayList<>();
                ordersProducts.add(ordersProduct);
                orders.setOrdersProduct(ordersProducts);
                ordersRepository.save(orders);
            }
            List<OrdersProduct> all = ordersProductRepository.findAllByOrderId(orders.getId());

            float sum = 0;
            for(OrdersProduct op: all){
                sum += op.getTotalPrice();
            }
            orders.setTotalOrderPrice(sum);
            ordersRepository.save(orders);
        }
        return "redirect:/bands/store/" + bandId;
    }

}