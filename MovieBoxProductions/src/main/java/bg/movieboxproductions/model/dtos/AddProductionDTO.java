package bg.movieboxproductions.model.dtos;

import bg.movieboxproductions.model.enums.Genre;
import bg.movieboxproductions.model.enums.ProductionType;

public record AddProductionDTO(
        Long id,
        String name,
        String imageUrl,
        String videoUrl,
        Integer year,
        Integer length,
        Integer rating,
        Integer rentPrice,
        String description,
        ProductionType productionType,
        Genre genre) {
}