package com.example.application;

import com.vaadin.flow.component.notification.Notification;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class CConexion {

    Connection conectar = null;
    String usuario = "root";
    String contraseña = "adr1an2004";
    String bd = "bdescuela";
    String ip = "localhost";
    String puerto = "3306";

    //  jdbc:mysql://localhost:3306/?user=root

    String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;

    public Connection estableceConexion() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contraseña);
            //JOptionPane.showMessageDialog(null, "La conexion se ha realizado con exito");

        } catch (Exception e) {

            Notification.show("Error al conectar..." + e.toString());
        }
        return conectar;
    }
}
