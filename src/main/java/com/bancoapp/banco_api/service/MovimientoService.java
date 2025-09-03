package com.bancoapp.banco_api.service;

import com.bancoapp.banco_api.model.Cuenta;
import com.bancoapp.banco_api.model.Movimiento;
import com.bancoapp.banco_api.repository.CuentaRepository;
import com.bancoapp.banco_api.repository.MovimientoRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service

public class MovimientoService {
    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    private static final double LIMITE_DIARIO = 1000.0;

    public MovimientoService(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public Movimiento registrarMovimiento(String numeroCuenta, Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.findById(numeroCuenta).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        double saldoActual = cuenta.getSaldoInicial();

        // Total retirado hoy
        double totalRetiradoHoy = movimientoRepository.findByCuentaAndFecha(cuenta, LocalDate.now())
                .stream()
                .filter(m -> m.getValor() < 0)
                .mapToDouble(m -> Math.abs(m.getValor()))
                .sum();

        if ("Retiro".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            if (saldoActual < movimiento.getValor()) {
                throw new RuntimeException("Saldo no disponible");
            }

            if (totalRetiradoHoy + movimiento.getValor() > LIMITE_DIARIO) {
                throw new RuntimeException("Cupo diario excedido");
            }

            movimiento.setValor(-Math.abs(movimiento.getValor()));
        } else if ("Depósito".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            movimiento.setValor(Math.abs(movimiento.getValor()));
        }

        double nuevoSaldo = saldoActual + movimiento.getValor();
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
        movimiento.setCuenta(cuenta);
        movimiento.setFecha(LocalDate.now());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setSaldoInicial(saldoActual);
        return movimiento;
    }

    public List<Movimiento> obtenerPorCuenta(String numeroCuenta) {
        Cuenta cuenta = cuentaRepository.findById(numeroCuenta).orElse(null);
        return movimientoRepository.findByCuentaAndFecha(cuenta, LocalDate.now());
    }

    public List<Movimiento> listarMovimientos(String numeroCuenta, LocalDate fechaConsulta) {
        Cuenta cuenta = cuentaRepository.findById(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        return movimientoRepository.findByCuentaAndFecha(cuenta, fechaConsulta);
    }

    public Movimiento registrarMovimiento(Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getNumeroCuenta())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        double saldoActual = cuenta.getSaldoInicial();

        if ("Retiro".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            if (saldoActual < movimiento.getValor()) {
                throw new RuntimeException("Saldo insuficiente para retiro");
            }
            movimiento.setValor(-movimiento.getValor()); // Resta el valor si es retiro
        }

        double nuevoSaldo = saldoActual + movimiento.getValor();

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setCuenta(cuenta);
        movimiento.setFecha(LocalDate.now());
        movimiento.setSaldo(nuevoSaldo);

        return movimientoRepository.save(movimiento);
    }
}
