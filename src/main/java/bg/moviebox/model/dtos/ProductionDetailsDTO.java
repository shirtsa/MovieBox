package bg.moviebox.model.dtos;

import bg.moviebox.model.enums.Genre;
import bg.moviebox.model.enums.ProductionType;

public record ProductionDetailsDTO(
        Long id,
        String name,
        String imageUrl,
        String videoUrl,
        Integer year,
        Integer length,
        Integer rating,
        String description,
        ProductionType productionType,
        Genre genre) {

}
