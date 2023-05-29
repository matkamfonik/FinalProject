package pl.coderslab.finalproject.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TaxYearDTO {

    private Long id;

    @NotNull
    @Digits(integer = 4, fraction = 0)
    private int year;

    private BigDecimal balance = BigDecimal.valueOf(0L, 2);

    private BigDecimal vatBalance = BigDecimal.valueOf(0L, 2);

    private Boolean upToDate = true;
}
