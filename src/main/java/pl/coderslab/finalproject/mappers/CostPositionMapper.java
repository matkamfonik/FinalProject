package pl.coderslab.finalproject.mappers;

import org.springframework.stereotype.Component;
import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.entities.*;


@Component
public class CostPositionMapper {

    public CostPosition toEntity(CostPositionDTO costPositionDTO, TaxMonth taxMonth, CostType costType) {
        CostPosition costPosition = new CostPosition();
        costPosition.setName(costPositionDTO.getName());
        costPosition.setNetto(costPositionDTO.getNetto());
        costPosition.setVatRate(costPositionDTO.getVatRate());
        costPosition.setTaxMonth(taxMonth);
        costPosition.setCostType(costType);
        return costPosition;
    }

    public CostPositionDTO toDto(CostPosition costPosition) {
        CostPositionDTO costPositionDTO = new CostPositionDTO();
        costPositionDTO.setId(costPosition.getId());
        costPositionDTO.setName(costPosition.getName());
        costPositionDTO.setCostTypeName(costPosition.getCostType().getName());
        costPositionDTO.setCostTypeId(costPosition.getCostType().getId());
        costPositionDTO.setNetto(costPosition.getNetto());
        costPositionDTO.setVatRate(costPosition.getVatRate());
        costPositionDTO.setVat(costPosition.getVat());
        costPositionDTO.setBrutto(costPosition.getBrutto());
        costPositionDTO.setVatDeducted(costPosition.getVatDeducted());
        costPositionDTO.setCostIncluded(costPosition.getCostIncluded());
        return costPositionDTO;
    }
}
