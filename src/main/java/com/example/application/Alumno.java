package com.example.application;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Alumno {
    String codigo;
    String nombre;
    String apellidos;

    List<Alumno> alumno = new ArrayList<>();


    public Alumno(String codigo, String nombre, String apellidos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
    public Alumno(){

    }

    public void InsertarAlumno(TextField paraNombres, TextField paraApellidos) {
        setNombreAlumno(paraNombres.getValue());
        setApellidosAlumno(paraApellidos.getValue());

        CConexion objetoConexion = new CConexion();
        String consulta = "insert into Alumnos (nombres, apellidos)values (?,?) ";
        try {

            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, getNombre());
            cs.setString(2, getApellidos());
            cs.execute();

            Notification.show("Se inserto correctamente el alumno");


        } catch (Exception e) {
            Notification.show("No se inserto correctamente ");

        }


    }
    public void eliminarAlumno(String paramCodigo){
      setCodigo(paramCodigo);
        CConexion objetoConexion = new CConexion();

        String consulta =" DELETE FROM Alumnos WHERE alumnos.id=?;";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, Integer.parseInt(getCodigo()));
            cs.execute();
            Notification.show("Se elimino correctamente");
        } catch (Exception e) {
            Notification.show("Ocurrio un error al eliminar ");
        }
    }
    public void modificarAlumno(String paramCodigo, TextField paramNombres, TextField paramApellidos) {

        setCodigo(paramCodigo);
        setNombreAlumno(paramNombres.getValue());
        setApellidosAlumno(paramApellidos.getValue());

        CConexion objetoConexion = new CConexion();

        String consulta = "UPDATE Alumnos SET alumnos.nombres =?, alumnos.apellidos=? WHERE alumnos.id=?;";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, getNombre());
            cs.setString(2, getApellidos());
            cs.setInt(3, Integer.parseInt(getCodigo()));

            cs.execute();

            Notification.show("Modificacion exitosa");
        } catch (Exception e) {
          Notification.show("No se modifico ");
        }
    }
    public void mostrarAlumno(Grid tableAlumnos) {

        CConexion objetoConexion = new CConexion();

        String sql = "";


        sql = "select *from Alumnos";

        String datas[] = new String[3];


        Statement st;

        try {
            st = objetoConexion.estableceConexion().createStatement();

            ResultSet rs = st.executeQuery(sql); //ejecutamos el query para que nos muestre la info de la bd


             //error creo que el arraylist esta guardando tal vez hay que eliminar

            while (rs.next()) {

                datas[0] = rs.getString(1);   // Esta parte obtiene la info de cada columna de la base de datos
                datas[1] = rs.getString(2);
                datas[2] = rs.getString(3);

                   alumno.add(new Alumno(datas[0],datas[1],datas[2]));



                datas[0]="";
                datas[1]="";
                datas[2]="";

                //aqui le pasamos los datos al grid

                tableAlumnos.setItems(alumno);



            }

        } catch (Exception e) {
            Notification.show("No se pudo mostrar los registros " + e.toString());
        }

    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombre = nombreAlumno;
    }
    public void setApellidosAlumno(String apellidosAlumno) {
        this.apellidos = apellidosAlumno;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
}
