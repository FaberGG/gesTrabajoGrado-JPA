package co.unicauca.gestrabajogradojpa.dto;

import co.unicauca.gestrabajogradojpa.model.EnumProgram;

public record ProfesorDTO(
        Long id,
        String nombres,
        String apellidos,
        String email,
        String celular,
        EnumProgram programa
) {}
