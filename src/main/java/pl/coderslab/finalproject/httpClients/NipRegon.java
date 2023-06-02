package pl.coderslab.finalproject.httpClients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.NIP;
import org.hibernate.validator.constraints.pl.REGON;

@NoArgsConstructor
@Setter
@Getter
public class NipRegon {
    @REGON
    private String regonNumber;

    @NIP
    private String nipNumber;
}
