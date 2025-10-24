package co.unicauca.gestrabajogradojpa.config;

import co.unicauca.gestrabajogradojpa.model.*;
import co.unicauca.gestrabajogradojpa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private TipoTrabajoRepository tipoTrabajoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private TrabajoGradoRepository trabajoGradoRepository;

    @Autowired
    private FormatoARepository formatoARepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        System.out.println(" Verificando datos de prueba...");

        //  VERIFICAR SI YA EXISTEN DATOS
        if (estudianteRepository.count() > 0 ||
                profesorRepository.count() > 0 ||
                trabajoGradoRepository.count() > 0) {
            System.out.println("Los datos ya existen en la base de datos");
            System.out.println(" Registros actuales:");
            System.out.println("   - Tipos de trabajo: " + tipoTrabajoRepository.count());
            System.out.println("   - Estudiantes: " + estudianteRepository.count());
            System.out.println("   - Profesores: " + profesorRepository.count());
            System.out.println("   - Trabajos de grado: " + trabajoGradoRepository.count());
            System.out.println("   - Formatos A: " + formatoARepository.count());
            System.out.println("  Omitiendo carga de datos de prueba...");
            return;
        }

        System.out.println(" Iniciando carga de datos de prueba...");

        // 1. CREAR Y GUARDAR TIPOS DE TRABAJO
        TipoTrabajo tipoInvestigacion = new TipoTrabajo(null, EnumModalidad.INVESTIGACION, 2);
        TipoTrabajo tipoPractica = new TipoTrabajo(null, EnumModalidad.PRACTICA_PROFESIONAL, 1);
        TipoTrabajo tipoCoterminal = new TipoTrabajo(null, EnumModalidad.PLAN_COTERMINAL, 2);

        List<TipoTrabajo> tipos = tipoTrabajoRepository.saveAll(
                Arrays.asList(tipoInvestigacion, tipoPractica, tipoCoterminal)
        );
        System.out.println("Tipos de trabajo guardados: " + tipos.size());

        // 2. CREAR Y GUARDAR ESTUDIANTES
        Estudiante estudiante1 = new Estudiante(
                "Juan Carlos",
                "Cárdenas Muñoz",
                "juan.cardenas@unicauca.edu.co",
                "3201234567",
                "10236547",
                EnumProgram.INGENIERIA_DE_SISTEMAS
        );

        Estudiante estudiante2 = new Estudiante(
                "María Fernanda",
                "López Ortiz",
                "maria.lopez@unicauca.edu.co",
                "3209876543",
                "10245896",
                EnumProgram.INGENIERIA_ELECTRONICA_Y_TELECOMUNICACIONES
        );

        Estudiante estudiante3 = new Estudiante(
                "Carlos Andrés",
                "Gómez Pérez",
                "carlos.gomez@unicauca.edu.co",
                "3157891234",
                "10258741",
                EnumProgram.INGENIERIA_DE_SISTEMAS
        );

        List<Estudiante> estudiantes = estudianteRepository.saveAll(
                Arrays.asList(estudiante1, estudiante2, estudiante3)
        );
        System.out.println(" Estudiantes guardados: " + estudiantes.size());

        // 3. CREAR Y GUARDAR PROFESORES
        Profesor profesor1 = new Profesor(
                "Julio Ariel",
                "Hurtado Alegría",
                "jhurtado@unicauca.edu.co",
                "3101234567",
                EnumProgram.INGENIERIA_DE_SISTEMAS
        );

        Profesor profesor2 = new Profesor(
                "Francisco José",
                "Pino Correa",
                "fpino@unicauca.edu.co",
                "3109876543",
                EnumProgram.INGENIERIA_DE_SISTEMAS
        );

        Profesor profesor3 = new Profesor(
                "Carlos Alberto",
                "Cobos Lozada",
                "ccobos@unicauca.edu.co",
                "3157894561",
                EnumProgram.INGENIERIA_ELECTRONICA_Y_TELECOMUNICACIONES
        );

        List<Profesor> profesores = profesorRepository.saveAll(
                Arrays.asList(profesor1, profesor2, profesor3)
        );
        System.out.println(" Profesores guardados: " + profesores.size());

        // 4. CREAR Y GUARDAR TRABAJOS DE GRADO

        // Trabajo 1: 1 estudiante, tipo INVESTIGACION
        TrabajoGrado trabajo1 = new TrabajoGrado();
        trabajo1.setTitulo("Modelo de medición de habilidades en arquitectos de software");
        trabajo1.setModalidad(EnumModalidad.INVESTIGACION);
        trabajo1.setFechaCreacion(LocalDateTime.now().minusMonths(2));
        trabajo1.setObjetivoGeneral("Diseñar un modelo para evaluar habilidades técnicas y blandas en arquitectos de software");
        trabajo1.setObjetivosEspecificos("1. Identificar competencias clave. 2. Diseñar métricas de evaluación. 3. Validar el modelo con expertos.");
        trabajo1.setEstado(EnumEstadoProyecto.EN_PROCESO);
        trabajo1.setNumeroIntentos(0);
        trabajo1.setEstudiante1(estudiante1);
        trabajo1.setEstudiante2(null); // Solo 1 estudiante
        trabajo1.setDirector(profesor1);
        trabajo1.setCodirectores(Arrays.asList(profesor2));
        trabajo1.setTipoTrabajo(tipoInvestigacion);

        // Trabajo 2: 2 estudiantes, tipo PRACTICA_PROFESIONAL
        TrabajoGrado trabajo2 = new TrabajoGrado();
        trabajo2.setTitulo("Desarrollo de aplicación móvil para gestión de inventarios en PYMES");
        trabajo2.setModalidad(EnumModalidad.PRACTICA_PROFESIONAL);
        trabajo2.setFechaCreacion(LocalDateTime.now().minusMonths(3));
        trabajo2.setObjetivoGeneral("Desarrollar una aplicación móvil multiplataforma para control de inventarios");
        trabajo2.setObjetivosEspecificos("1. Diseñar arquitectura de la aplicación. 2. Implementar módulos principales. 3. Realizar pruebas con usuarios reales.");
        trabajo2.setEstado(EnumEstadoProyecto.EN_PROCESO);
        trabajo2.setNumeroIntentos(1);
        trabajo2.setEstudiante1(estudiante2);
        trabajo2.setEstudiante2(estudiante3); // 2 estudiantes
        trabajo2.setDirector(profesor2);
        trabajo2.setCodirectores(Arrays.asList(profesor3));
        trabajo2.setTipoTrabajo(tipoPractica);

        // Trabajo 3: 2 estudiantes, tipo PLAN_COTERMINAL
        TrabajoGrado trabajo3 = new TrabajoGrado();
        trabajo3.setTitulo("Sistema de recomendación basado en aprendizaje automático para plataformas educativas");
        trabajo3.setModalidad(EnumModalidad.PLAN_COTERMINAL);
        trabajo3.setFechaCreacion(LocalDateTime.now().minusMonths(6));
        trabajo3.setObjetivoGeneral("Implementar un sistema de recomendación personalizado usando técnicas de ML");
        trabajo3.setObjetivosEspecificos("1. Analizar algoritmos de recomendación. 2. Entrenar modelos con datos reales. 3. Evaluar precisión del sistema.");
        trabajo3.setEstado(EnumEstadoProyecto.APROBADO);
        trabajo3.setNumeroIntentos(0);
        trabajo3.setEstudiante1(estudiante1);
        trabajo3.setEstudiante2(estudiante2); // 2 estudiantes
        trabajo3.setDirector(profesor3);
        trabajo3.setCodirectores(Arrays.asList(profesor1, profesor2)); // 2 codirectores
        trabajo3.setTipoTrabajo(tipoCoterminal);

        List<TrabajoGrado> trabajos = trabajoGradoRepository.saveAll(
                Arrays.asList(trabajo1, trabajo2, trabajo3)
        );
        System.out.println(" Trabajos de grado guardados: " + trabajos.size());

        // 5. CREAR Y GUARDAR FORMATOS A

        // Formato para Trabajo 1
        FormatoA formato1 = new FormatoA();
        formato1.setNumeroIntento(1);
        formato1.setRutaArchivo("/documentos/trabajo1_formato1.pdf");
        formato1.setNombreArchivo("formato_a_intento1.pdf");
        formato1.setRutaCartaAceptacion(null);
        formato1.setNombreCartaAceptacion(null);
        formato1.setFechaCarga(LocalDateTime.now());
        formato1.setEstado(EnumEstadoFormato.PENDIENTE);
        formato1.setObservaciones(null);
        formato1.setEvaluadoPor(null);
        formato1.setFechaEvaluacion(null);
        formato1.setTrabajoGrado(trabajo1);

        // Formato para Trabajo 2
        FormatoA formato2 = new FormatoA();
        formato2.setNumeroIntento(1);
        formato2.setRutaArchivo("/documentos/trabajo2_formato1.pdf");
        formato2.setNombreArchivo("formato_a_intento1.pdf");
        formato2.setRutaCartaAceptacion("/documentos/trabajo2_carta.pdf");
        formato2.setNombreCartaAceptacion("carta_aceptacion.pdf");
        formato2.setFechaCarga(LocalDateTime.now().minusDays(10));
        formato2.setEstado(EnumEstadoFormato.APROBADO);
        formato2.setObservaciones("Aprobado con observaciones menores");
        formato2.setEvaluadoPor(1);
        formato2.setFechaEvaluacion(LocalDateTime.now().minusDays(5));
        formato2.setTrabajoGrado(trabajo2);

        List<FormatoA> formatos = formatoARepository.saveAll(
                Arrays.asList(formato1, formato2)
        );
        System.out.println(" Formatos A guardados: " + formatos.size());

        System.out.println(" ¡Carga de datos completada exitosamente!");
        System.out.println(" Total de registros creados:");
        System.out.println("   - Tipos de trabajo: " + tipos.size());
        System.out.println("   - Estudiantes: " + estudiantes.size());
        System.out.println("   - Profesores: " + profesores.size());
        System.out.println("   - Trabajos de grado: " + trabajos.size());
        System.out.println("   - Formatos A: " + formatos.size());
    }
}