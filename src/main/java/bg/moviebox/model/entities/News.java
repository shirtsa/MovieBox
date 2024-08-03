package bg.moviebox.model.entities;

import bg.moviebox.model.enums.NewsType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "news")
public class News extends BaseEntity {

    private String name;

    private String firstImageUrl;

    private String secondImageUrl;

    private String trailerUrl;

    private String description;

    @Enumerated(EnumType.STRING)
    private NewsType newsType;

    public String getName() {
        return name;
    }

    public News setName(String name) {
        this.name = name;
        return this;
    }

    public String getFirstImageUrl() {
        return firstImageUrl;
    }

    public News setFirstImageUrl(String firstImageUrl) {
        this.firstImageUrl = firstImageUrl;
        return this;
    }

    public String getSecondImageUrl() {
        return secondImageUrl;
    }

    public News setSecondImageUrl(String secondImageUrl) {
        this.secondImageUrl = secondImageUrl;
        return this;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public News setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public News setDescription(String description) {
        this.description = description;
        return this;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public News setNewsType(NewsType newsType) {
        this.newsType = newsType;
        return this;
    }

    @Override
    public String toString() {
        return "News{" +
                "name='" + name + '\'' +
                ", firstImageUrl='" + firstImageUrl + '\'' +
                ", secondImageUrl='" + secondImageUrl + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", description='" + description + '\'' +
                ", newsType=" + newsType +
                '}';
    }
}
