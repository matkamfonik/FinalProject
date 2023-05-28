package pl.coderslab.finalproject.mappers;

import org.springframework.stereotype.Component;
import pl.coderslab.finalproject.dtos.BusinessDTO;
import pl.coderslab.finalproject.dtos.CostPositionDTO;
import pl.coderslab.finalproject.entities.*;


@Component
public class CostPositionMapper {

    public CostPosition toEntity(CostPositionDTO costPositionDTO, TaxMonth taxMonth, CostType costType) {
        CostPosition costPosition = new CostPosition();
        costPosition.setName(costPositionDTO.getName());
        costPosition.setBrutto(costPositionDTO.getBrutto());
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
        costPositionDTO.setBrutto(costPosition.getBrutto());
        costPositionDTO.setVatRate(costPosition.getVatRate());
        return costPositionDTO;
    }
}
