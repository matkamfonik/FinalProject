package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.ToOne;

import java.math.BigDecimal;
import java.util.List;

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
    private BigDecimal brutto;

    @NotNull
    @DecimalMin("0")
    private BigDecimal vatRate;

    @ManyToOne
    @JoinColumn(name = "tax_month_id")
    private TaxMonth taxMonth;

}
