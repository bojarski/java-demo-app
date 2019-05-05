package pl.bojarski.demoapp.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String id) {
        super("There is no product with id: " + id);
    }
}