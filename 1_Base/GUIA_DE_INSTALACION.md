# GUIA PASO A PASO - Arena UAA (Videojuegos)
### Para alguien que NO domina Java Web. Sigue los pasos en orden.

Stack del proyecto: **Maven + Tomcat 10/11 (Jakarta) + MySQL 8 + NetBeans**.
El codigo Java ya viene compilado y verificado (0 errores). Tu solo tienes que
instalar los programas, crear la base de datos y ejecutar.

---

## INDICE
1. Programas que vas a instalar
2. PASO 1 - Instalar el JDK (Java)
3. PASO 2 - Instalar MySQL + Workbench
4. PASO 3 - Instalar Apache Tomcat
5. PASO 4 - Instalar NetBeans
6. PASO 5 - Crear la base de datos
7. PASO 6 - Abrir el proyecto en NetBeans
8. PASO 7 - Poner tu contrasena de MySQL
9. PASO 8 - Agregar Tomcat a NetBeans
10. PASO 9 - Ejecutar el proyecto
11. PASO 10 - Probar que todo funciona
12. Mapa de archivos (que es cada cosa y donde esta)
13. Solucion de problemas (LEER si algo falla)

---

## 1. PROGRAMAS QUE VAS A INSTALAR
Descarga e instala estos 4 (si ya tienes alguno, salta ese paso):

| Programa | Version recomendada | Para que sirve |
|----------|--------------------|----------------|
| JDK (Java) | 17 o 21 | Compilar y correr Java |
| MySQL Server + Workbench | 8.x | Base de datos |
| Apache Tomcat | 11 (o 10.1) | Servidor donde corre la app |
| Apache NetBeans | 22 o superior | Programa donde abres el proyecto |

> IMPORTANTE: este proyecto usa **Tomcat 10 u 11** (paquete `jakarta.*`).
> NO uses Tomcat 9; ese usa `javax.*` y daria error de compilacion.

---

## PASO 1 - INSTALAR EL JDK (JAVA)
1. Busca en Google: **"Adoptium Temurin JDK 21"** (https://adoptium.net).
2. Descarga el instalador `.msi` para Windows (x64) de **JDK 21 (LTS)**.
3. Instalalo con "Siguiente, Siguiente, Finalizar".
   - En la pantalla de opciones, activa **"Set JAVA_HOME variable"** si aparece.
4. Para comprobar: abre **CMD** (tecla Windows, escribe `cmd`) y teclea:
   ```
   java -version
   ```
   Debe mostrar la version 21 (o 17). Si la muestra, listo.

---

## PASO 2 - INSTALAR MYSQL + WORKBENCH
1. Google: **"MySQL Installer Community download"** (dev.mysql.com).
2. Descarga **MySQL Installer for Windows** (el "mysql-installer-community").
3. Al instalar, elige el tipo **"Developer Default"** (incluye Server + Workbench).
4. Cuando te pida la contrasena del usuario **root**, ESCRIBE UNA Y ANOTALA.
   (Ejemplo, pon: `root`. La vas a necesitar en el PASO 7.)
5. Termina la instalacion dejando todo por defecto (puerto **3306**).
6. Abre **MySQL Workbench** y haz clic en la conexion "Local instance MySQL".
   Si abre sin errores, MySQL funciona.

---

## PASO 3 - INSTALAR APACHE TOMCAT
1. Google: **"Apache Tomcat 11 download"** (tomcat.apache.org).
2. En la seccion **"Core"**, descarga el **"zip"** de 64-bit Windows
   (por ejemplo `apache-tomcat-11.0.x-windows-x64.zip`).
3. **Descomprime** ese zip en una carpeta facil, por ejemplo:
   ```
   C:\tomcat11
   ```
   (Dentro debe quedar: C:\tomcat11\bin, C:\tomcat11\conf, etc.)
4. No necesitas iniciarlo a mano: NetBeans lo arranca por ti (PASO 8).

---

## PASO 4 - INSTALAR NETBEANS
1. Google: **"Apache NetBeans download"** (netbeans.apache.org).
2. Descarga el instalador para Windows de la version mas reciente.
3. Instalalo (Siguiente, Siguiente, Finalizar). Ya trae Maven incluido.
4. Abre NetBeans una vez para que termine de configurarse.

---

## PASO 5 - CREAR LA BASE DE DATOS
1. Abre **MySQL Workbench** y entra a tu conexion local.
2. Menu **File -> Open SQL Script...** y elige el archivo:
   ```
   BD Arena Gallos.sql   (esta en la carpeta del proyecto)
   ```
   (O abre el archivo, copia TODO y pegalo en una pestania de consulta.)
3. Haz clic en el **rayo amarillo** (Execute / Ctrl+Shift+Enter) para ejecutar todo.
4. En el panel izquierdo (SCHEMAS) da clic derecho -> **Refresh All**.
   Debe aparecer la base de datos **arena_uaa** con 12 tablas.
5. (Opcional) Para ver datos, ejecuta:
   ```sql
   USE arena_uaa;
   SELECT * FROM usuario;
   ```

---

## PASO 6 - ABRIR EL PROYECTO EN NETBEANS
1. Abre NetBeans.
2. Menu **File -> Open Project...**
3. Navega hasta la carpeta **arena-uaa-videojuegos** (la que tiene el `pom.xml`).
   NetBeans la reconoce con un icono de cafe/Maven. Seleccionala y **Open Project**.
4. La primera vez, NetBeans **descargara las librerias** (mysql-connector y la API
   de servlets) automaticamente con Maven. NECESITAS INTERNET en este paso.
   Espera a que termine (barra de progreso abajo a la derecha).

---

## PASO 7 - PONER TU CONTRASENA DE MYSQL
El proyecto necesita saber la contrasena de tu MySQL (la del PASO 2).

1. En NetBeans, en el panel **Projects** (izquierda), abre:
   ```
   Source Packages -> mx.uaa.arena.config -> Conexion.java
   ```
2. Busca estas dos lineas y ponlas con TUS datos:
   ```java
   private static final String USUARIO  = "root";
   private static final String PASSWORD = "root";   // <-- tu contrasena de MySQL
   ```
3. Guarda con **Ctrl+S**.

> Si pusiste otra contrasena en el PASO 2, escribela aqui. Si tu usuario no es
> "root", cambialo tambien.

---

## PASO 8 - AGREGAR TOMCAT A NETBEANS (solo la primera vez)
1. Menu **Tools -> Servers**.
2. Clic en **Add Server...**
3. Elige **Apache Tomcat or TomEE** y **Next**.
4. En "Server Location" pon la carpeta donde descomprimiste Tomcat:
   ```
   C:\tomcat11
   ```
5. Pon un usuario y contrasena cualquiera (puede ser admin / admin) y **Finish**.
   (NetBeans solo los usa para administrar el servidor.)

---

## PASO 9 - EJECUTAR EL PROYECTO
1. En el panel **Projects**, clic derecho sobre **arena-uaa-videojuegos**.
2. Clic en **Run** (o presiona **F6**).
   - Si te pregunta el servidor, elige **Apache Tomcat**.
3. NetBeans compila, arranca Tomcat y abre el navegador solo.
4. La direccion sera algo como:
   ```
   http://localhost:8080/arena-uaa-videojuegos/
   ```
   Si no abre solo, copia esa direccion en tu navegador.

---

## PASO 10 - PROBAR QUE TODO FUNCIONA
1. Te aparece la pantalla de **Login**.
2. Entra con:
   ```
   Usuario: admin
   Contrasena: admin123
   ```
3. Debes ver el **Dashboard** con tarjetas (Juegos/Disciplinas, Equipos, etc.).
4. Prueba el menu lateral:
   - **Juegos / Disciplinas -> + Nueva disciplina** -> llena y Registrar. Aparece en la lista.
   - Prueba **Editar** y **Eliminar** en cualquier fila.
   - Repite con Equipos, Jugadores, Rondas, Jornadas, Partidos y Participaciones.
   - Prueba **Inscripciones**, **Clasificacion**, **Generar equipos** y **Torneos**.
5. Prueba **Registrate** (crear cuenta nueva) y luego inicia sesion con ella.
6. Prueba **Cerrar sesion** (arriba a la derecha). Debe regresar al login.
7. Prueba la proteccion: cierra sesion y pega en el navegador
   `http://localhost:8080/arena-uaa-videojuegos/dashboard`.
   Te debe **rebotar al login** (eso es el filtro de seguridad funcionando).

Si todo esto pasa: el proyecto esta COMPLETO y funcionando.

---

## 12. MAPA DE ARCHIVOS (que es cada cosa)

Carpeta raiz:
- `pom.xml` -> configuracion de Maven (librerias). Programa: NetBeans/Maven.
- `BD Arena Gallos.sql` -> script de la BD. Programa: MySQL Workbench.

Codigo Java (en `src/main/java/mx/uaa/arena/`):
- `config/Conexion.java` -> conexion a MySQL (aqui va tu contrasena).
- `model/` -> clases que representan las tablas (Usuario, JuegoDisciplina, Equipo,
  Jugador, Ronda, Jornada, Partido, Enfrentar, Participar, Enfrentamiento).
- `dao/` -> clases que hacen las consultas SQL (un DAO por tabla).
- `util/FiltroPalabras.java` -> filtro de lenguaje ofensivo (RF-03).
- `servlet/` -> los Servlets (controladores):
  - `LoginServlet`, `LogoutServlet`, `RegistroServlet`, `DashboardServlet`
  - `JuegoDisciplinaServlet`, `EquipoServlet`, `JugadorServlet`, `RondaServlet`,
    `JornadaServlet`, `PartidoServlet`, `ParticipacionServlet`, `GraficaServlet`
  - `InscripcionServlet`, `ConfiguracionServlet`, `ClasificacionServlet`,
    `TorneoServlet`, `EquiposGeneradorServlet`
- `filter/AuthFilter.java` -> protege las paginas si no hay sesion.

Vistas (en `src/main/webapp/`):
- `login.jsp`, `registro.jsp`, `inscripcion.jsp`, `dashboard.jsp`, `index.jsp`
- `clasificacion.jsp`, `configuracion.jsp`, `generar_equipos.jsp`
- `partials/header.jsp` y `footer.jsp` -> encabezado/pie reutilizables.
- `juegos/`, `equipos/`, `jugadores/`, `rondas/`, `jornadas/`, `partidos/`,
  `participaciones/`, `torneos/` -> cada una con `lista.jsp` y/o `form.jsp`.
- `css/estilos.css` -> todo el diseno (colores, botones, tablas).
- `WEB-INF/web.xml` -> configuracion web (pagina de inicio, sesion).

Como se conecta todo:
`navegador -> Servlet -> DAO -> Conexion -> MySQL` y de regreso
`Servlet -> JSP -> navegador`. El `AuthFilter` revisa la sesion antes de cada pagina.

---

## 13. SOLUCION DE PROBLEMAS (LEE AQUI SI ALGO FALLA)

**A) Error al iniciar sesion / pagina de error con "Communications link failure"
o "Access denied for user 'root'":**
- Es la conexion a MySQL. Revisa el PASO 7: usuario y contrasena en `Conexion.java`.
- Verifica que el servicio de MySQL este encendido (en Workbench debe conectar).
- Verifica que ejecutaste `base_datos.sql` (PASO 5).

**B) Error "Unknown database 'arena_uaa'":**
- No se creo la BD. Vuelve al PASO 5 y ejecuta TODO el script.

**C) La pagina se ve sin colores (sin CSS):**
- Asegurate de entrar por la direccion con el nombre del proyecto:
  `http://localhost:8080/arena-uaa-videojuegos/` (no quites esa parte).

**D) "Port 8080 already in use" (puerto ocupado):**
- Ya hay algo usando el 8080. Cierra otras apps/servidores, o en
  `C:\tomcat11\conf\server.xml` cambia `port="8080"` por `port="8081"`
  (y entra por `:8081`).

**E) Errores con `javax.servlet` o `jakarta.servlet`:**
- Este proyecto es para **Tomcat 10/11** (jakarta). Si por error instalaste
  **Tomcat 9**, descarga Tomcat 11 y reconfiguralo en el PASO 8.

**F) NetBeans marca errores rojos al abrir (no encuentra librerias):**
- Es que Maven no termino de descargar. Con internet, clic derecho en el
  proyecto -> **Clean and Build**. Espera a que descargue.

**G) "HTTP Status 404 - Not Found":**
- Revisa la direccion. Debe terminar con `/arena-uaa-videojuegos/`.
- Vuelve a hacer **Clean and Build** y luego **Run**.

**H) Quiero reiniciar la base de datos a como estaba:**
- Vuelve a ejecutar `BD Arena Gallos.sql` completo (al inicio hace
  `DROP DATABASE IF EXISTS arena_uaa;` y la crea de nuevo con los datos).

---

## NOTA DE SEGURIDAD (opcional, por si tu profesor pregunta)
Las contrasenas se guardan en texto plano para que sea facil de probar.
En un sistema real se guardarian "hasheadas" (por ejemplo con SHA-256 o BCrypt).
Es una mejora opcional que puedes mencionar en tu documentacion.

FIN DE LA GUIA. Exito con tu proyecto, Emilio.
