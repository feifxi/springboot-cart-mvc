package suchai125.amazon.project125.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suchai125.amazon.project125.entities.Product;
import suchai125.amazon.project125.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    final private ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductByCode(String productCode) {
        return productRepository.findById(productCode).orElse(null);
    }

    public List<Product> getProductsByName(String productName) {
        return productRepository.findByProductName(productName);
    }

    public Product addProduct(Product product) {
        if (productRepository.existsById(product.getProductCode())) return null;
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        if (!productRepository.existsById(product.getProductCode())) return null;
        return productRepository.save(product); // replace the existing
    }

    public void deleteProduct(String productCode) {
        if (!productRepository.existsById(productCode)) return;
        productRepository.deleteById(productCode);
    }

    public boolean checkProductExists(String productCode) {
        return productRepository.existsById(productCode);
    }
}
