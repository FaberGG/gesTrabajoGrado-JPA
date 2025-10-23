package co.unicauca.gestrabajogradojpa.dto;

import java.time.LocalDateTime;

public record FormatoADTO(
        Integer id,
        String observaciones,
        Integer evaluadoPor,
        LocalDateTime fechaEvaluacion,
        Integer trabajoGradoId
) {}
