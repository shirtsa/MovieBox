package bg.moviebox.model.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddNewsDTO(
        Long id,

        @NotNull(message = "{add.news.name.length}")
        @Size(message = "{add.news.name.length}", min = 5, max = 100)
        String name,

        @NotEmpty
        String firstImageUrl,

        @NotEmpty
        String secondImageUrl,

        @NotEmpty
        String trailerUrl,
        @NotNull(message = "{add.news.description.length}")
        @Size(message = "{add.news.description.length}", min = 50, max = 5000)
        String description,

        String newsType) {
    public static AddNewsDTO empty() {
        return new AddNewsDTO(null, null, null, null, null, null, null);
    }
}
