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

    private String url = "jdbc:mysql://localhost/panelera_exportation";
    private String usuario = "root";
    private String clave = "";
    String driver = "com.mysql.jdbc.Driver";
    private Connection conexion;

    Connection cx;

    public Conexion() {

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
            Class.forName(driver);
            conexion = DriverManager.getConnection(this.url, this.usuario, this.clave);
            System.out.println("Conectado");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {
        connectar();
        try {
            conexion.close();
            System.out.println("Desconectado");
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
            PreparedStatement preSt = cx.prepareStatement(sentSQL);
            preSt.execute();
            return 1;

        } catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ResultSet consultarReg(String sentSQL) {
        try {

            PreparedStatement preSt = cx.prepareStatement(sentSQL);

            ResultSet Resl = preSt.executeQuery();
            return Resl;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
}
