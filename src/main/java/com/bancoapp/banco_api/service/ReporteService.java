package com.bancoapp.banco_api.service;

import com.bancoapp.banco_api.dto.ReporteDTO;
import com.bancoapp.banco_api.model.Cliente;
import com.bancoapp.banco_api.model.Cuenta;
import com.bancoapp.banco_api.model.Movimiento;
import com.bancoapp.banco_api.repository.ClienteRepository;
import com.bancoapp.banco_api.repository.CuentaRepository;
import com.bancoapp.banco_api.repository.MovimientoRepository;
import com.lowagie.text.Document; // <-- ESTE ES EL IMPORT CORRECTO
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final ClienteRepository clienteRepository;

    public ReporteService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository, ClienteRepository clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<ReporteDTO> generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));

        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
        List<ReporteDTO> reporteCompleto = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(cuenta.getId(), fechaInicio, fechaFin);
            for (Movimiento m : movimientos) {
                reporteCompleto.add(ReporteDTO.builder()
                        .fecha(m.getFecha().toString())
                        .cliente(cliente.getNombre())
                        .numeroCuenta(cuenta.getNumeroCuenta())
                        .tipo(String.valueOf(cuenta.getTipoCuenta()))
                        .saldoInicial(m.getSaldo().subtract(m.getValor())) // Saldo ANTES del movimiento
                        .estado(cuenta.isEstado())
                        .valorMovimiento(m.getValor())
                        .saldoDisponible(m.getSaldo()) // Saldo DESPUÉS del movimiento
                        .build());
            }
        }
        return reporteCompleto;
    }

    public String generarPdfBase64(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        List<ReporteDTO> data = generarReporte(clienteId, fechaInicio, fechaFin);
        String clienteNombre = clienteRepository.findById(clienteId).map(Cliente::getNombre).orElse("Desconocido");

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // AHORA SÍ SE USA LA CLASE CORRECTA, QUE NO ES ABSTRACTA
            Document doc = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(doc, baos);

            doc.open(); // Funciona
            doc.add(new Paragraph("Estado de Cuenta - Cliente: " + clienteNombre));
            doc.add(new Paragraph(String.format("Rango: %s a %s", fechaInicio, fechaFin)));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(8);
            List.of("Fecha", "Cliente", "Cuenta", "Tipo", "Saldo Inicial", "Estado", "Movimiento", "Saldo Disponible")
                    .forEach(header -> table.addCell(new PdfPCell(new Phrase(header))));

            for (ReporteDTO r : data) {
                table.addCell(r.getFecha());
                table.addCell(r.getCliente());
                table.addCell(r.getNumeroCuenta());
                table.addCell(r.getTipo());
                table.addCell(String.valueOf(r.getSaldoInicial()));
                table.addCell(r.isEstado() ? "ACTIVA" : "INACTIVA");
                table.addCell(String.valueOf(r.getValorMovimiento()));
                table.addCell(String.valueOf(r.getSaldoDisponible()));
            }

            doc.add(table); // Funciona
            doc.close(); // Funciona

            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            // Es buena práctica envolver la excepción original para no perder la causa
            throw new RuntimeException("No se pudo generar el PDF: " + e.getMessage(), e);
        }
    }
}

