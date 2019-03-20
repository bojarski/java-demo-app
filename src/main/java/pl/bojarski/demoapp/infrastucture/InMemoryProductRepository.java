package pl.bojarski.demoapp.infrastucture;

import org.springframework.stereotype.Repository;
import pl.bojarski.demoapp.domain.Product;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>();

    @Override
    public void save(Product product) {
        products.put(product.getId(), product);
    }
}
