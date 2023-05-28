package pl.coderslab.finalproject.mappers;

import org.springframework.stereotype.Component;
import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.dtos.RevenuePositionDTO;
import pl.coderslab.finalproject.entities.CostPosition;
import pl.coderslab.finalproject.entities.CostType;
import pl.coderslab.finalproject.entities.RevenuePosition;
import pl.coderslab.finalproject.entities.TaxMonth;


@Component
public class RevenuePositionMapper {

    public RevenuePosition toEntity(RevenuePositionDTO revenuePositionDTO, TaxMonth taxMonth) {
        RevenuePosition revenuePosition = new RevenuePosition();
        revenuePosition.setName(revenuePositionDTO.getName());
        revenuePosition.setNetto(revenuePositionDTO.getNetto());
        revenuePosition.setVatRate(revenuePositionDTO.getVatRate());
        revenuePosition.setTaxMonth(taxMonth);
        return revenuePosition;
    }

    public RevenuePositionDTO toDto(RevenuePosition revenuePosition) {
        RevenuePositionDTO revenuePositionDTO = new RevenuePositionDTO();
        revenuePositionDTO.setId(revenuePosition.getId());
        revenuePositionDTO.setName(revenuePosition.getName());
        revenuePositionDTO.setNetto(revenuePosition.getNetto());
        revenuePositionDTO.setVatRate(revenuePosition.getVatRate());
        revenuePositionDTO.setVat(revenuePosition.getVat());
        revenuePositionDTO.setBrutto(revenuePosition.getBrutto());
        return revenuePositionDTO;
    }
}
