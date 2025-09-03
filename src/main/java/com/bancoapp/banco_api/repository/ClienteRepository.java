package com.bancoapp.banco_api.repository;

import com.bancoapp.banco_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository  extends JpaRepository<Cliente, Long> {
    Cliente findByIdentificacion(String identificacion);
}