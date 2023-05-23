/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panelera_exportation.Controller;

import Configuration.Conexion;
import Funciones.Endcoder;
import com.sun.jdi.connect.spi.Connection;
import java.beans.Encoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Cris
 */
public class LoginController {

    private Connection cnn;
    private Conexion cn = new Conexion();
    private String passwordDesencriptada;
    private String passwordConsultada;
    Endcoder enconder = new Endcoder();

    public void validarSiCoincideClave(String m) {
        try {
            cn.connectar();
            String compararClave = "SELECT password FROM Login_DTO WHERE username = ?";
            PreparedStatement preparaConsulta = cn.getConexion().prepareStatement(compararClave);
            preparaConsulta.setString(1, m);
            ResultSet resul = preparaConsulta.executeQuery();

            if (resul.next()) {
                passwordConsultada= resul.getString("password");
                enconder.decrypt(passwordConsultada);
                passwordDesencriptada = enconder.getClave_dencrypt();
                System.out.println("Validando la clave con el usuario ingresado: "+passwordConsultada);
            }else{
                System.out.println("No se desencripta:");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean validateusername(String username, String password) throws SQLException, Exception {
        validarSiCoincideClave(username);

        if (password.equals(this.passwordDesencriptada)) {
            try {
            cn.connectar();
            String sqlTraer = "SELECT username, password FROM Login_DTO WHERE username = ? AND password = ?";
            PreparedStatement preparaConsulta = cn.getConexion().prepareStatement(sqlTraer);
            preparaConsulta.setString(1, username);
            System.out.println("Username is: " + username);
            preparaConsulta.setString(2, passwordConsultada);
            System.out.println("Query: " + sqlTraer);

            System.out.println("Password secuere: " + this.passwordConsultada);
            ResultSet resul = preparaConsulta.executeQuery();
            System.out.println("ResultSet: "+resul.toString());
            if (resul.next()) {
                System.out.println("Nombre de usuario:"+resul.getString("username"));
                System.out.println("Password del usuario: "+resul.getString("password"));
                System.out.println("Nombre de usuario:"+username);
                System.out.println("Password del usuario: "+password);
                return true;
            }else{
                System.out.println("Nada para recuperar en: "+resul.toString());
            }
        } catch (SQLException e) {
            System.out.println("No se realiza el login: " + e.getMessage());
        } finally {
            cn.desconectar();
        }
        
        }else{
            System.out.println("No se pudo realizar la consulta:\nLas contraseñas no coinciden");
            JOptionPane.showMessageDialog(null, "No se pudo realizar la consulta:\nLas contraseñas no coinciden o el usuario no existe.\nComprueba los datos ingresados.");
        }
        return false;
    }

    public void newUserLoginDTO(String username, String password) {
        try {
            cn.connectar();

            String sql = "INSERT INTO Login_DTO (UserName,Password) VALUES(?,?)";

            PreparedStatement st = cn.getConexion().prepareStatement(sql);
            st.setString(1, username);

            st.setString(2, password);
            st.executeUpdate();
            System.out.println("Se realizó un registro.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "invalid", "Error al crear LoginDto", JOptionPane.ERROR_MESSAGE);

            JOptionPane.showMessageDialog(null, "Datos No Registrado.", "Error al crear Usuario", JOptionPane.ERROR_MESSAGE);

            System.out.println("Datos errados" + e);

        } finally {
            cn.desconectar();
        }
    }
}
