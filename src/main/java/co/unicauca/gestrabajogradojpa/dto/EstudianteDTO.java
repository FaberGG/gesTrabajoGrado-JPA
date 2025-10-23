package co.unicauca.gestrabajogradojpa.dto;

import co.unicauca.gestrabajogradojpa.model.EnumProgram;

public record EstudianteDTO(
        Long id,
        String nombres,
        String apellidos,
        String email,
        String celular,
        String codigoEstudiante,
        EnumProgram programa
) {}
