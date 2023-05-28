package pl.coderslab.finalproject.mappers;

import org.springframework.stereotype.Component;
import pl.coderslab.finalproject.dtos.TaxMonthDTO;
import pl.coderslab.finalproject.entities.TaxMonth;
import pl.coderslab.finalproject.entities.TaxYear;
import pl.coderslab.finalproject.entities.User;


@Component
public class TaxMonthMapper {

    public TaxMonth toEntity(TaxMonthDTO taxMonthDTO, User user, TaxYear taxYear) {
        TaxMonth taxMonth = new TaxMonth();
        taxMonth.setNumber(taxMonthDTO.getNumber());
        taxMonth.setTaxYear(taxYear);
        taxMonth.setIncome(taxMonthDTO.getIncome());
        taxMonth.setSocialInsurance(taxMonthDTO.getSocialInsurance());
        taxMonth.setPitValue(taxMonthDTO.getPitValue());
        taxMonth.setVatValue(taxMonthDTO.getVatValue());
        taxMonth.setUpToDate(taxMonthDTO.getUpToDate());
        taxMonth.setActive(true);
        return taxMonth;
    }

    public TaxMonthDTO toDto(TaxMonth taxMonth) {
        TaxMonthDTO taxMonthDTO = new TaxMonthDTO();
        taxMonthDTO.setId(taxMonth.getId());
        taxMonthDTO.setNumber(taxMonth.getNumber());
        taxMonthDTO.setIncome(taxMonth.getIncome());
        taxMonthDTO.setSocialInsurance(taxMonth.getSocialInsurance());
        taxMonthDTO.setPitValue(taxMonth.getPitValue());
        taxMonthDTO.setVatValue(taxMonth.getVatValue());
        taxMonthDTO.setUpToDate(taxMonth.getUpToDate());
        return taxMonthDTO;
    }
}
