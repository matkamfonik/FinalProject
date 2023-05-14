package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "businesses")
@Getter
@Setter
public class Bussiness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToOne
    @JoinColumn(name = "id_bussiness", unique = true)
    private Address address;

    @NotBlank
    private String NIP;

    @NotBlank
    private String REGON;

    @OneToMany
    @JoinColumn(name = "id_bussiness")
    List<TaxYear> taxYears = new ArrayList<>();


}
