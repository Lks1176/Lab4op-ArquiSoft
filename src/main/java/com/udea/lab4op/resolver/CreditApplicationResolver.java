package com.udea.lab4op.resolver;

import com.udea.lab4op.entity.CreditApplication;
import com.udea.lab4op.service.CreditApplicationService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.math.BigDecimal;

@Controller
public class CreditApplicationResolver {

    private final CreditApplicationService applicationService;

    public CreditApplicationResolver(CreditApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @MutationMapping
    public CreditApplication applyForCredit(@Argument Long creditId,
                                            @Argument String clientName,
                                            @Argument String clientId,
                                            @Argument String email,
                                            @Argument String phoneNumber,
                                            @Argument String monthlyIncome) {
        BigDecimal income = new BigDecimal(monthlyIncome);
        return applicationService.applyForCredit(creditId, clientName, clientId,
                email, phoneNumber, income);
    }

    @QueryMapping
    public CreditApplication applicationByCode(@Argument String applicationCode) {
        return applicationService.getApplicationByCode(applicationCode);
    }
}
