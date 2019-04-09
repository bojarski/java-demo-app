package pl.bojarski.demoapp.infrastucture;

import pl.bojarski.demoapp.domain.Product;

import java.util.List;

public interface ProductRepository {

    void save(Product product);

    List<Product> findAll();

    //SPEL
    Product findById(String id);

    void remove(String id);
}
