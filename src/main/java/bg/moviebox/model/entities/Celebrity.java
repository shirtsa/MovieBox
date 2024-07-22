package bg.moviebox.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "celebrities")
public class Celebrity extends BaseEntity {

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String name;

    @Column(nullable = false)
    @NotEmpty
    private String imageUrl;

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 20)
    private String biography;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "celebrities_movie_productions",
            joinColumns = @JoinColumn(name = "celebrity_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Production> knownForMovies;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "celebrities_tv_productions",
            joinColumns = @JoinColumn(name = "celebrity_id"),
            inverseJoinColumns = @JoinColumn(name = "tv_id"))
    private Set<Production> knownForTvShows;

    public Celebrity() {
        this.knownForMovies = new HashSet<>();
        this.knownForTvShows = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String fullName) {
        this.name = fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Set<Production> getKnownForMovies() {
        return knownForMovies;
    }

    public void setKnownForMovies(Set<Production> knownForMovies) {
        this.knownForMovies = knownForMovies;
    }

    public Set<Production> getKnownForTvShows() {
        return knownForTvShows;
    }

    public void setKnownForTvShows(Set<Production> knownForTvShows) {
        this.knownForTvShows = knownForTvShows;
    }
}
