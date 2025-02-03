package suchai125.amazon.project125.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import suchai125.amazon.project125.models.Cart;
import suchai125.amazon.project125.models.CartItem;
import suchai125.amazon.project125.services.ProductService;

import java.util.List;

@Controller
public class AuthController {
    private final ProductService productService;
    private final List<String> slidesUrl;
    private final HttpSession httpSession;

    @Autowired
    public AuthController(ProductService productService, HttpSession httpSession) {
        this.productService = productService;
        this.httpSession = httpSession;
        this.slidesUrl = List.of(
                "https://i.ytimg.com/vi/l5sP6s-u7XE/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLA7_er_VJ2vOEaS45neIcK9b5RB2Q",
                "https://mf.b37mrtl.ru/rbthmedia/images/2021.05/original/60b4cf3985600a50dd341bb9.jpg",
                "https://media1.tenor.com/m/CY0jIzTEBOcAAAAd/baki-sea-king-retsu.gif"
        );;
    }

    @GetMapping("/login")
    public String loginPage(ModelMap model) {
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("slidesUrl",slidesUrl);
        model.addAttribute("page","login");
        return "product_list";
    }

    @PostMapping("/loginAuth")
    public String loginAuth(@RequestParam String username, @RequestParam String password, ModelMap model) {
        if (!password.equals("suchai handsome")) {
            model.addAttribute("products", productService.getProducts());
            model.addAttribute("slidesUrl",slidesUrl);
            model.addAttribute("statusMsg",new String[]{"error", "Invalid password : correct password is suchai handsome"});
            model.addAttribute("page","login");
            return "product_list";
        }
        Cart<String, CartItem> cart = new Cart<String, CartItem>();
        httpSession.setAttribute("cart", cart);
        httpSession.setAttribute("user", username);
        return "redirect:/";
    }
}
