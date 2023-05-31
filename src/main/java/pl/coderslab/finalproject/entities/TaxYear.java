package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tax_years")
@Getter
@Setter
public class TaxYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Digits(integer = 4, fraction = 0)
    @Column(unique = true)
    private int year;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    private BigDecimal balance = BigDecimal.ZERO;

    private BigDecimal vatBalance = BigDecimal.ZERO;

    private Boolean active = true;

    private Boolean upToDate = true;
}
