package pl.coderslab.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.finalproject.entities.CostPosition;
import pl.coderslab.finalproject.entities.RevenuePosition;

import java.util.List;

public interface RevenuePositionRepository extends JpaRepository <RevenuePosition, Long> {

    List<RevenuePosition> findAllByTaxMonthId(Long taxMonthId);
}
