package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String NIP;

    @NotBlank
    private String REGON;

    @NotBlank
    private String city;

    @NotBlank
    private String postalCode;

    private String Street;

    @NotBlank
    private String number;

    private String apartmentNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
