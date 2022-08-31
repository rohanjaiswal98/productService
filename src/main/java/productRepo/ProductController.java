package productRepo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class ProductController {

    private final ProductRepository repository;

    ProductController(ProductRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/products")
    List<Product> all() {
        return repository.findAll();
    }

    @PostMapping("/products")
    Product newProduct(@RequestBody Product product) {
        return repository.save(product);
    }


    @GetMapping("/products/{id}")
    Product one(@PathVariable Long id) {

        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PutMapping("/products/{id}")
    Product updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {

        return repository.findById(id) //
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setDescription(newProduct.getDescription());
                    product.setPrice(newProduct.getPrice());
                    return repository.save(product);
                }) //
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });
    }

    @DeleteMapping("/products/{id}")
    ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        Product product = repository.findById(id).map(p -> {
            repository.deleteById(id);
            return p;
        }).orElseThrow(() -> new ProductNotFoundException(id));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
