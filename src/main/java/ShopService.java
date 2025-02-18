import java.util.*;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            if (productRepo.getProductById(productId).isPresent()){
                Product productToOrder = productRepo.getProductById(productId).get();
                products.add(productToOrder);
            } else {
                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                throw new NoSuchElementException();
            }
        }
        Order newOrder = new Order(UUID.randomUUID().toString(), products);
        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrderListByState(OrderState state) {
        return orderRepo.getOrders().stream()
                .filter(order -> order.state().equals(state))
                .toList();
    }

    public Optional<Order> updateOrder(String id, OrderState newOrderState) {
        Order existingOrder = orderRepo.getOrderById(id);
        if (existingOrder == null ) {
            return Optional.empty();
        }
        if (existingOrder.state() == newOrderState){
            return Optional.empty();
        }
        Order updatedOrder = existingOrder.withState(newOrderState);
        orderRepo.removeOrder(id);
        return Optional.of(orderRepo.addOrder(updatedOrder));
    }
}
