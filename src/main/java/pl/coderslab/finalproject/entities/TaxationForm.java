package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "taxation_forms")
@Getter
@Setter
public class TaxationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "100")
    private BigDecimal pitRate;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal secondPitRate;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal taxFreeAllowance;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal healthCareContributionRate;

    @NotNull
    private Boolean healthContributionAsCost;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal taxThreshold;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer costRate;
}
