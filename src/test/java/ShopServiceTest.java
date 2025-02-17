import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        OrderState expectedStatus = OrderState.PROCESSING;

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")));
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
        assertEquals(expectedStatus, actual.status());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN & THEN
        assertThrowsExactly(NoSuchElementException.class,
                () -> shopService.addOrder(productsIds)
        );
    }

    @Test
    void getOrderListByStateTest(){
        ShopService shopService = new ShopService();
        List<String> productsIds1 = List.of("1", "3");
        List<String> productsIds2 = List.of("1", "2");
        Order order1 = shopService.addOrder(productsIds1);
        Order order2 = shopService.addOrder(productsIds1);
        OrderState state = OrderState.PROCESSING;

        List<Order> orders = shopService.getOrderListByState(state);

        for (Order order : orders) {
            assertEquals(state, order.status());
        }
        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }
}
