package pl.bojarski.demoapp.infrastucture;

import org.springframework.stereotype.Repository;
import pl.bojarski.demoapp.domain.Product;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Repository
class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>();

    @Override
    public void save(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public List<Product> findAll() {
        return products.values()
                .stream()
                .sorted(Comparator.comparing(Product::getCreatedAt))
                .collect(toList());
    }

    @Override
    public Product findById(String id) {
        return products.get(id);
    }

    @Override
    public void remove(String id) {
        products.remove(id);
    }
}
