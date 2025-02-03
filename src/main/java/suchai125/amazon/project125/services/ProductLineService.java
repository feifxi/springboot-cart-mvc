package suchai125.amazon.project125.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suchai125.amazon.project125.entities.Productline;
import suchai125.amazon.project125.repositories.ProductlineRepository;

import java.util.List;

@Service
public class ProductLineService {
    final ProductlineRepository productlineRepository;
    @Autowired
    public ProductLineService(ProductlineRepository productlineRepository) {
        this.productlineRepository = productlineRepository;
    }

    public List<Productline> getProductLines() {
        return productlineRepository.findAll();
    }
}
