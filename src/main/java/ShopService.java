import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                return null;
            }


        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrderListByState(OrderState state) {
        return orderRepo.getOrders().stream()
                .filter(order -> order.status().equals(state))
                .toList();
    }
}
