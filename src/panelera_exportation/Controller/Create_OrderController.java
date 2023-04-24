/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panelera_exportation.Controller;

import Configuration.Conexion;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Create_OrderDTO;
import com.sun.jdi.connect.spi.Connection;
import java.beans.Statement;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Cris
 */
public class Create_OrderController {

    private Connection cnn;
    private Conexion cn = new Conexion();

    public static ArrayList<Create_OrderDTO> consultcreate_Order() {

        return null;

    }

    public List<Create_OrderDTO> traerlasOrdenes() {
        List<Create_OrderDTO> arregloDeOrdenes = new ArrayList<>();
        String sqlTraer = "SELECt employe_id, full_name, product_type, amount_order, destination, date, currency, total,shipping_type from create_orderdto";

        try {
            cn.connectar();
            PreparedStatement preparaConsulta = cn.getCx().prepareStatement(sqlTraer);
            ResultSet resultado = preparaConsulta.executeQuery();

            while (resultado.next()) {
                Create_OrderDTO ordenTraida = new Create_OrderDTO();
                ordenTraida.setEmploye_ID(resultado.getInt("Employe_ID"));
                ordenTraida.setFull_name(resultado.getString("Full_Name"));
                ordenTraida.setProduct_type(resultado.getString("Product_Type"));
                ordenTraida.setAmount_order(resultado.getString("Amount_Order"));
                ordenTraida.setDestination(resultado.getString("Destination"));

                ordenTraida.setFechaEnvio(resultado.getDate("date"));
                ordenTraida.setCurrency(resultado.getString("Currency"));
                ordenTraida.setTota(resultado.getString("Total"));
                ordenTraida.setShipping_type(resultado.getString("Shipping_Type"));
                arregloDeOrdenes.add(ordenTraida);
            }
            return arregloDeOrdenes;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error with the List");
            return Collections.emptyList();
        }
    }

    public List<Create_OrderDTO> listadoTotal() {
        return traerlasOrdenes();
    }

    public Create_OrderDTO consultemploye_ID(int empleye_ID) throws SQLException {
        ResultSet resul;
        Create_OrderDTO CreateDTO = new Create_OrderDTO();
        Conexion cn = new Conexion();
        cn.connectar();

        resul = cn.consultarReg("SELECT * FROM `Create_OrderDTO` WHERE create_orderDTO.employe_ID= " + "employe_ID" + "");

        try {

            while (resul.next()) {
                System.out.println(resul.getString("employe_ID"));
                System.out.println(resul.getString("full_name"));
                System.out.println(resul.getString("product_type"));
                System.out.println(resul.getString("amount_order"));
                System.out.println(resul.getString("destination"));
                System.out.println(resul.getString("date"));
                System.out.println(resul.getString("currency"));
                System.out.println(resul.getString("shipping_type"));

                CreateDTO.setDestination(resul.getString("destination"));
                CreateDTO.setFechaEnvio(resul.getDate("date"));
                CreateDTO.setEmploye_ID(resul.getInt("employe_ID"));
                CreateDTO.setFull_name(resul.getNString("full_name"));
                CreateDTO.setAmount_order(resul.getString("amount_order"));
                CreateDTO.setProduct_type(resul.getString("product_type"));
                CreateDTO.setCurrency(resul.getString("currency"));
                CreateDTO.setShipping_type(resul.getString("Shipping_type"));

            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            cn.desconectar();
        }
        return CreateDTO;
    }

    public boolean CreateOrder(Create_OrderDTO createDTO) {
        boolean flag = false;
        Conexion cn = new Conexion();
        cn.connectar();
        Date fecha = (Date) createDTO.getFechaEnvio();

        if (fecha != null) {
            // Convertir la fecha al formato de fecha de MySQL
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaMySQL = sdf.format(fecha);

            // Insertar la fecha en la base de datos
            int resul = cn.ejecutarSentenciaSql("INSERT INTO Create_OrderDTO (`full_name`, `product_type`, `amount_order`, `destination`, `date`, `currency`, `total`, `shipping_type`) VALUES('" + createDTO.getFull_name() + "','" + createDTO.getProduct_type() + "','" + createDTO.getAmount_order() + "','" + createDTO.getDestination() + "','" + fechaMySQL + "','" + createDTO.getCurrency() + "','" + createDTO.getTota() + "','" + createDTO.getShipping_type() + "')");
            System.out.println("Script: " + resul);
        if (resul == 1) {

            System.out.println("Save Successfully");

            flag = true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo realziar la consulta");
        }
        } 

        
        return flag;
    }

    public boolean DeleteCreateOrder(int employe_ID) {
        boolean flag = false;

        Conexion cn = new Conexion();
        cn.connectar();

        int resul = cn.ejecutarSentenciaSql("DELETE FROM `Create_OrderDTO` WHERE 'employe_ID'= " + employe_ID + "; ");

        if (resul == 1) {
            System.out.println("Deleted Successfully");
            flag = true;
        }

        return flag;

    }

}
