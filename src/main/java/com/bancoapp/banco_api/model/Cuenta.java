package com.bancoapp.banco_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cuenta")
@Data
public class Cuenta {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String numeroCuenta;

        @Enumerated(EnumType.STRING)
        @Column(name = "tipo_cuenta", length = 20, nullable = false)
        private TipoCuenta tipoCuenta;

        @Column(precision = 19, scale = 2)
        private BigDecimal saldoInicial;

        private boolean estado;

        @ManyToOne
        @JoinColumn(name = "cliente_id", nullable = false)
        private Cliente cliente;
        @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
        private List<Movimiento> movimientos;
}
