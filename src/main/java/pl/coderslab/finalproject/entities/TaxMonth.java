package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "tax_months")
public class TaxMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer number =0;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tax_year_id")
    private TaxYear taxYear;

    @NotNull
    private BigDecimal income = BigDecimal.ZERO;

    private BigDecimal revenue = BigDecimal.ZERO;

    private BigDecimal costs = BigDecimal.ZERO;

    @NotNull
    private BigDecimal socialInsurance = BigDecimal.ZERO;

    @NotNull
    private BigDecimal pitValue = BigDecimal.ZERO;

    @NotNull
    private BigDecimal vatValue = BigDecimal.ZERO;

    @NotNull
    private Boolean active = true;

    @NotNull
    private Boolean upToDate = true;
}
