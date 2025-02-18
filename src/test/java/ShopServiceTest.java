import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        assertEquals(expectedStatus, actual.state());
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
        Order order1 = shopService.addOrder(productsIds1);
        Order order2 = shopService.addOrder(productsIds1);
        OrderState state = OrderState.PROCESSING;

        List<Order> orders = shopService.getOrderListByState(state);

        for (Order order : orders) {
            assertEquals(state, order.state());
        }
        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    void updateOrderTest_whenNewStateIsSet() {
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "3");
        Order originalOrder = shopService.addOrder(productsIds);

        Optional<Order> updatedOrderOptional = shopService.updateOrder(originalOrder.id(), OrderState.IN_DELIVERY);

        assertTrue(updatedOrderOptional.isPresent());
        Order updatedOrder = updatedOrderOptional.get();
        assertEquals(OrderState.IN_DELIVERY, updatedOrder.state());
        assertEquals(originalOrder.id(), updatedOrder.id());
        assertEquals(originalOrder.products(), updatedOrder.products());
        assertNotEquals(originalOrder.state(), updatedOrder.state());
    }


    @Test
    void updateOrderTest_whenSameStateIsSet() {
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "3");
        Order originalOrder = shopService.addOrder(productsIds);

        Optional<Order> updatedOrderOptional = shopService.updateOrder(originalOrder.id(), originalOrder.state());

        assertFalse(updatedOrderOptional.isPresent());
    }
}
