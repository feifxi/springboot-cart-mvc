package suchai125.amazon.project125.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suchai125.amazon.project125.entities.Product;
import suchai125.amazon.project125.models.Cart;
import suchai125.amazon.project125.models.CartItem;
import suchai125.amazon.project125.models.ClassicModelLineItem;

@Service
public class CartService {
    private final ProductService productService;
    @Autowired
    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public void addItem(String productCode, Cart<String, CartItem> cart) {
        Product p = productService.getProductByCode(productCode);
        if (p != null) {
            CartItem ci = new ClassicModelLineItem(p);
            cart.addItem(productCode, ci);
        }
    }

    public void removeItem(String productCode, Cart<String, CartItem> cart) {
        Product p = productService.getProductByCode(productCode);
        if (p != null) {
            cart.removeItem(productCode);
        }

    }

    public void removeAllItem(String productCode, Cart<String, CartItem> cart) {
        Product p = productService.getProductByCode(productCode);
        if (p != null) {
            cart.removeAllItem(productCode);
        }
    }
}
