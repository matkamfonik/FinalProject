package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "tax_year_id")
    private TaxYear taxYear;

    @OneToMany
    @JoinColumn(name = "id_tax_month")
    private List<RevenuePosition> revenues = new ArrayList<>();

    @NotNull
    private BigDecimal income = BigDecimal.ZERO;

    @NotNull
    private BigDecimal socialInsurace = BigDecimal.ZERO;

    @NotNull
    private BigDecimal pitValue = BigDecimal.ZERO;

    @NotNull
    private BigDecimal vatValue = BigDecimal.ZERO;

    @NotNull
    private Boolean active = true;

    @NotNull
    private Boolean upToDate = true;
}
