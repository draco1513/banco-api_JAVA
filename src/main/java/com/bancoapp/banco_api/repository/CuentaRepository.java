package com.bancoapp.banco_api.repository;

import com.bancoapp.banco_api.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    void deleteByNumeroCuenta(String numeroCuenta);
    List<Cuenta> findByClienteId(Long clienteId);


}
