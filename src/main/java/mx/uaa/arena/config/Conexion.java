package mx.uaa.arena.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL =
        "jdbc:mysql://localhost:3306/arena_uaa"
        + "?useSSL=false&serverTimezone=America/Mexico_City&allowPublicKeyRetrieval=true";

    private static final String USUARIO  = "root";
    private static final String PASSWORD = "admin";

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver JDBC de MySQL. "
                + "Revisa la dependencia mysql-connector-j en el pom.xml.", e);
        }

        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
