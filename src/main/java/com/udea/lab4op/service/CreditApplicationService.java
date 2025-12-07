package com.udea.lab4op.service;

import com.udea.lab4op.entity.Credit;
import com.udea.lab4op.entity.CreditApplication;
import com.udea.lab4op.repository.CreditApplicationRepository;
import com.udea.lab4op.repository.CreditRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreditApplicationService {

    private final CreditApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;

    public CreditApplicationService(CreditApplicationRepository applicationRepository,
                                    CreditRepository creditRepository) {
        this.applicationRepository = applicationRepository;
        this.creditRepository = creditRepository;
    }

    public CreditApplication applyForCredit(Long creditId, String clientName,
                                            String clientId, String email,
                                            String phoneNumber, BigDecimal monthlyIncome) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el credito"));

        if (!"DISPONIBLE".equals(credit.getStatus())) {
            throw new RuntimeException("No es posible aplicar el credito");
        }

        // Validación simple de ingresos (3x la cuota mensual aproximada)
        BigDecimal monthlyPayment = calculateMonthlyPayment(
                credit.getAmount(),
                credit.getInterestRate(),
                credit.getTermMonths()
        );

        if (monthlyIncome.compareTo(monthlyPayment.multiply(BigDecimal.valueOf(3))) < 0) {
            throw new RuntimeException("Ganancias mensuales insuficientes para aplicar a este credito");
        }

        CreditApplication application = new CreditApplication();
        application.setClientName(clientName);
        application.setClientId(clientId);
        application.setEmail(email);
        application.setPhoneNumber(phoneNumber);
        application.setMonthlyIncome(monthlyIncome);
        application.setCredit(credit);
        application.setApplicationDate(LocalDateTime.now());

        String applicationCode = generateApplicationCode(credit.getCreditType());
        application.setApplicationCode(applicationCode);

        return applicationRepository.save(application);
    }

    public CreditApplication getApplicationByCode(String applicationCode) {
        return applicationRepository.findByApplicationCode(applicationCode)
                .orElseThrow(() -> new RuntimeException("Petición no encontrada"));
    }

    private String generateApplicationCode(String creditType) {
        return creditType.substring(0, 3).toUpperCase() + "-" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal amount, BigDecimal annualRate, int months) {
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(1200), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyRate);
        BigDecimal numerator = amount.multiply(monthlyRate).multiply(
                onePlusR.pow(months)
        );
        BigDecimal denominator = onePlusR.pow(months).subtract(BigDecimal.ONE);
        return numerator.divide(denominator, 2, BigDecimal.ROUND_HALF_UP);
    }
}