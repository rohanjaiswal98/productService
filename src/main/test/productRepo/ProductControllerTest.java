package productRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import productRepo.exception.ProductNotFoundException;
import productRepo.model.Product;
import productRepo.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductController productController;

    private Product stubProduct;
    private List<Product> productList;

    @BeforeEach
    public void setup() {
        stubProduct = new Product("TestProduct", "TestDescription", 99);
        productList = Collections.singletonList(stubProduct);
    }

    @Test
    void all() {
        when(productRepository.findAll()).thenReturn(productList);
        List<Product> actualResult = productController.all();
        assertFalse(actualResult.isEmpty());
        assertEquals(actualResult, productList);

    }

    @Test
    void newProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(stubProduct);
        Product actualResult = productController.newProduct(stubProduct);
        assertNotNull(actualResult);
        assertEquals(actualResult, stubProduct);

    }

    @Test
    void oneFound() {
        Optional<Product> productOptional = Optional.of(stubProduct);
        when(productRepository.findById(any())).thenReturn(productOptional);
        Product actualResult = productController.one(1L);
        assertEquals(actualResult, stubProduct);
    }

    @Test
    void oneNotFound() {
        Optional<Product> productOptional = Optional.empty();
        when(productRepository.findById(any())).thenReturn(productOptional);
        Product actualResult = null;
        try {
            actualResult = productController.one(1L);
            fail();
        } catch (ProductNotFoundException e) {
            assertNull(actualResult);
            assertTrue(e.getMessage().contains("1"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void updateProductExists() {
        Optional<Product> productOptional = Optional.of(stubProduct);
        when(productRepository.findById(any())).thenReturn(productOptional);
        when(productRepository.save(any(Product.class))).thenReturn(stubProduct);
        Product actualResult = productController.updateProduct(stubProduct, 1L);
        assertEquals(actualResult, stubProduct);

    }

    @Test
    void updateProduct() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(stubProduct);
        Product actualResult = productController.updateProduct(stubProduct, 1L);
        assertEquals(actualResult, stubProduct);

    }

    @Test
    void deleteProduct() {
        Optional<Product> productOptional = Optional.of(stubProduct);
        when(productRepository.findById(any())).thenReturn(productOptional);
        doNothing().when(productRepository).deleteById(any());
        ResponseEntity<Product> actualResult = productController.deleteProduct(1L);
        assertNotNull(actualResult);
        assertEquals(actualResult.getStatusCode(), HttpStatus.OK);
        assertEquals(actualResult.getBody(), stubProduct);

    }
}