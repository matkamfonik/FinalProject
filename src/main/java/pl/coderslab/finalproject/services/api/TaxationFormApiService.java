package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.TaxationForm;
import pl.coderslab.finalproject.repository.TaxationFormRepository;
import pl.coderslab.finalproject.services.interfaces.TaxationFormService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class TaxationFormApiService implements TaxationFormService {
    private final TaxationFormRepository taxationFormRepository;

    @Override
    public List<TaxationForm> getList() {
        return taxationFormRepository.findAll();
    }

    @Override
    public Optional<TaxationForm> get(Long id) {
        return taxationFormRepository.findById(id);
    }

    @Override
    public void add(TaxationForm taxationForm) {
        taxationFormRepository.save(taxationForm);
    }
}
