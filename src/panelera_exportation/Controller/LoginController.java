/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panelera_exportation.Controller;
import Configuration.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 *
 * @author Cris
 */
public class LoginController {
    
    public boolean validateusername(String username, String password) throws SQLException{
        Conexion cn= new Conexion();
        cn.connectar();
        boolean flag = false;
        
        try {
            ResultSet resul= cn.consultarReg("SELECT * FROM `Login_DTO`");
            while (resul.next()){
                System.out.println(resul.getString("username"));
                 System.out.println(resul.getString("password"));
                 System.out.println(username);
                 System.out.println(password);
                 if (username.equals(resul.getString("username"))&& password.equals(resul.getString("password")))
                     
                     
                 {
                    flag= true;
                }
                 
            }
            
        } catch (SQLException e) {
            System.out.println();
            
        }finally{
            cn.connectar();
        }
        return flag;
    }
    
    
    
    
   
    
    
    
}
