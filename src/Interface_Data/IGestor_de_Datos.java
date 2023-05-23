/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface_Data;

/**
 *
 * @author Cris
 * @param <T>
 */
public interface IGestor_de_Datos<T> {
    void creat (Object T);
     T lectura (int ID);
     void Actualizar (Object t);
     void Elimana (int Id);
    
}
