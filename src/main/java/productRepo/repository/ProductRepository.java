package productRepo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import productRepo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
