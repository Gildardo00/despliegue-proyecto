# Arena UAA - Torneos

Sistema web para administrar **torneos universitarios por disciplinas** del Departamento de
Actividades Deportivas de la **Universidad Autonoma de Aguascalientes**.
Automatiza la inscripcion, la clasificacion de participantes y la generacion de enfrentamientos,
sustituyendo el proceso manual en papel y Excel.
Proyecto final de **Programacion Visual Java Web** - UAA.
El modelo de datos sigue el diagrama Entidad-Relacion (ver `docs/diagrama_ER.mermaid`).

## Tecnologias
- Java 11 (compatible con JDK 17 / 21 / 25) + Maven
- Servlets + JSP (API **Jakarta**, para Apache **Tomcat 10 / 11**)
- MySQL 8
- HTML + CSS (sin frameworks)

## Que incluye
- Login con sesiones, registro de administradores y logout.
- Filtro que protege todas las paginas internas.
- Dashboard con resumen del torneo.
- CRUD de: Juegos/Disciplinas, Equipos, Jugadores, Rondas, Jornadas, Partidos y Participaciones.
- Inscripcion publica de participantes por disciplina, categoria y rama.
- Generacion automatica de equipos, clasificacion y brackets de torneo.

## Requerimientos cubiertos (Matriz de Requerimientos v2)
| ID | Requerimiento | Donde se implementa |
|----|---------------|---------------------|
| RF-01 | Registro obligatorio: bloquear inscripciones incompletas | `InscripcionServlet`, `RegistroServlet`, validaciones en cada formulario |
| RF-02 | Clasificacion automatica por deporte, categoria y rama | `ClasificacionServlet` + `clasificacion.jsp` |
| RF-03 | Filtro de nombres con lenguaje inapropiado (lista configurable) | `util/FiltroPalabras`, `palabra_prohibida`, `configuracion.jsp` |
| RF-04 | Generacion de brackets y tablas Round Robin (min. 4, bye si impar) | `TorneoServlet` + `torneos/form.jsp` |
| RF-05a | Emparejamiento por siembra (#1 vs ultimo) en eliminacion directa | `TorneoServlet.generarEliminacion` |
| RF-05b | Emparejamiento aleatorio sin repetir pares en Round Robin | `TorneoServlet.generarRoundRobin` |
| RF-06 | Creacion aleatoria y balanceada de equipos (diferencia <= 1) | `EquiposGeneradorServlet` + `generar_equipos.jsp` |
| RF-07 | Control de apertura/cierre de inscripciones | `ConfiguracionServlet`, `ConfiguracionDAO` |
| RF-08 | Soporte multidisciplina con datos separados | CRUD de `juego_disciplina` (disciplinas activas e ilimitadas) |
| RNF-03 | Acceso restringido a usuarios autenticados | `AuthFilter`, login con credenciales |
| RNF-05 | Persistencia en base de datos (sin archivos fisicos) | MySQL + todos los DAO |
| RNF-06 | Integridad: sin duplicados ni referencias huerfanas | Llaves foraneas, `UNIQUE`, control de duplicados en servlets |

> RNF-01 (disponibilidad), RNF-02 (concurrencia) y RNF-04 (tiempos) dependen de la
> infraestructura de despliegue de la UAA, no del codigo.

## Usuarios de prueba (ya vienen en la base de datos)
| Usuario | Contrasena |
|---------|-----------|
| admin   | admin123  |
| emilio  | emilio123 |
| staff   | staff123  |

## Flujo recomendado para una demo
1. **Inscripciones**: abre/cierra el registro y administra el filtro de palabras desde el menu *Inscripciones*.
2. **Inscripcion publica**: en `/inscripcion` registra participantes a una disciplina (ej. Valorant) sin equipo.
3. **Generar equipos**: en *Generar equipos* reparte la bolsa de inscritos en N equipos aleatorios balanceados.
4. **Clasificacion**: revisa en *Clasificacion* como quedan agrupados por disciplina, categoria y rama.
5. **Torneos**: en *Torneos* elige un grupo (ej. League of Legends / Universitaria / Mixto) y genera el bracket
   con siembra (#1 vs ultimo) o la tabla Round Robin segun el tipo de competencia de la disciplina.

## Como echarlo a andar (resumen)
1. Ejecuta `BD Arena Gallos.sql` en MySQL Workbench.
2. Abre la carpeta del proyecto en NetBeans (es un proyecto Maven).
3. En `Conexion.java` pon tu usuario y contrasena de MySQL.
4. Agrega Apache Tomcat 10/11 como servidor en NetBeans.
5. Run (F6). Abre: `http://localhost:8080/arena-uaa-videojuegos/`

> Los pasos COMPLETOS y detallados (instalar todo desde cero) estan en
> **GUIA_DE_INSTALACION.md**.
