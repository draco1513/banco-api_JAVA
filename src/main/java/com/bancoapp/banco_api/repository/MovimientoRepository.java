package com.bancoapp.banco_api.repository;

import com.bancoapp.banco_api.model.Movimiento;
import com.bancoapp.banco_api.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.Optional;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaAndFecha(Cuenta cuenta, LocalDate fecha);
//    @Query("SELECT SUM(m.valor) FROM Movimiento m WHERE m.cuenta.id = :cuentaId AND m.fecha = :fecha AND m.valor < 0")
//    Optional<BigDecimal> sumarRetirosPorCuentaYFecha(@Param("cuentaId") Long cuentaId, @Param("fecha") LocalDate fecha);


    @Query("""
         SELECT COALESCE(SUM(ABS(m.valor)), 0)
         FROM Movimiento m
         WHERE m.cuenta.id = :cuentaId
           AND m.tipoMovimiento = com.bancoapp.banco_api.model.TipoMovimiento.RETIRO
           AND m.fecha >= :inicio AND m.fecha < :fin
         """)
    BigDecimal sumaRetirosEnDia(Long cuentaId, LocalDateTime inicio, LocalDateTime fin);

    @Query("""
         SELECT COALESCE(SUM(CASE WHEN m.tipoMovimiento = com.bancoapp.banco_api.model.TipoMovimiento.RETIRO THEN ABS(m.valor) ELSE 0 END), 0),
                COALESCE(SUM(CASE WHEN m.tipoMovimiento = com.bancoapp.banco_api.model.TipoMovimiento.DEPOSITO THEN m.valor ELSE 0 END), 0)
         FROM Movimiento m
         WHERE m.cuenta.id = :cuentaId AND m.fecha BETWEEN :desde AND :hasta
         """)
    Object[] totalesDebitoCredito(Long cuentaId, LocalDateTime desde, LocalDateTime hasta);

    List<Movimiento> findByCuentaAndFechaBetween(Cuenta c, LocalDateTime d, LocalDateTime h);

    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDate fechaInicio, LocalDate fechaFin);
}
