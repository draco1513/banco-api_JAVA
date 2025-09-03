package com.bancoapp.banco_api.repository;

import com.bancoapp.banco_api.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {
}
