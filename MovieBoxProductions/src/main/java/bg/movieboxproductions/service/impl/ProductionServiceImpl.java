package bg.movieboxproductions.service.impl;

import bg.movieboxproductions.model.dtos.AddProductionDTO;
import bg.movieboxproductions.model.dtos.ProductionDTO;
import bg.movieboxproductions.model.entities.Production;
import bg.movieboxproductions.repository.ProductionRepository;
import bg.movieboxproductions.service.ProductionService;

import bg.movieboxproductions.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionServiceImpl implements ProductionService {

    private final ProductionRepository productionRepository;

    public ProductionServiceImpl(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    @Override
    public ProductionDTO createProduction(AddProductionDTO addProductionDTO) {
        Production production = productionRepository.save(map(addProductionDTO));
        return map(production);
    }

    @Override
    public void deleteProduction(Long productionId) {
        productionRepository.deleteById(productionId);
    }

    @Override
    public ProductionDTO getProductionById(Long productionId) {
        return productionRepository
                .findById(productionId)
                .map(ProductionServiceImpl::map)
                .orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    public List<ProductionDTO> getAllProductions() {
        return productionRepository
                .findAll()
                .stream()
                .map(ProductionServiceImpl::map).toList();
    }

    private static ProductionDTO map(Production production) {
        return new ProductionDTO(
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