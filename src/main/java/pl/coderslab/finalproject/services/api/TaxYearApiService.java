package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dtos.TaxMonthDTO;
import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.mappers.TaxYearMapper;
import pl.coderslab.finalproject.repository.TaxYearRepository;
import pl.coderslab.finalproject.services.calculation.TaxYearCalculationService;
import pl.coderslab.finalproject.services.interfaces.TaxYearService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class TaxYearApiService implements TaxYearService {
    private final TaxYearRepository taxYearRepository;

    private final TaxYearMapper taxYearMapper;

    private final TaxYearCalculationService taxYearCalculationService;

    private final TaxMonthApiService taxMonthService;

    @Override
    public List<TaxYear> findAllTaxYears(Long businessId) {
        return taxYearRepository.findAllTaxYearNameByBusinessIdOrderByYearAsc(businessId);
    }

    @Override
    public TaxYearDTO findByYearAndBusinessId(int year, Long businessId) {
        return taxYearRepository.findByYearAndBusinessId(year, businessId).map(taxYearMapper::toDto).orElse(new TaxYearDTO());
    }

    @Override
    public List<TaxYearDTO> findByBusinessIdAndYearGreaterThan(Long businessId, int year) {
        return taxYearRepository.findByBusinessIdAndYearGreaterThan(businessId, year).stream().map(taxYearMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public TaxYearDTO getDTO(Long id) {
        return taxYearRepository.findById(id).map(taxYearMapper::toDto).orElse(new TaxYearDTO());
    }

    @Override
    public Optional<TaxYear> get(Long id){
        return taxYearRepository.findById(id);
    }

    @Override
    public void save(TaxYearDTO taxYearDTO, Long businessId) {
        TaxYear taxYear = taxYearMapper.toEntity(taxYearDTO, businessId);
        TaxYearDTO previousTaxYear = this.findByYearAndBusinessId(taxYear.getYear() - 1, taxYear.getBusiness().getId());
        List<TaxMonthDTO> months = taxMonthService.findByTaxYearIdOrderByNumberAsc(taxYear.getId());
        taxYearCalculationService.calculate(taxYear, previousTaxYear, months);
        taxYearRepository.save(taxYear);

        this.setNextMonthsNotUpToDate(taxYear, businessId);
    }

    @Override
    public void delete(Long id){
        TaxYear taxYear = this.get(id).get();
        this.setNextMonthsNotUpToDate(taxYear, taxYear.getBusiness().getId());
        taxYearRepository.deleteById(id);
    }

    @Override
    public void update(Long taxYearId, Long businessId){
        TaxYear taxYear = this.get(taxYearId).get();
        TaxYearDTO previousTaxYear = this.findByYearAndBusinessId(taxYear.getYear() - 1, taxYear.getBusiness().getId());
        List<TaxMonthDTO> months = taxMonthService.findByTaxYearIdOrderByNumberAsc(taxYear.getId());
        taxYearCalculationService.calculate(taxYear, previousTaxYear, months);
        taxYearRepository.save(taxYear);
        this.setNextMonthsNotUpToDate(taxYear, businessId);
    }

    public void setNextMonthsNotUpToDate(TaxYear taxYear, Long businessId) {
        List<TaxYearDTO> newerYears = this.findByBusinessIdAndYearGreaterThan(businessId, taxYear.getYear());
        newerYears.forEach(ty -> {
            ty.setUpToDate(false);
            this.patch(ty, businessId);
            taxMonthService.findByTaxYearIdOrderByNumberAsc(ty.getId()).forEach(tm -> {
                tm.setUpToDate(false);
                taxMonthService.patch(tm, taxYearMapper.toEntity(ty,businessId));
            });
        });
    }
    @Override
    public void patch(TaxYearDTO taxYearDTO, Long businessId){
        TaxYear taxYear = taxYearMapper.toEntity(taxYearDTO, businessId);
        taxYearRepository.save(taxYear);
    }

}
