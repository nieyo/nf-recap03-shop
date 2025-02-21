import lombok.With;

import java.time.Instant;
import java.util.List;

public record Order(
        String id,
        List<Product> products,
        @With OrderState state,
        Instant timestamp
) {
    public Order(String id, List<Product> products, Instant timestamp) {
        this(id, products, OrderState.PROCESSING, timestamp);
    }
}
