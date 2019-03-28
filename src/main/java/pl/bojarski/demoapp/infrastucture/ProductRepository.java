package pl.bojarski.demoapp.infrastucture;

import pl.bojarski.demoapp.domain.Product;

public interface ProductRepository {

    void save(Product product);

    //SPEL
    Product findById(String id);

    void remove(String id);
}
