package com.bancoapp.banco_api.controller;

import com.bancoapp.banco_api.model.Cuenta;
import com.bancoapp.banco_api.service.CuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public List<Cuenta> listarCuentas() {
        return cuentaService.listarCuentas();
    }

    @PostMapping
    public Cuenta crearCuenta(@RequestBody Cuenta cuenta) {
        return cuentaService.crearCuenta(cuenta);
    }

    @DeleteMapping("/{numeroCuenta}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable String numeroCuenta) {
        cuentaService.eliminarCuenta(numeroCuenta);
        return ResponseEntity.noContent().build();
    }
}
