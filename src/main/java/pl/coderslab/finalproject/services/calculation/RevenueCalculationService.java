package pl.coderslab.finalproject.services.calculation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.RevenuePosition;
import pl.coderslab.finalproject.mappers.RevenuePositionMapper;
import pl.coderslab.finalproject.services.api.TaxMonthApiService;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
@Setter
@Getter
@RequiredArgsConstructor
public class RevenueCalculationService {

    private final RevenuePositionMapper revenuePositionMapper;

    public void calculate(RevenuePosition revenuePosition) {
        revenuePosition.setVat(revenuePosition.getNetto().multiply(revenuePosition.getVatRate().divide(BigDecimal.valueOf(100L), new MathContext(5)), new MathContext(5)));
        revenuePosition.setBrutto(revenuePosition.getNetto().add(revenuePosition.getVat(), new MathContext(5)));
    }
}
