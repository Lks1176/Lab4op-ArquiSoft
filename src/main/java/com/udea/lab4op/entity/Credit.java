package com.udea.lab4op.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String creditType; // PERSONAL, HIPOTECARIO, VEHICULO, EMPRESARIAL
    private BigDecimal amount;
    private BigDecimal interestRate;
    private int termMonths;
    private String status; // DISPONIBLE, PENDIENTE, APROBADO, RECHAZADO, ACTIVO, PAGO
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL)
    private List<CreditApplication> applications;
}
