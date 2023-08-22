package OnlineShopping.controllers;

import OnlineShopping.dto.CheckoutRequest;
import OnlineShopping.models.Deal;
import OnlineShopping.models.Role;
import OnlineShopping.services.AuthenticationService;
import OnlineShopping.services.DealService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/deal")
public class DealController {

    @Autowired
    DealService dealService;
    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/deal/{id}")
    public Deal getDealById(@PathVariable("id") Long id) {
        return dealService.getDealById(id);
    }

    @GetMapping("/")
    public List<Deal> deals(HttpServletRequest request) {
        var user = authenticationService.getUserByRequest(request);
        if (user == null) return new ArrayList<Deal>();
        if (user.getRole() == Role.ADMIN) {
            return dealService.getAllDeals();
        } else {
            return dealService.getUserDeals(user);
        }
    }

    @PostMapping("/")
    public void createDeal(@Valid @RequestBody List<CheckoutRequest> purchases,
                           HttpServletRequest request) {
        var user = authenticationService.getUserByRequest(request);
        dealService.createDeal(purchases, user);
    }

}
