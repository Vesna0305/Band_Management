package com.BandManagement.api;

import com.BandManagement.persistence.model.*;
import com.BandManagement.persistence.repositories.OrdersProductRepository;
import com.BandManagement.persistence.repositories.OrdersRepository;
import com.BandManagement.persistence.repositories.UsersRepository;
import com.BandManagement.service.BandService;
import com.BandManagement.service.OrdersService;
import com.BandManagement.service.UserServiceImpl;
import com.BandManagement.util.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersRepository ordersRepository;
    private final OrdersService ordersService;
    private final UsersRepository usersRepository;
    private final BandService bandService;
    private final OrdersProductRepository ordersProductRepository;
    private final UserServiceImpl userService;

    @PostMapping("/create")
    public void createOrders(@RequestBody Orders order) {
        ordersService.createOrder(order);
    }

    @GetMapping("/get-all-orders")
    public List<Orders> getOrders() {
        return ordersService.getOrders();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@RequestBody Orders order, @PathVariable UUID orderId) {
        ordersService.getOrdersById(orderId);
    }

    @GetMapping("/listByCustomer")
    public String myOrdersPage(Model model, Orders order, Band band, OrdersProduct op) {

        String name = UserUtils.getUsername();

        model.addAttribute("order", order);
        model.addAttribute("orders", ordersRepository.findByUserEmailOrderByOrderStatusDesc(name));
        model.addAttribute("band", band);
        model.addAttribute("bandsList", bandService.getBands());
        model.addAttribute("op", op);

        return "shopping-cart";
    }

    @RequestMapping(value = "/buyNow/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public String myOrdersPage(@PathVariable("id") UUID id, Model model) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        order.setOrderStatus(false);
        ordersRepository.save(order);

        return "redirect:/orders/listByCustomer";
    }

    @GetMapping("/listAllOrders")
    public String AdminOrdersPage(Model model, Orders order, Band band, Users user, OrdersProduct op, String email, Principal principal /*UUID oid, UUID pid*/) {
        model.addAttribute("order", order);
        model.addAttribute("orders", ordersRepository.findAllByOrderByTotalOrderPriceDescUserIdAsc());
        model.addAttribute("band", band);
        model.addAttribute("bandsList", bandService.getBands());
        model.addAttribute("op", op);

        return "order-history-admin";
    }

    //SEARCH:
    @RequestMapping("/search/user")
    public String findByLastNameIgnoreCase(Model model, @Param("keyword") String keyword) {
        List<Orders> orders = ordersService.findByLastNameContainingIgnoreCase(keyword);
        model.addAttribute("orders", orders);
        model.addAttribute("keyword", keyword);

        return "search-orders";
    }

}
