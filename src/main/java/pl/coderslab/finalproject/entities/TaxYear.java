package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tax_years")
public class TaxYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
