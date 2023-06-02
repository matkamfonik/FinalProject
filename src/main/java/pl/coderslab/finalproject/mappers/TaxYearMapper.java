package pl.coderslab.finalproject.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.coderslab.finalproject.dtos.TaxYearDTO;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.TaxYear;

@Component
@RequiredArgsConstructor
public class TaxYearMapper {

    public TaxYear toEntity(TaxYearDTO taxYearDTO, Business business) {
        TaxYear taxYear = new TaxYear();
        taxYear.setId(taxYearDTO.getId());
        taxYear.setYear(taxYearDTO.getYear());
        taxYear.setBusiness(business);
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
