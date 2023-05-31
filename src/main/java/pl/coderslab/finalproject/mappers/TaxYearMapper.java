package pl.coderslab.finalproject.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.services.api.BusinessApiService;


@Component
@RequiredArgsConstructor
public class TaxYearMapper {

    private final BusinessApiService businessService;

    public TaxYear toEntity(TaxYearDTO taxYearDTO, Long businessId) {
        TaxYear taxYear = new TaxYear();
        taxYear.setYear(taxYearDTO.getYear());
        taxYear.setBusiness(businessService.get(businessId).get());
        taxYear.setBalance(taxYearDTO.getBalance());
        taxYear.setVatBalance(taxYearDTO.getVatBalance());
        taxYear.setUpToDate(taxYearDTO.getUpToDate());
        taxYear.setActive(true);
        return taxYear;
    }

    public TaxYearDTO toDto(TaxYear taxYear) {
        TaxYearDTO taxYearDTO = new TaxYearDTO();
        taxYearDTO.setId(taxYear.getId());
        taxYearDTO.setYear(taxYear.getYear());
        taxYearDTO.setBalance(taxYear.getBalance());
        taxYearDTO.setVatBalance(taxYear.getVatBalance());
        taxYearDTO.setUpToDate(taxYear.getUpToDate());
        return taxYearDTO;
    }
}
