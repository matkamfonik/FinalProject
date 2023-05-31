package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dtos.TaxMonthDTO;
import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.entities.TaxationForm;
import pl.coderslab.finalproject.mappers.TaxMonthMapper;
import pl.coderslab.finalproject.repository.TaxMonthRepository;
import pl.coderslab.finalproject.services.calculation.TaxMonthCalculationService;
import pl.coderslab.finalproject.services.interfaces.TaxMonthService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class TaxMonthApiService implements TaxMonthService {
    private final TaxMonthRepository taxMonthRepository;

    private final TaxMonthCalculationService taxMonthCalculationService;

    private final TaxMonthMapper taxMonthMapper;


    @Override
    public List<TaxMonthDTO> findByTaxYearIdOrderByNumberAsc(Long yearId) {
        return taxMonthRepository.findByTaxYearIdOrderByNumberAsc(yearId).stream().map(taxMonthMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TaxMonthDTO> findByTaxYearIdAndNumberGreaterThan(Long taxYearId, Integer monthNumber) {
        return taxMonthRepository.findByTaxYearIdAndNumberGreaterThan(taxYearId, monthNumber).stream().map(taxMonthMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public TaxMonthDTO findFirstByTaxYearIdOrderByNumberDesc(Long taxYearId) {
        return taxMonthMapper.toDto(taxMonthRepository.findFirstByTaxYearIdOrderByNumberDesc(taxYearId).orElse(new TaxMonth()));
    }

    @Override
    public Optional<TaxMonth> findPrevious(Long taxMonthId) {
        TaxMonth taxMonth = taxMonthRepository.findById(taxMonthId).get();
        Optional<TaxMonth> previousTaxMonth;
        if (taxMonth.getNumber() > 1) {
            previousTaxMonth = taxMonthRepository.findByTaxYearIdAndNumber(taxMonth.getTaxYear().getId(), taxMonth.getNumber() - 1);
        } else {
            previousTaxMonth = taxMonthRepository.findFirstByTaxYearYearAndNumber(taxMonth.getTaxYear().getYear() - 1, 12);
        }
        return previousTaxMonth;
    }

    @Override
    public void update(Long taxMonthId, Long businessId) {
        TaxMonth taxMonth = this.get(taxMonthId).get();
        TaxationForm taxationForm = taxMonth.getTaxYear().getBusiness().getTaxationForm();
        TaxMonth previousMonth = this.findPrevious(taxMonth.getId()).orElse(new TaxMonth());
        taxMonthCalculationService.calculate(taxMonth, taxationForm, previousMonth);
        taxMonthRepository.save(taxMonth);
        this.setNextMonthsNotUpToDate(taxMonthId, taxMonth.getTaxYear());
    }

    @Override
    public TaxMonthDTO getDTO(Long taxMonthId) {
        return taxMonthRepository.findById(taxMonthId).map(taxMonthMapper::toDto).orElse(new TaxMonthDTO());
    }

    @Override
    public Optional<TaxMonth> get(Long taxMonthId) {
        return taxMonthRepository.findById(taxMonthId);
    }

    @Override
    public void add(TaxMonthDTO taxMonthDTO, TaxYear taxYear) {

        TaxMonth taxMonth = taxMonthMapper.toEntity(taxMonthDTO, taxYear);
        taxMonthRepository.save(taxMonth);

        this.setNextMonthsNotUpToDate(taxMonth.getId(), taxYear);
    }

    @Override
    public void delete(Long id){

        this.setNextMonthsNotUpToDate(id, this.get(id).get().getTaxYear());

        taxMonthRepository.deleteById(id);

    }

    @Override
    public void patch(TaxMonthDTO taxMonthDTO, TaxYear taxYear) {
        TaxMonth taxMonth = taxMonthMapper.toEntity(taxMonthDTO, taxYear);
        taxMonthRepository.save(taxMonth);
    }

    @Override
    public List<TaxMonth> findNextMonths(Long yearId, Integer taxMonthNumber, Integer taxYearYear) {
        return taxMonthRepository.findNextMonths(yearId, taxMonthNumber, taxYearYear);
    }


    public void setNextMonthsNotUpToDate(Long taxMonthId, TaxYear taxYear) {
        TaxMonth taxMonth = this.get(taxMonthId).get();
        this.findNextMonths(taxMonth.getTaxYear().getId(), taxMonth.getNumber(), taxMonth.getTaxYear().getYear())
                .forEach(tm -> {
                    tm.setUpToDate(false);
                    this.patch(taxMonthMapper.toDto(tm), tm.getTaxYear());
                });
    }



}
