package co.unicauca.gestrabajogradojpa.dto;

import java.time.LocalDateTime;

public record FormatoACreateDTO(
        String observaciones,
        Integer evaluadoPor,
        LocalDateTime fechaEvaluacion
) {}
