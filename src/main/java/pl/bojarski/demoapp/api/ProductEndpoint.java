package pl.bojarski.demoapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.bojarski.demoapp.domain.ProductFacade;
import pl.bojarski.demoapp.domain.ProductRequestDto;
import pl.bojarski.demoapp.domain.ProductResponseDto;
import pl.bojarski.demoapp.domain.ProductUpdateRequestDto;

@RestController
@RequestMapping("/api/v1/products")
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

    @PutMapping
    ProductResponseDto updateProduct(@RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        return productFacade.update(productUpdateRequestDto);
    }

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable("id") String id) {
        productFacade.delete(id);
    }
}
