package bg.moviebox.service.impl;

import bg.moviebox.model.dtos.AddProductionDTO;
import bg.moviebox.model.dtos.ProductionDetailsDTO;
import bg.moviebox.model.dtos.ProductionSummaryDTO;
import bg.moviebox.model.entities.Production;
import bg.moviebox.model.enums.ProductionType;
import bg.moviebox.repository.ProductionRepository;
import bg.moviebox.service.ExchangeRateService;
import bg.moviebox.service.ProductionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductionServiceImpl implements ProductionService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductionServiceImpl.class);
    private final RestClient productionRestClient;
    private final ProductionRepository productionRepository;
    private final ExchangeRateService exchangeRateService;

    public ProductionServiceImpl(@Qualifier("productionsRestClient") RestClient productionRestClient,
                                 ProductionRepository productionRepository,
                                 ExchangeRateService exchangeRateService) {
        this.productionRestClient = productionRestClient;
        this.productionRepository = productionRepository;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public void createProduction(AddProductionDTO addProductionDTO) {
        LOGGER.info("Creating new production...");

        productionRestClient
                .post()
                .uri("http://localhost:8081/productions")
                .body(addProductionDTO)
                .retrieve();
    }

    @Override
    public void deleteProduction(Long productionId) {
        productionRepository.deleteById(productionId);
    }

    @Override
    public ProductionDetailsDTO getProductionDetails(Long id) {
        return productionRestClient
                .get()
                .uri("http://localhost:8081/productions/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ProductionDetailsDTO.class);
//        return this.productionRepository
//                .findById(id)
//                .map(this::toProductionDetailsDTO)
//                .orElseThrow(() -> new ObjectNotFoundException("Production not found!", id));
    }

    @Override
    public List<ProductionSummaryDTO> getAllProductionsSummary() {
        LOGGER.info("Get all productions...");

        return productionRestClient
                .get()
                .uri("http://localhost:8081/productions")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
//        return productionRepository
//                .findAll()
//                .stream()
//                .map(ProductionServiceImpl::toProductionSummary)
//                .toList();
    }

    @Override
    public List<ProductionDetailsDTO> getAllMovieProductions() {
        List<Production> movieProductions = productionRepository.findProductionsByProductionType(ProductionType.MOVIE);
        return movieProductions
                .stream()
                .map(this::toProductionDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductionDetailsDTO> getAllTvProductions() {
        List<Production> tvProductions = productionRepository.findProductionsByProductionType(ProductionType.TV);
        return tvProductions
                .stream()
                .map(this::toProductionDetailsDTO)
                .collect(Collectors.toList());
    }

    //delete last 3
    private static ProductionSummaryDTO toProductionSummary(Production production) {
        return new ProductionSummaryDTO(
                production.getId(),
                production.getName(),
                production.getImageUrl(),
                production.getVideoUrl(),
                production.getYear(),
                production.getLength(),
                production.getRating(),
                production.getRentPrice(),
                production.getDescription(),
                production.getProductionType(),
                production.getGenre());
    }

    //Mapped Production entity to ProductionDetails DTO
    private ProductionDetailsDTO toProductionDetailsDTO(Production production) {
        return new ProductionDetailsDTO(
                production.getId(),
                production.getName(),
                production.getImageUrl(),
                production.getVideoUrl(),
                production.getYear(),
                production.getLength(),
                production.getRating(),
                production.getRentPrice(),
                production.getDescription(),
                production.getProductionType(),
                production.getGenre(),
                exchangeRateService.allSupportedCurrencies());
    }

    //Mapped Production DTO to production entity
    private static Production map(AddProductionDTO addProductionDTO) {
        return new Production()
                .setName(addProductionDTO.name())
                .setImageUrl(addProductionDTO.imageUrl())
                .setGenre(addProductionDTO.genre())
                .setLength(addProductionDTO.length())
                .setRating(addProductionDTO.rating())
                .setRentPrice(addProductionDTO.rentPrice())
                .setVideoUrl(addProductionDTO.videoUrl())
                .setYear(addProductionDTO.year())
                .setDescription(addProductionDTO.description())
                .setProductionType(addProductionDTO.productionType());
    }
}
