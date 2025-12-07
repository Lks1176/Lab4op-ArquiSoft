package com.udea.lab4op.resolver;

import com.udea.lab4op.entity.Credit;
import com.udea.lab4op.service.CreditService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class CreditResolver {

    private final CreditService creditService;

    public CreditResolver(CreditService creditService) {
        this.creditService = creditService;
    }

    @QueryMapping
    public List<Credit> allCredits() {
        return creditService.getAllCredits();
    }

    @QueryMapping
    public Credit creditById(@Argument Long id) {
        return creditService.getCreditById(id);
    }

    @QueryMapping
    public List<Credit> creditsByCategory(@Argument String creditType) {
        return creditService.getCreditsByCategory(creditType);
    }

    @MutationMapping
    public Credit createCredit(@Argument String creditType,
                               @Argument String amount,
                               @Argument String interestRate,
                               @Argument int termMonths) {
        BigDecimal amountDecimal = new BigDecimal(amount);
        BigDecimal rateDecimal = new BigDecimal(interestRate);
        return creditService.createCredit(creditType, amountDecimal, rateDecimal, termMonths);
    }

    @MutationMapping
    public Credit updateCreditStatus(@Argument Long id, @Argument String status) {
        return creditService.updateCreditStatus(id, status);
    }
}
