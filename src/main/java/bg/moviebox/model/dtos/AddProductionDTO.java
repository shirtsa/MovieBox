package bg.moviebox.model.dtos;

import bg.moviebox.model.enums.Genre;
import bg.moviebox.model.enums.ProductionType;
import jakarta.validation.constraints.*;

public record AddProductionDTO(
        Long id,
        @NotEmpty(message = "{add.production.name.not.empty}")
        @Size(message = "{add.production.name.length}", min = 1, max = 200) String name,
        @NotEmpty
        String imageUrl,
        @NotEmpty
        String videoUrl,
        @NotNull
        @Positive
        @Min(value = 1906)
        @Max(value = 2024) Integer year,
        @NotNull
        @Positive
        Integer length,
        @PositiveOrZero
        Integer rating,

        @PositiveOrZero
        @NotNull
        Integer rentPrice,
        @NotEmpty(message = "add.description.not.empty")
        @Size(message = "add.description.length", min = 50, max = 5000) String description,
        ProductionType productionType,
        Genre genre) {

    public static AddProductionDTO empty() {
        return new AddProductionDTO(null, null, null,null, null, null, null, null, null, null, null);
    }
}
