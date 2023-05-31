package pl.coderslab.finalproject.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@Getter
@Setter
public class RevenuePositionDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin("0")
    private BigDecimal netto = BigDecimal.valueOf(0L, 2);

    @NotNull
    @DecimalMin("0")
    private BigDecimal vatRate = BigDecimal.valueOf(0L, 2);

    private BigDecimal vat = BigDecimal.valueOf(0L, 2);

    private BigDecimal brutto = BigDecimal.valueOf(0L, 2);

}
