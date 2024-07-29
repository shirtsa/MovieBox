package bg.moviebox.model.dtos;

public record AddCelebrityDTO(
        String name,
        String imageUrl,
        String biography) {

    public static AddCelebrityDTO empty() {
        return new AddCelebrityDTO(null, null, null);
    }
}
