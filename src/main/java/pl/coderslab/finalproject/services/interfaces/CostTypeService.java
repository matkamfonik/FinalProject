package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.entities.CostType;

import java.util.List;
import java.util.Optional;

public interface CostTypeService {
    List<CostType> getList();

    Optional<CostType> get(Long id);

    void add(CostType costType);
}
