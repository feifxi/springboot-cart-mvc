package suchai125.amazon.project125.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import suchai125.amazon.project125.models.Cart;
import suchai125.amazon.project125.models.CartItem;
import suchai125.amazon.project125.services.CartService;
import suchai125.amazon.project125.services.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final HttpSession httpSession;

    @Autowired
    public CartController(CartService cartService , ProductService productService, HttpSession httpSession) {
        this.cartService = cartService;
        this.productService = productService;
        this.httpSession = httpSession;
    }

    @GetMapping
    public String getCart(ModelMap map){
        return "view_cart";
    }

    @GetMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam String productCode, ModelMap map){
        Cart<String, CartItem> cart = (Cart) httpSession.getAttribute("cart");
        cartService.addItem(productCode, cart);
        // response AJAX
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/remove")
    public ResponseEntity<Cart> decreaseItemCart(@RequestParam String productCode, ModelMap map){
        Cart<String, CartItem> cart = (Cart) httpSession.getAttribute("cart");
        cartService.removeItem(productCode, cart);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/remove-all")
    public String removeFromCart(@RequestParam String productCode, ModelMap map){
        Cart<String, CartItem> cart = (Cart) httpSession.getAttribute("cart");
        cartService.removeAllItem(productCode, cart);
        return "redirect:/cart";
    }
}

