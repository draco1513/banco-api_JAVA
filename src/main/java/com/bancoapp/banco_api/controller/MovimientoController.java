package com.bancoapp.banco_api.controller;

import com.bancoapp.banco_api.model.Movimiento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

//    private final MovimientoService movimientoService;
//
//    public MovimientoController(MovimientoService movimientoService) {
//        this.movimientoService = movimientoService;
//    }
//
//    @GetMapping("/{numeroCuenta}/{fecha}")
//    public ResponseEntity<List<Movimiento>> listarMovimientos(
//            @PathVariable String numeroCuenta,
//            @PathVariable String fecha) {
//
//        LocalDate fechaConsulta = LocalDate.parse(fecha);
//        List<Movimiento> movimientos = movimientoService.listarMovimientos(Long.valueOf(numeroCuenta), fechaConsulta);
//        return ResponseEntity.ok(movimientos);
//    }

//    @PostMapping
//    public ResponseEntity<Movimiento> registrarMovimiento(@RequestBody Movimiento movimiento) {
//        Movimiento nuevoMovimiento = movimientoService.registrarMovimiento(movimiento);
//        return ResponseEntity.ok(nuevoMovimiento);
//    }
}
