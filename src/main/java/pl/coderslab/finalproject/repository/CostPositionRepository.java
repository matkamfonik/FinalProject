package pl.coderslab.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.CostPosition;

import java.util.List;

public interface CostPositionRepository extends JpaRepository <CostPosition, Long> {

    List<CostPosition> findAllByTaxMonthId(Long taxMonthId);
}
