package bg.moviebox.repository;

import bg.moviebox.model.entities.Production;
import bg.moviebox.model.enums.ProductionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {

    List<Production> findProductionsByProductionType(ProductionType productionType);

}
