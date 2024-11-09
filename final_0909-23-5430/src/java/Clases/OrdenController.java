/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdenController {
    Orden[] tablaOrden;
    int indiceArray;
    private ConexionBaseDeDatos conectorBD;
    private Connection conexion;
    private PreparedStatement statement = null;
    private ResultSet result = null;
    
    public void abrirConexion(){
        conectorBD= new ConexionBaseDeDatos();
        conexion=conectorBD.conectar();
    }       
    
    public String guardarOrden(Orden orden){        
        String sql = "INSERT INTO alumno(id_orden, fecha, cliente, total, observaciones, tipo_orden_id_tipo) ";
             sql += " VALUES(?,?,?,?,?,?)";              
       try{     
            abrirConexion();
            statement = conexion.prepareStatement(sql); 
            statement.setInt(1, orden.getId());
            statement.setString(2, orden.getFecha());
            statement.setString(3, orden.getCliente());
            statement.setInt(4, orden.getTotal());
            statement.setString(5, orden.getObservacion());
                int resultado = statement.executeUpdate(); 
                if(resultado > 0){
                    return String.valueOf(resultado);
                }else{
                    return String.valueOf(resultado);
                }
        }catch(SQLException e){ 
            return e.getMessage();
        }
    }
    
    public void getOrdenes(StringBuffer respuesta){   
        String sql="SELECT orden_de_trabajo.id_orden, orden_de_trabajo.fecha, orden_de_trabajo.cliente, orden_de_trabajo.total, orden_de_trabajo.observaciones, orden_de_trabajo.tipo_orden_id_tipo  FROM orden_de_trabajo";
        try{
        abrirConexion();
        statement= conexion.prepareStatement(sql);                        
        result = statement.executeQuery();            
            if(result!=null){
                while (result.next()){
                respuesta.append("<tr>");//crear la fila y la etique td son las columnas
                respuesta.append("<td >").append(result.getString("id_orden")).append("</td>");
                respuesta.append("<td >").append(result.getString("fecha")).append("</td>");
                respuesta.append("<td >").append(result.getString("cliente")).append("</td>");
                respuesta.append("<td >").append(result.getString("total")).append("</td>");
                respuesta.append("<td >").append(result.getString("observaciones")).append("</td>");
                respuesta.append("<td id=\"").append(result.getString("tipo_orden_id_tipo"))
                        .append("\"  onclick=\"eliminarOrden(this.id);\">")
                        .append(" <a class=\"btn btn-warning\"'><i class=\"fas fa-edit\"></i>  </a>"
                                +" <a class=\"btn btn-danger\"'> <i class=\"fas fa-trash-alt\"></i> </a>"
                                + " <td></tr>");
                }
            }else{
                respuesta.append("error al consultar");
            }           
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public String eliminarOrden(int id){        
        String sql = "DELETE FROM alumno WHERE numero_carne="+id;              
       try{     
            abrirConexion();
            statement = conexion.prepareStatement(sql); 
            int resultado = statement.executeUpdate();
            if(resultado > 0){
                return String.valueOf(resultado);
            }else{
                return String.valueOf(resultado);
            }
        }catch(SQLException e){ 
            return e.getMessage();
        }
    }
    
    // *** FUNCIONALIDAD PARA TRABAJAR CON ARRAYS ***
    public OrdenController(){// Construcctor para trabar con arrays
        this.tablaOrden = new Orden[100];
        this.indiceArray=0;
    }
    
     public void guardarOrdenEnArray(Orden orden){
        this.tablaOrden[this.indiceArray]=orden;  
        this.indiceArray=this.indiceArray+1;
        // procedimiento para almacenar en la Base de Datos
    }
    
    public Orden[] getOrdenFromArray(){
        return this.tablaOrden;
    }
    
}