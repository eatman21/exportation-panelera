/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panelera_exportation.Controller;

import Configuration.Conexion;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import Model.Create_OrderDTO;

/**
 *
 * @author Cris
 */
public class Create_OrderController {

    private Conexion cn = new Conexion();

    public List<Create_OrderDTO> traerlasOrdenes() {
        List<Create_OrderDTO> arregloDeOrdenes = new ArrayList<>();
        String sqlTraer = "SELECT employe_id, full_name, product_type, amount_order, destination, date, currency, total, shipping_type FROM create_orderdto";

        cn.connectar();
        try (PreparedStatement preparaConsulta = cn.getConexion().prepareStatement(sqlTraer);
             ResultSet resultado = preparaConsulta.executeQuery()) {

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
            JOptionPane.showMessageDialog(null, "Error with the List: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            cn.desconectar();
        }
    }

    public List<Create_OrderDTO> listadoTotal() {
        return traerlasOrdenes();
    }

    public Create_OrderDTO consultemploye_ID(int empleye_ID) throws SQLException {
        Create_OrderDTO CreateDTO = new Create_OrderDTO();
        String consulta = "SELECT employe_ID, full_name, product_type, amount_order, destination, date, currency, total, shipping_type FROM Create_OrderDTO WHERE employe_ID = ?";

        cn.connectar();
        try (PreparedStatement preparaConsulta = cn.getConexion().prepareStatement(consulta)) {
            preparaConsulta.setInt(1, empleye_ID);

            try (ResultSet resultado = preparaConsulta.executeQuery()) {
                if (resultado.next()) {
                    CreateDTO.setDestination(resultado.getString("destination"));
                    CreateDTO.setFechaEnvio(resultado.getDate("date"));
                    CreateDTO.setEmploye_ID(resultado.getInt("employe_ID"));
                    CreateDTO.setFull_name(resultado.getString("full_name"));
                    CreateDTO.setAmount_order(resultado.getString("amount_order"));
                    CreateDTO.setProduct_type(resultado.getString("product_type"));
                    CreateDTO.setCurrency(resultado.getString("currency"));
                    CreateDTO.setShipping_type(resultado.getString("Shipping_type"));
                    CreateDTO.setTota(resultado.getString("total"));
                } else {
                    JOptionPane.showMessageDialog(null, "No hay registros");
                }
            }
        } catch (Exception e) {
            System.out.println("Problemas al consultar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cn.desconectar();
        }
        return CreateDTO;
    }

    public void crearOrder(Create_OrderDTO createDTO) throws SQLException {
        String sql = "INSERT INTO Create_OrderDTO (full_name, product_type, amount_order, destination, date, currency, total, shipping_type) VALUES(?,?,?,?,?,?,?,?)";

        cn.connectar();
        try (PreparedStatement st = cn.getConexion().prepareStatement(sql)) {
            st.setString(1, createDTO.getFull_name());
            st.setString(2, createDTO.getProduct_type());
            st.setString(3, createDTO.getAmount_order());
            st.setString(4, createDTO.getDestination());

            // Castear fechas
            Date fechaEnvio = (Date) createDTO.getFechaEnvio();
            if (fechaEnvio != null) {
                long tiempoEnvio = fechaEnvio.getTime();
                System.out.println("Tiempo envio: " + tiempoEnvio);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaMySQL = sdf.format(createDTO.getFechaEnvio());
                System.out.println("Fecha formateada: " + fechaMySQL);
                st.setString(5, fechaMySQL);
            } else {
                st.setDate(5, fechaEnvio);
            }

            st.setString(6, createDTO.getCurrency());
            st.setString(7, createDTO.getTota());
            st.setString(8, createDTO.getShipping_type());

            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se realizó un registro.", "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor comprueba los datos.", "Error al crear", JOptionPane.ERROR_MESSAGE);
            System.out.println("Datos errados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cn.desconectar();
        }
    }

    public void deleteOrder(int employe_ID) {
        String sql = "DELETE FROM create_orderdto WHERE employe_ID = ?";

        cn.connectar();
        try (PreparedStatement stmt = cn.getConexion().prepareStatement(sql)) {
            stmt.setInt(1, employe_ID);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Data Deleted");
            } else {
                JOptionPane.showMessageDialog(null, "Error to Delete");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Deleting: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cn.desconectar();
        }
    }

    public void actualizarData(Create_OrderDTO actualiza, int id) {
        String sql = "UPDATE create_orderdto SET full_name=?, product_type=?, amount_order=?, destination=?, currency=?, Total=?, shipping_type=? WHERE employe_ID = ?";

        cn.connectar();
        try (PreparedStatement st = cn.getConexion().prepareStatement(sql)) {
            st.setString(1, actualiza.getFull_name());
            st.setString(2, actualiza.getProduct_type());
            st.setString(3, actualiza.getAmount_order());
            st.setString(4, actualiza.getDestination());
            st.setString(5, actualiza.getCurrency());
            st.setString(6, actualiza.getTota());
            st.setString(7, actualiza.getShipping_type());
            st.setInt(8, id);

            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Actualizados");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Actualizar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cn.desconectar();
        }
    }

}
