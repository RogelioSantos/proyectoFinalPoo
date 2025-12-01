# Sistema de Ticketing de Incidencias LAB-LIS

## Descripción del Proyecto

Sistema de gestión de incidencias técnicas desarrollado para el Laboratorio de Cómputo (LAB-LIS). Este sistema permite **registrar, rastrear y gestionar** incidencias técnicas de los equipos de cómputo del laboratorio, facilitando la comunicación entre usuarios y técnicos.

### Características Principales

- **Registro de incidencias**: Los usuarios (estudiantes, profesores) pueden reportar fallos en los equipos indicando el ID del equipo, descripción del problema y tipo de incidencia.
- **Clasificación por tipo**: Las incidencias se categorizan en HARDWARE, SOFTWARE o RED.
- **Gestión de prioridades**: Sistema de prioridades (BAJA, MEDIA, ALTA) para atender primero los casos críticos.
- **Ciclo de vida completo**: Estados de incidencia: ABIERTA → EN_PROCESO → RESUELTA → CERRADA.
- **Asignación de técnicos**: Permite asignar técnicos responsables a cada incidencia.
- **Filtrado y consultas**: Múltiples opciones de filtrado por estado, tipo, prioridad, técnico o equipo.
- **Historial de cambios**: Registro detallado de todas las modificaciones realizadas a cada incidencia.
- **Notas de solución**: Los técnicos pueden documentar cómo resolvieron cada problema.
- **Reportes**: Generación de reportes estadísticos del estado del laboratorio.

### Roles de Usuario

| Rol | Funcionalidades |
|-----|-----------------|
| **Estudiante** | Reportar fallos, consultar estado de sus incidencias |
| **Profesor** | Reportar fallos, consultar estado de sus incidencias |
| **Técnico** | Atender incidencias, cambiar estados, agregar notas de solución |
| **Administrador** | Gestión completa, asignar prioridades, generar reportes |

## Estructura del Proyecto

```
proyecto_poo/
├── src/
│   ├── Usuario.java          # Clase abstracta base para usuarios
│   ├── Estudiante.java       # Usuario tipo estudiante
│   ├── Profesor.java         # Usuario tipo profesor
│   ├── Tecnico.java          # Usuario tipo técnico
│   ├── Administrador.java    # Usuario tipo administrador
│   ├── Incidencia.java       # Clase principal de incidencias + Enums
│   └── SistemaTicketing.java # Clase principal con menú y lógica
├── bin/                      # Archivos compilados
├── lib/                      # Dependencias externas
└── README.md
```

## Requisitos

- **Java JDK 8** o superior
- **Visual Studio Code** con extensión Java (opcional)
- Terminal o línea de comandos

## Instrucciones de Ejecución

### Opción 1: Usando Visual Studio Code

1. Abrir la carpeta del proyecto en VS Code
2. Asegurarse de tener instalada la extensión "Extension Pack for Java"
3. Abrir el archivo `SistemaTicketing.java`
4. Hacer clic en el botón **Run** (▶) o presionar `F5`

### Opción 2: Usando Terminal (Windows)

```powershell
# Navegar a la carpeta del proyecto
cd c:\Users\roger\java\poo\proyecto_final\proyecto_poo

# Compilar todos los archivos Java
javac -d bin src/*.java

# Ejecutar el programa
java -cp bin SistemaTicketing
```

### Opción 3: Usando Terminal (Linux/Mac)

```bash
# Navegar a la carpeta del proyecto
cd /ruta/al/proyecto_poo

# Compilar todos los archivos Java
javac -d bin src/*.java

# Ejecutar el programa
java -cp bin SistemaTicketing
```

## Uso del Sistema

Al ejecutar el programa, se mostrará un menú interactivo:

```
╔══════════════════════════════════════════════════╗
║     SISTEMA TICKETING LAB-LIS                    ║
╠══════════════════════════════════════════════════╣
║  1. Registrar Nueva Incidencia                   ║
║  2. Consultar/Filtrar Incidencias                ║
║  3. Atender Incidencia (Estado/Prioridad/Técnico)║
║  4. Ver Historial de Incidencia                  ║
║  5. Generar Reporte                              ║
║  6. Gestionar Técnicos                           ║
║  0. Salir                                        ║
╚══════════════════════════════════════════════════╝
```

### Ejemplo de uso básico:

1. **Registrar una incidencia**: Seleccionar opción 1, ingresar ID del equipo (ej: "PC-LAB-001"), descripción del fallo y tipo.
2. **Consultar incidencias**: Seleccionar opción 2 y elegir el filtro deseado.
3. **Atender incidencia**: Seleccionar opción 3, ingresar el ID de la incidencia y realizar las acciones necesarias.

## Tecnologías Utilizadas

- **Lenguaje**: Java
- **Paradigma**: Programación Orientada a Objetos (POO)
- **Conceptos aplicados**: Herencia, Polimorfismo, Encapsulamiento, Enumeraciones, Colecciones

## Autor
Rojas Santos Rogelio 
Proyecto Final - Diseño y Programación Orientada a Objetos  
Ingeniería de Software
