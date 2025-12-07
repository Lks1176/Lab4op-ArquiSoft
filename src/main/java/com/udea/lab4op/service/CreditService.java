package com.udea.lab4op.service;

import com.udea.lab4op.entity.Credit;
import com.udea.lab4op.repository.CreditRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditService {

    private final CreditRepository creditRepository;

    public CreditService(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    public List<Credit> getAllCredits() {
        return creditRepository.findAll();
    }

    public Credit getCreditById(Long id) {
        return creditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado ningún credito por este id"));
    }

    public List<Credit> getCreditsByCategory(String creditType) {
        return creditRepository.findByCreditType(creditType);
    }

    public Credit createCredit(String creditType, BigDecimal amount,
                               BigDecimal interestRate, int termMonths) {
        Credit credit = new Credit();
        credit.setCreditType(creditType);
        credit.setAmount(amount);
        credit.setInterestRate(interestRate);
        credit.setTermMonths(termMonths);
        credit.setStatus("DISPONIBLE");
        credit.setCreatedAt(LocalDateTime.now());
        return creditRepository.save(credit);
    }

    public Credit updateCreditStatus(Long id, String status) {
        Credit credit = getCreditById(id);
        credit.setStatus(status);
        if ("APROVADO".equals(status)) {
            credit.setApprovedAt(LocalDateTime.now());
        }
        return creditRepository.save(credit);
    }
}
