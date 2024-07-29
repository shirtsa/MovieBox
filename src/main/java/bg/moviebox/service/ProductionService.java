package bg.moviebox.service;

import bg.moviebox.model.dtos.AddProductionDTO;
import bg.moviebox.model.dtos.ProductionDetailsDTO;
import bg.moviebox.model.dtos.ProductionSummaryDTO;
import bg.moviebox.model.enums.ProductionType;

import java.util.List;

public interface ProductionService {

    long createProduction(AddProductionDTO addProductionDTO);

    void deleteProduction(Long productionId);

    ProductionDetailsDTO getProductionDetails(Long id);

    List<ProductionSummaryDTO> getAllProductionsSummary();

    List<ProductionDetailsDTO> getAllMovieProductions();

    List<ProductionDetailsDTO> getAllTvProductions();

}
