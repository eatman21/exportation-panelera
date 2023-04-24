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

    String bd = "panelera_exportation";
    String url = "jdbc:mysql://localhost:3308/";
    String user = "root";
    String password = "";
    String driver = "com.mysql.cj.jdbc.Driver";

    Connection cx;

    public Connection connectar() {

        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url + bd, user, password);
            System.out.println("Connected" + bd);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Not Connected" + bd);
        }
        return cx;

    }

    public Connection getCx() {
        return cx;
    }

    public void setCx(Connection cx) {
        this.cx = cx;
    }

    
    public void desconectar() {
        try {
            cx.close();
        } catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
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
