package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tax_years")
@Getter
@Setter
public class TaxYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Digits(integer = 4, fraction = 0)
    private int year;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    private BigDecimal balance = BigDecimal.ZERO;

    private BigDecimal vatBalance = BigDecimal.ZERO;

    private Boolean active = true;

    private Boolean upToDate = true;
}
