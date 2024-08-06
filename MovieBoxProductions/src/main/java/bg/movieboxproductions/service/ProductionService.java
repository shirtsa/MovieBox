package bg.movieboxproductions.service;

import bg.movieboxproductions.model.dtos.AddProductionDTO;
import bg.movieboxproductions.model.dtos.ProductionDTO;

import java.util.List;

public interface ProductionService {

    ProductionDTO createProduction(AddProductionDTO addProductionDTO);

    void deleteProduction(Long productionId);

    ProductionDTO getProductionById(Long productionId);

    List<ProductionDTO> getAllProductions();
}
