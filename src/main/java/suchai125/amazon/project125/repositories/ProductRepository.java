package suchai125.amazon.project125.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import suchai125.amazon.project125.entities.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("""
        select p from Product p where p.productName like %?1%
    """)
    List<Product> findByProductName(String productName);
}