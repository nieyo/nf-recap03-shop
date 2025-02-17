import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    @Test
    void getProducts() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        List<Product> actual = repo.getProducts();

        //THEN
        List<Product> expected = new ArrayList<>();
        expected.add(new Product("1", "Apfel"));
        expected.add(new Product("3", "Birne"));
        expected.add(new Product("4", "Kiwi"));

        assertEquals(actual, expected);
    }

    @Test
    void getProductById() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Optional<Product> expected = Optional.of(new Product("1", "Apfel"));

        //WHEN
        Optional<Product> actual = repo.getProductById("1");

        //THEN
        assertEquals(actual, expected);
    }

    @Test
    void getProductById_whenProductNotExists() {
        ProductRepo repo = new ProductRepo();
        Optional<Product> expected = Optional.empty();

        Optional<Product> actual = repo.getProductById("2");

        assertEquals(actual, expected);
    }

    @Test
    void addProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Product newProduct = new Product("2", "Banane");
        Product expected = new Product("2", "Banane");

        //WHEN
        Product actual = repo.addProduct(newProduct);

        //THEN
        assertEquals(actual, expected);
        assertTrue(repo.getProductById("2").isPresent());
        assertEquals(repo.getProductById("2").get(), expected);
    }

    @Test
    void removeProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();

        //WHEN
        repo.removeProduct("1");

        //THEN
        assertTrue(repo.getProductById("1").isEmpty());
    }
}
