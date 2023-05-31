package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.CostType;
import pl.coderslab.finalproject.repository.CostTypeRepository;
import pl.coderslab.finalproject.services.interfaces.CostTypeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class CostTypeApiService implements CostTypeService {
    private final CostTypeRepository costTypeRepository;

    @Override
    public List<CostType> getList() {
        return costTypeRepository.findAll();
    }

    @Override
    public Optional<CostType> get(Long id) {
        return costTypeRepository.findById(id);
    }

    @Override
    public void add(CostType costType) {
        costTypeRepository.save(costType);
    }
}
