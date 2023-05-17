package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "businesses")
@Getter
@Setter
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToOne
    @NotNull
    @JoinColumn(name = "address_id", unique = true)
    private Address address;

    @NotBlank
    private String NIP;

    @NotBlank
    private String REGON;

    @OneToMany
    @JoinColumn(name = "id_business")
    List<TaxYear> taxYears = new ArrayList<>();


    @ManyToOne
    @JoinColumn
    @NotNull
    private TaxationForm taxationForm;
}
