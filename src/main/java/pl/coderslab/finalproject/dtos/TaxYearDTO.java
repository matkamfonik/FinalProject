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

    private BigDecimal balance = BigDecimal.ZERO;

    private BigDecimal vatBalance = BigDecimal.ZERO;

    private Boolean upToDate = true;
}
