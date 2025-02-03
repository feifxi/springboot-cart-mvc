package suchai125.amazon.project125.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import suchai125.amazon.project125.entities.Product;
import suchai125.amazon.project125.models.Cart;
import suchai125.amazon.project125.models.CartItem;
import suchai125.amazon.project125.services.CartService;
import suchai125.amazon.project125.services.ProductLineService;
import suchai125.amazon.project125.services.ProductService;

import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private final ProductLineService productLineService;
    private final CartService cartService;
    private final List<String> slidesUrl;
    private final HttpSession httpSession;

    @Autowired
    public ProductController(ProductService productService, ProductLineService productLineService, CartService cartService, HttpSession httpSession) {
        this.productService = productService;
        this.productLineService = productLineService;
        this.cartService = cartService;
        this.httpSession = httpSession;
        this.slidesUrl = List.of(
                "https://i.ytimg.com/vi/l5sP6s-u7XE/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLA7_er_VJ2vOEaS45neIcK9b5RB2Q",
                "https://mf.b37mrtl.ru/rbthmedia/images/2021.05/original/60b4cf3985600a50dd341bb9.jpg",
                "https://media1.tenor.com/m/CY0jIzTEBOcAAAAd/baki-sea-king-retsu.gif"
        );
    }

    @GetMapping
    public String getProducts(ModelMap model) {
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("slidesUrl",slidesUrl);
        model.addAttribute("page","main");
         return "product_list";
    }

    @GetMapping("/addProduct")
    public String addNewProductPage(@RequestParam(defaultValue = "") Product product, ModelMap model) {
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("productLines", productLineService.getProductLines());
        model.addAttribute("slidesUrl",slidesUrl);
        model.addAttribute("page","addProduct");
        return "product_list";
    }

    @PostMapping("/createNewProduct")
    public String addNewProduct(@ModelAttribute Product product, ModelMap model) {
//        System.out.println(product.toString());
        String[] statusMsg = null;
        String page = null;
        if (productService.checkProductExists(product.getProductCode())) {
            statusMsg = new String[]{"error", "This product code is belong to the existing product"};
            page = "addProduct";
        }
        else {
            product.setProductCode("125_" + product.getProductCode());
            productService.addProduct(product);
            statusMsg = new String[]{"success", "Add new product successfully"};
            page = "main";
        }
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("slidesUrl",slidesUrl);
        model.addAttribute("page",page);
        model.addAttribute("statusMsg",statusMsg);
        return "product_list";
    }

    @GetMapping("/edit")
    public String updateProductPage(@RequestParam String productCode, ModelMap model) {
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("editProduct", productService.getProductByCode(productCode));
        model.addAttribute("productLines", productLineService.getProductLines());
        model.addAttribute("slidesUrl",slidesUrl);
        model.addAttribute("page","updateProduct");
        return "product_list";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product, ModelMap model) {
        String[] statusMsg = null;
        String page = null;
        if (!productService.checkProductExists(product.getProductCode())) {
            statusMsg = new String[]{"error", "This product code is belong to the existing product"};
            model.addAttribute("editProduct", product);
            page = "updateProduct";
        }
        else {
            productService.updateProduct(product);
            statusMsg = new String[]{"success", "Update product successfully"};
            page = "main";
        }
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("slidesUrl",slidesUrl);
        model.addAttribute("page",page);
        model.addAttribute("statusMsg",statusMsg);
        return "product_list";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam String productCode, ModelMap model) {
        String[] statusMsg = null;
        String page = null;
        if (!productService.checkProductExists(productCode)) {
            statusMsg = new String[]{"error", "There is no product with that code"};
        }
        else {
            Cart<String, CartItem> cart = (Cart) httpSession.getAttribute("cart");
            cartService.removeItem(productCode, cart);  // remove product in cart
            productService.deleteProduct(productCode);  // remove product from repo
            statusMsg = new String[]{"success", "Delete product successfully"};
        }
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("slidesUrl",slidesUrl);
        model.addAttribute("page","main");
        model.addAttribute("statusMsg",statusMsg);
        return "product_list";
    }


    @GetMapping("/search")
    public String searchProduct(@RequestParam String productName, ModelMap model) {
        model.addAttribute("products", productService.getProductsByName(productName));
        model.addAttribute("slidesUrl",slidesUrl);
        model.addAttribute("page","main");
        return "product_list";
    }
}
