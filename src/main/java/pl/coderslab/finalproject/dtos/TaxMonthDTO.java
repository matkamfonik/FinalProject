package pl.coderslab.finalproject.dtos;

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

    private BigDecimal income = BigDecimal.valueOf(0L, 2);

    private BigDecimal revenue = BigDecimal.valueOf(0L, 2);

    private BigDecimal costs = BigDecimal.valueOf(0L, 2);

    private BigDecimal socialInsurance = BigDecimal.valueOf(0L, 2);

    private BigDecimal pitValue = BigDecimal.valueOf(0L, 2);

    private BigDecimal vatValue = BigDecimal.valueOf(0L, 2);

    private Boolean upToDate = true;
}
