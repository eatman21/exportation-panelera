/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Conexion {

    private String url;
    private String usuario;
    private String clave;
    private String driver;
    private Connection conexion;

    // Load configuration from file

    Connection cx;

    public Conexion() {
        // Load database configuration from external file
        this.url = ConfigLoader.getDatabaseURL();
        this.usuario = ConfigLoader.getDatabaseUser();
        this.clave = ConfigLoader.getDatabasePassword();
        this.driver = ConfigLoader.getDatabaseDriver();
    }

    public Connection getCx() {
        return cx;
    }

    public void setCx(Connection cx) {
        this.cx = cx;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void connectar() {
        try {
            System.out.println("=== Database Connection Debug ===");
            System.out.println("URL: " + this.url);
            System.out.println("User: " + this.usuario);
            System.out.println("Driver: " + this.driver);
            System.out.println("================================");

            Class.forName(driver);
            conexion = DriverManager.getConnection(this.url, this.usuario, this.clave);
            System.out.println("Conectado");
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Connection failed with URL: " + this.url);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Desconectado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Conexion cn = new Conexion();
        cn.connectar();
    }

    public int ejecutarSentenciaSql(String sentSQL) {
        try {
            if (conexion != null) {
                PreparedStatement preSt = conexion.prepareStatement(sentSQL);
                preSt.execute();
                return 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ResultSet consultarReg(String sentSQL) {
        try {
            if (conexion != null) {
                PreparedStatement preSt = conexion.prepareStatement(sentSQL);
                ResultSet Resl = preSt.executeQuery();
                return Resl;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
