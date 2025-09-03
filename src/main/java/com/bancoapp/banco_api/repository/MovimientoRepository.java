package com.bancoapp.banco_api.repository;

import com.bancoapp.banco_api.model.Movimiento;
import com.bancoapp.banco_api.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaAndFecha(Cuenta cuenta, LocalDate fecha);
}
