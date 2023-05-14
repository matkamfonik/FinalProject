package pl.coderslab.finalproject.services.interfaces;

import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.TaxationForm;

import java.util.List;
import java.util.Optional;

public interface TaxationFormService {
    List<TaxationForm> getList();

    Optional<TaxationForm> get(Long id);

    void add(TaxationForm taxationForm);
}
