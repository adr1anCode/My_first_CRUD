package com.example.application.views.main;

import com.example.application.Alumno;
import com.example.application.CConexion;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;


@Route("")


public class MainView extends VerticalLayout {

    private static final String H1 = "";

    public MainView() {

        Alumno alumno = new Alumno();

        AtomicReference<String> id2= new AtomicReference<>("");
        AtomicReference<String> nombre2= new AtomicReference<>("");
        AtomicReference<String> apellido2= new AtomicReference<>("");
        CConexion conexion = new CConexion();
        conexion.estableceConexion();


        TextField nombre = new TextField("Nombre");
        TextField apellido = new TextField("Apellido");
        TextField id = new TextField("Id");
        Grid <Alumno >grid = new Grid<>(Alumno.class); //aqui declaramos la tabla
        grid.setAllRowsVisible(true);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setHeight("500px");
        grid.setWidth("1000px");
        id.setEnabled(false);
        try {
            grid.setColumnOrder( grid.getColumnByKey("codigo"),grid.getColumnByKey("apellidos"), grid.getColumnByKey("nombre"));
        }catch (Exception e){
            Notification.show(e.toString());
        }

        Button guardar = new Button("Guardar");
        Button eliminar = new Button("Eliminar");
        Button actuaiizar = new Button("Modificar");


        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        actuaiizar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        eliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);
        guardar.setWidth("150px");
        actuaiizar.setWidth("150px");
        eliminar.setWidth("150px");
        guardar.setHeight("60px");
        eliminar.setHeight("60px");
        actuaiizar.setHeight("60px");


        id.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        nombre.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        apellido.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        id.setHeight("115px");
        nombre.setHeight("115px");
        apellido.setHeight("115px");
        id.setWidth("220px");
        nombre.setWidth("220px");
        apellido.setWidth("220px");


alumno.mostrarAlumno(grid);

        setMargin(true);

        grid.addSelectionListener(selectionEvent -> { // con esto vamos a obtener los valores de la fila seleccionada
                    Alumno alumnoSeleccionado = selectionEvent.getFirstSelectedItem().orElse(null);
                    if (selectionEvent!=null) {
                        if (alumnoSeleccionado != null) {
                            id2.set(String.valueOf(alumnoSeleccionado.getCodigo()));
                            nombre2.set(String.valueOf(alumnoSeleccionado.getNombre()));
                            apellido2.set(String.valueOf(alumnoSeleccionado.getApellidos()));
                            id.setValue(String.valueOf(id2));
                            nombre.setValue(String.valueOf(nombre2));
                            apellido.setValue(String.valueOf(apellido2));
                        } else {
                            id.setValue("");
                            nombre.setValue("");
                            apellido.setValue("");
                        }

                    }});

        HorizontalLayout horizontal=new HorizontalLayout();
        VerticalLayout vertical=new VerticalLayout();
        vertical.setAlignItems(Alignment.START);
        horizontal.setAlignItems(Alignment.STRETCH);
        horizontal.getAlignItems();

        // este es el add para los textfield
        add(vertical);
        vertical.add(
                new H1("Portal Alumnos")
        );
        horizontal.add(
               new VerticalLayout(

                       id, nombre, apellido
               ),
                grid
        );
        add(horizontal);
        // este add es para los botones
       add(
               new HorizontalLayout(
                       guardar,actuaiizar,eliminar
               )
       );

//BOTON GUARDAR
        guardar.addClickListener(click -> {
            if (nombre.isEmpty()) {
                Notification.show("Ingresa los datos");

            } else {
                Alumno objetoAlumno = new Alumno();
                objetoAlumno.InsertarAlumno(nombre, apellido);
                objetoAlumno.mostrarAlumno(grid);
            }

        });
//BOTON ELIMINAR
        eliminar.addClickListener(click -> {

            ConfirmDialog confirmDialog = new ConfirmDialog();
            confirmDialog.setHeader("¿Confirmar?");
            confirmDialog.setWidth("310px");
            confirmDialog.setHeight("200px");
            // Configurar acciones para el botón de confirmación y cancelación

            confirmDialog.setConfirmButton("Sí", (a) -> {
                Alumno objetoAlumno = new Alumno();
                objetoAlumno.eliminarAlumno(String.valueOf(id2));
                objetoAlumno.mostrarAlumno(grid);
                confirmDialog.close();

            });
            confirmDialog.setCancelButton("No", (b) -> {

                confirmDialog.close();
            });

            confirmDialog.open();
        });


        actuaiizar.addClickListener(click -> {
            Notification.show("Actualizado con exito");
        });

        //BOTON MODIFICAR
        actuaiizar.addClickListener(Click -> {
            ConfirmDialog confirmDialog = new ConfirmDialog();
            confirmDialog.setHeader("¿Confirmar?");
            confirmDialog.setWidth("310px");
            confirmDialog.setHeight("200px");
            // Configurar acciones para el botón de confirmación y cancelación

            confirmDialog.setConfirmButton("Sí", (a) -> {

                Alumno objetoAlumno = new Alumno();
                objetoAlumno.modificarAlumno(String.valueOf(id2),nombre,apellido);
                objetoAlumno.mostrarAlumno(grid);
                confirmDialog.close();
            });
            confirmDialog.setCancelButton("No", (b) -> {

                confirmDialog.close();
            });

            confirmDialog.open();

        });


    }

}





