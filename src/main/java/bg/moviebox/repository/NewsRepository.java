package bg.moviebox.repository;

import bg.moviebox.model.entities.News;
import bg.moviebox.model.enums.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByNewsType(NewsType newsType);

    News findFirstByNewsType(NewsType newsType);
}
