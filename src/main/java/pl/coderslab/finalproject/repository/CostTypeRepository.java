package pl.coderslab.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.finalproject.entities.CostType;

public interface CostTypeRepository extends JpaRepository <CostType, Long> {
}
