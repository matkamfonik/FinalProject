package pl.coderslab.finalproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.NIP;
import org.hibernate.validator.constraints.pl.REGON;


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
    private String city;

    @NotBlank
    @Pattern(regexp = "\\d{2}-\\d{3}")
    private String postalCode;

    @NotBlank
    private String Street;

    @NotBlank
    @Pattern(regexp = "\\d+[a-zA-Z]?")
    private String number;

    @Min(value = 1)
    private Integer apartmentNumber;

    @NIP
    private String nip;

    @REGON
    private String regon;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
