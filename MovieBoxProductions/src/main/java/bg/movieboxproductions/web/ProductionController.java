package bg.movieboxproductions.web;

import bg.movieboxproductions.model.dtos.AddProductionDTO;
import bg.movieboxproductions.model.dtos.ProductionDTO;
import bg.movieboxproductions.service.ProductionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/productions")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductionDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(productionService.getProductionById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductionDTO> deleteById(@PathVariable("id") Long id) {
        productionService.deleteProduction(id);
        return ResponseEntity
                .noContent()
                .build();
    }


    @GetMapping
    public ResponseEntity<List<ProductionDTO>> getAllProductions() {
        return ResponseEntity.ok(productionService.getAllProductions());
    }

    @PostMapping
    public ResponseEntity<ProductionDTO> createProduction(@RequestBody AddProductionDTO addProductionDTO) {

        ProductionDTO productionDTO = productionService.createProduction(addProductionDTO);
        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(productionDTO.id())
                                .toUri()
                ).body(productionDTO);
    }
}
