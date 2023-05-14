package pl.coderslab.finalproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class TaxationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String name;

    @NotNull
    double pitRate;

    double secondPitRate;

    @NotNull
    BigDecimal taxFreeAllowance;

    @NotNull
    double healthCareContributionRate;

    @NotNull
    boolean healthContributionAsCost;

    @NotNull
    BigDecimal taxThreshold;

    @NotNull
    int costRate;


}
