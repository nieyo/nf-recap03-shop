import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), Instant.now());
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        Product product1 = new Product("1", "Apfel");
        expected.add(new Order("1", List.of(product1), Instant.now()));

        for (int i = 0; i < actual.size(); i++) {
            Order actualOrder = actual.get(i);
            Order expectedOrder = expected.get(i);
            assertEquals(actualOrder.id(), expectedOrder.id());
            assertEquals(actualOrder.products(), expectedOrder.products());
            assertEquals(actualOrder.state(), expectedOrder.state());
        }
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), Instant.now());
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1");

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1), Instant.now());

        assertEquals(expected.id(), actual.id());
        assertEquals(expected.products(), actual.products());
        assertEquals(expected.state(), actual.state());


    }

    @Test
    void addOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), Instant.now());

        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1), Instant.now());
        assertEquals(expected.id(), actual.id());
        assertEquals(expected.products(), actual.products());
        assertEquals(expected.state(), actual.state());
        assertEquals(repo.getOrderById("1").id(), expected.id());
        assertEquals(repo.getOrderById("1").products(), expected.products());
        assertEquals(repo.getOrderById("1").state(), expected.state());
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertNull(repo.getOrderById("1"));
    }
}
