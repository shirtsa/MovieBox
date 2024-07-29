package bg.moviebox.service.impl;

import bg.moviebox.model.dtos.AddProductionDTO;
import bg.moviebox.model.dtos.ProductionDetailsDTO;
import bg.moviebox.model.dtos.ProductionSummaryDTO;
import bg.moviebox.model.entities.Production;
import bg.moviebox.model.enums.ProductionType;
import bg.moviebox.repository.ProductionRepository;
import bg.moviebox.service.ProductionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductionServiceImpl implements ProductionService {

    private final ProductionRepository productionRepository;

    public ProductionServiceImpl(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    @Override
    public long createProduction(AddProductionDTO addProductionDTO) {
       return productionRepository.save(map(addProductionDTO)).getId();
    }

    @Override
    public void deleteProduction(Long productionId) {
        productionRepository.deleteById(productionId);
    }

    @Override
    public ProductionDetailsDTO getProductionDetails(Long id) {
        return this.productionRepository
                .findById(id)
                .map(ProductionServiceImpl::toProductionDetailsDTO)
                .orElseThrow();
    }

    @Override
    public List<ProductionSummaryDTO> getAllProductionsSummary() {
        return productionRepository
                .findAll()
                .stream()
                .map(ProductionServiceImpl::toProductionSummary)
                .toList();
    }

    @Override
    public List<ProductionDetailsDTO> getAllMovieProductions() {
        List<Production> movieProductions = productionRepository.findProductionsByProductionType(ProductionType.MOVIE);
        return movieProductions
                .stream()
                .map(ProductionServiceImpl::toProductionDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductionDetailsDTO> getAllTvProductions() {
        List<Production> tvProductions = productionRepository.findProductionsByProductionType(ProductionType.TV);
        return tvProductions
                .stream()
                .map(ProductionServiceImpl::toProductionDetailsDTO)
                .collect(Collectors.toList());
    }

    private static ProductionSummaryDTO toProductionSummary(Production production) {
        return new ProductionSummaryDTO(
                production.getId(),
                production.getName(),
                production.getImageUrl(),
                production.getVideoUrl(),
                production.getYear(),
                production.getLength(),
                production.getRating(),
                production.getDescription(),
                production.getProductionType(),
                production.getGenre());
    }

    //Mapped Production entity to ProductionDetails DTO
    private static ProductionDetailsDTO toProductionDetailsDTO(Production production) {
        return new ProductionDetailsDTO(
                production.getId(),
                production.getName(),
                production.getImageUrl(),
                production.getVideoUrl(),
                production.getYear(),
                production.getLength(),
                production.getRating(),
                production.getDescription(),
                production.getProductionType(),
                production.getGenre());
    }

    //Mapped Production DTO to production entity
    private static Production map(AddProductionDTO addProductionDTO) {
        return new Production()
                .setName(addProductionDTO.name())
                .setImageUrl(addProductionDTO.imageUrl())
                .setGenre(addProductionDTO.genre())
                .setLength(addProductionDTO.length())
                .setRating(addProductionDTO.rating())
                .setVideoUrl(addProductionDTO.videoUrl())
                .setYear(addProductionDTO.year())
                .setDescription(addProductionDTO.description())
                .setProductionType(addProductionDTO.productionType());
    }
}
