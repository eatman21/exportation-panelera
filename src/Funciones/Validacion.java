/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Cris
 */
public class Validacion {

    public boolean ValidarEmail(String Email) {
//    Patron para validar el Email  //
        Pattern partern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[_A-Za-z](2,))$");
        Matcher match = partern.matcher(Email);

        return match.find();
       
    }
}
