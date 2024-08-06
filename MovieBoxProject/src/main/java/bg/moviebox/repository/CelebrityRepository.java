package bg.moviebox.repository;

import bg.moviebox.model.entities.Celebrity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CelebrityRepository extends JpaRepository<Celebrity, Long> {

}
