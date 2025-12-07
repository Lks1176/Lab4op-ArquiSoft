package com.udea.lab4op.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit_application")
public class CreditApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;
    private String clientId;
    private String email;
    private String phoneNumber;
    private BigDecimal monthlyIncome;
    private String applicationCode;
    private LocalDateTime applicationDate;

    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;
}
