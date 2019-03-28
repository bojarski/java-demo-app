package pl.bojarski.demoapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.bojarski.demoapp.domain.ProductFacade;
import pl.bojarski.demoapp.domain.ProductRequestDto;
import pl.bojarski.demoapp.domain.ProductResponseDto;

@RestController
@RequestMapping("/products")
class ProductEndpoint {

    private final ProductFacade productFacade;

    @Autowired
    public ProductEndpoint(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PostMapping
    ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productFacade.create(productRequestDto);
    }

    @GetMapping("/{id}")
    ProductResponseDto getProduct(@PathVariable("id") String id) {
        return productFacade.findById(id);
    }

    @PutMapping("/{id}")
    ProductResponseDto updateProduct(@PathVariable("id") String id,
                                     @RequestBody ProductRequestDto productRequestDto) {

        return productFacade.update(id, productRequestDto);
    }

    @DeleteMapping("/{id}")
    ProductResponseDto deleteProduct(@PathVariable("id") String id) {
        return productFacade.delete(id);
    }
}
