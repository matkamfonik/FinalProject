package pl.coderslab.finalproject.httpClients;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Firma {
    private String nazwa;
    private String id;
    private Wlasciciel wlasciciel;
    private AdresKorespondencyjny adresKorespondencyjny;
}
