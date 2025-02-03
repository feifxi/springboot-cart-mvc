package suchai125.amazon.project125.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import suchai125.amazon.project125.entities.Productline;

public interface ProductlineRepository extends JpaRepository<Productline, String> {
}