package pl.coderslab.finalproject.httpClients;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdresKorespondencyjny {
    private String budynek;
    private String kod;
    private Integer lokal;
    private String miasto;
    private String ulica;
}
