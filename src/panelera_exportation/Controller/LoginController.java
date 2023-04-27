/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panelera_exportation.Controller;

import Configuration.Conexion;
import com.sun.jdi.connect.spi.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Cris
 */
public class LoginController {

    private Connection cnn;
    private Conexion cn = new Conexion();

    public boolean validateusername(String username, String password) throws SQLException {

        boolean flag = false;

        try {
            cn.connectar();
            String sqlTraer="SELECT username, password FROM Login_DTO WHERE username = '" + username + "' and password = '" + password + "'";
            
            PreparedStatement preparaConsulta = cn.getConexion().prepareStatement(sqlTraer);
            ResultSet resul = preparaConsulta.executeQuery();

            
            if (resul.next()) {
                System.out.println(resul.getString("username"));
                System.out.println(resul.getString("password"));
                System.out.println(username);
                System.out.println(password);
                if (username.equals(resul.getString("username")) && password.equals(resul.getString("password"))) {
                    flag = true;
                }

            }

        } catch (SQLException e) {
            System.out.println();

        } finally {
            cn.connectar();
        }
        return flag;
    }

}
