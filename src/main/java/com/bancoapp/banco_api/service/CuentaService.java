package com.bancoapp.banco_api.service;

import com.bancoapp.banco_api.model.Cuenta;
import com.bancoapp.banco_api.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public List<Cuenta> listarCuentas() {
        return cuentaRepository.findAll();
    }

    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void eliminarCuenta(String numeroCuenta) {
        cuentaRepository.deleteById(Long.valueOf(numeroCuenta));
    }
}
