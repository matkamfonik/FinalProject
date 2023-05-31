package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "revenue_positions")
@Getter
@Setter
public class RevenuePosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin("0")
    private BigDecimal netto;

    @NotNull
    @DecimalMin("0")
    private BigDecimal vatRate;

    private BigDecimal vat;

    private BigDecimal brutto;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "tax_month_id")
    private TaxMonth taxMonth;

}
