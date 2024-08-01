package bg.moviebox.service;

import bg.moviebox.model.dtos.AddProductionDTO;
import bg.moviebox.model.dtos.ProductionDetailsDTO;
import bg.moviebox.model.dtos.ProductionSummaryDTO;
import bg.moviebox.model.enums.ProductionType;

import java.util.List;

public interface ProductionService {

    void createProduction(AddProductionDTO addProductionDTO); //long

    void deleteProduction(Long productionId);

    ProductionDetailsDTO getProductionDetails(Long id);

    List<ProductionSummaryDTO> getAllProductionsSummary();

    List<ProductionDetailsDTO> getAllMovieProductions();

    List<ProductionDetailsDTO> getAllTvProductions();

}
