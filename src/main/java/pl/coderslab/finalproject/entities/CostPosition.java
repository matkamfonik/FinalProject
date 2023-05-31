package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "cost_positions")
@Getter
@Setter
public class CostPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "cost_type_id")
    private CostType costType;

    @NotNull
    @DecimalMin("0")
    private BigDecimal netto;

    @NotNull
    @DecimalMin("0")
    private BigDecimal vatRate;

    private BigDecimal vat;

    private BigDecimal brutto;

    private BigDecimal vatDeducted;

    private BigDecimal costIncluded;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tax_month_id")
    private TaxMonth taxMonth;

}
