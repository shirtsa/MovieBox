package bg.moviebox.model.entities;

import bg.moviebox.model.enums.Genre;
import bg.moviebox.model.enums.ProductionType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "productions")
public class Production extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Column
    private String videoUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductionType productionType;

    @Column
    private Integer length;

    @Column
    private Integer rating;

    @Column
    private Integer year;

    @ManyToMany(mappedBy = "knownForMovies")
    private Set<Celebrity> celebritiesInTheMovie;

    @ManyToMany(mappedBy = "knownForTvShows")
    private Set<Celebrity> celebritiesInTheTvShow;

    public Production() {
        this.celebritiesInTheMovie = new HashSet<>();
        this.celebritiesInTheTvShow = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Production setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Production setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public Production setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public Integer getLength() {
        return length;
    }

    public Production setLength(Integer length) {
        this.length = length;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public Production setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Production setVideoUrl(String trailer) {
        this.videoUrl = trailer;
        return this;
    }

    public ProductionType getProductionType() {
        return productionType;
    }

    public Production setProductionType(ProductionType productionType) {
        this.productionType = productionType;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public Production setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Set<Celebrity> getCelebritiesInTheMovie() {
        return celebritiesInTheMovie;
    }

    public Production setCelebritiesInTheMovie(Set<Celebrity> celebritiesInTheMovie) {
        this.celebritiesInTheMovie = celebritiesInTheMovie;
        return this;
    }

    public Set<Celebrity> getCelebritiesInTheTvShow() {
        return celebritiesInTheTvShow;
    }

    public Production setCelebritiesInTheTvShow(Set<Celebrity> celebritiesInTheTvShow) {
        this.celebritiesInTheTvShow = celebritiesInTheTvShow;
        return this;
    }
}
