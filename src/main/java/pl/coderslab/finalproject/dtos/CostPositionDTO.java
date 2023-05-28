package pl.coderslab.finalproject.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;


@Getter
@Setter
public class CostPositionDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Long costTypeId;

    private  String costTypeName;

    @NotNull
    @DecimalMin("0")
    private BigDecimal brutto;

    @NotNull
    @DecimalMin("0")
    private BigDecimal vatRate;






}
