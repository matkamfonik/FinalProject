package pl.coderslab.finalproject.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.NIP;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.format.annotation.NumberFormat;


@Getter
@Setter
public class BusinessDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String city;

    @NotBlank
    @Pattern(regexp = "\\d{2}-\\d{3}")
    private String postalCode;

    private String Street;

    @NotBlank
    @Pattern(regexp = "\\d+[a-zA-Z]?")
    private String number;

    @NumberFormat
    @Min(value = 0)
    private String apartmentNumber;

    @NIP
    private String nip;

    @REGON
    private String regon;

    @ManyToOne
    @JoinColumn
    @NotNull
    private Long taxationForm;


}
