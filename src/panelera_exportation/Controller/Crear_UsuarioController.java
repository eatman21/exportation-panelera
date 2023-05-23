/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panelera_exportation.Controller;


import Model.Crear_UsuarioDTO;
import Configuration.Conexion;
import Interface_Data.IGestor_de_Datos;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;





/**
 *
 * @author Cris
 */
public class Crear_UsuarioController implements IGestor_de_Datos<Crear_UsuarioDTO>  {
    private com.sun.jdi.connect.spi.Connection cnn;
    private Conexion cn = new Conexion();
    LoginController datoparaguardarloging = new LoginController();
    
    
    public void create_User(Crear_UsuarioDTO createDTO) throws SQLException {
              
      
        
        try {
            cn.connectar();

            String sql = "INSERT INTO Crear_UsuarioDTO (Full_Name, Email,Phone_Number,Address,User_Name,Password,Fecha_nacimiento) VALUES(?,?,?,?,?,?,?)";
           
            
            PreparedStatement st = cn.getConexion().prepareStatement(sql);
            st.setString(1, createDTO.getFull_Name());
            
            st.setString(2, createDTO.getEmail());
            System.out.println("invalid Email");
            
            st.setInt(3, createDTO.getPhone_Number());
           
            st.setString(4, createDTO.getAddress());
            
            st.setString(5, createDTO.getUser_Name());
            
             st.setString(6, createDTO.getPassword());
             
            //Castear fechas
            java.sql.Date Fecha_nacimiento = new java.sql.Date(createDTO.getFecha_nacimiento().getTime());
            st.setDate(7, Fecha_nacimiento);
            
             

            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se realizó un registro.", "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);
            
            datoparaguardarloging.newUserLoginDTO(createDTO.getUser_Name(), createDTO.getPassword());
            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "invalid", "Error al crear Usuario", JOptionPane.ERROR_MESSAGE);
            
            JOptionPane.showMessageDialog(null, "Datos No Registrado.", "Error al crear Usuario", JOptionPane.ERROR_MESSAGE);
            
            System.out.println("Datos errados" + e);
            
            
        }finally {
            cn.desconectar();
        }
        
        
    }

    @Override
    public void creat(Object T) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Crear_UsuarioDTO lectura(int ID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void Actualizar(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void Elimana(int Id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
