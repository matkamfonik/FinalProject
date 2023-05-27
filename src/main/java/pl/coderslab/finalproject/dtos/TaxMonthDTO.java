package pl.coderslab.finalproject.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TaxMonthDTO {

    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 12)
    private Integer number;

    private BigDecimal income = BigDecimal.ZERO;

    private BigDecimal socialInsurance = BigDecimal.ZERO;

    private BigDecimal pitValue = BigDecimal.ZERO;

    private BigDecimal vatValue = BigDecimal.ZERO;

    private Boolean upToDate = true;
}
