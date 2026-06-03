package com.mycompany.proyxml.Controlador;

import com.mycompany.proyxml.Modelo.Huesped;
import com.mycompany.proyxml.Modelo.InsertarXML;
import com.mycompany.proyxml.Modelo.LeerXML;
import com.mycompany.proyxml.Vista.FrmHotel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControladorHotel {
    private final String NOMBRE_ARCHIVO = "huespedes";
    private final FrmHotel vista;
    private final InsertarXML insertador;
    private final LeerXML lector;
    private List<Huesped> listaHuespedes;

    public ControladorHotel(FrmHotel vista) {
        this.vista = vista;
        this.insertador = new InsertarXML();
        this.lector = new LeerXML();
        cargarDatos(); // Al iniciar, carga los datos del XML
    }

    // Carga la lista desde el archivo y actualiza la tabla
    public void cargarDatos() {
        listaHuespedes = lector.leerXML(NOMBRE_ARCHIVO);
        actualizarTabla();
    }

    // Refresca la tabla con los datos actuales de listaHuespedes
    private void actualizarTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.jTable1.getModel();
        model.setRowCount(0); // limpiar
        for (Huesped h : listaHuespedes) {
            model.addRow(new Object[]{h.getId(), h.getNombre(), h.getApellido(), h.getProcedencia()});
        }
    }

    // Guarda la lista actual en el archivo XML
    private void guardarEnArchivo() {
        try {
            insertador.crearXML(NOMBRE_ARCHIVO, listaHuespedes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Obtiene el próximo ID disponible (máximo + 1)
    private int obtenerNuevoId() {
        int max = 0;
        for (Huesped h : listaHuespedes) {
            if (h.getId() > max) max = h.getId();
        }
        return max + 1;
    }

    // Insertar nuevo huésped
    public void agregarHuesped(String nombre, String apellido, String procedencia) {
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || procedencia.trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios");
            return;
        }
        int nuevoId = obtenerNuevoId();
        Huesped nuevo = new Huesped(nuevoId, nombre, apellido, procedencia);
        listaHuespedes.add(nuevo);
        guardarEnArchivo();
        actualizarTabla();
        limpiarCampos();
        JOptionPane.showMessageDialog(vista, "Huésped guardado con ID: " + nuevoId);
    }

    // Eliminar huésped seleccionado en la tabla
    public void eliminarHuespedSeleccionado() {
        int fila = vista.jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un huésped para eliminar");
            return;
        }
        int idEliminar = (int) vista.jTable1.getValueAt(fila, 0);
        // Confirmar
        int confirm = JOptionPane.showConfirmDialog(vista, "¿Eliminar huésped con ID " + idEliminar + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            listaHuespedes.removeIf(h -> h.getId() == idEliminar);
            guardarEnArchivo();
            actualizarTabla();
            JOptionPane.showMessageDialog(vista, "Eliminado correctamente");
        }
    }

    // Buscar por ID (campo jTextField4)
    public void buscarPorId(String textoId) {
        try {
            int id = Integer.parseInt(textoId.trim());
            for (Huesped h : listaHuespedes) {
                if (h.getId() == id) {
                    // Seleccionar la fila en la tabla
                    DefaultTableModel model = (DefaultTableModel) vista.jTable1.getModel();
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if ((int) model.getValueAt(i, 0) == id) {
                            vista.jTable1.setRowSelectionInterval(i, i);
                            vista.jTable1.scrollRectToVisible(vista.jTable1.getCellRect(i, 0, true));
                            break;
                        }
                    }
                    // Llenar campos para edición
                    vista.txtNombre.setText(h.getNombre());
                    vista.TxtApellidos.setText(h.getApellido());
                    vista.txtProcedencia.setText(h.getProcedencia());
                    return;
                }
            }
            JOptionPane.showMessageDialog(vista, "No se encontró huésped con ID " + id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Ingrese un número de ID válido");
        }
    }

    // Actualizar huésped seleccionado con los datos de los campos
    public void actualizarHuesped() {
        int fila = vista.jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un huésped para actualizar");
            return;
        }
        int idActual = (int) vista.jTable1.getValueAt(fila, 0);
        String nuevoNombre = vista.txtNombre.getText().trim();
        String nuevoApellido = vista.TxtApellidos.getText().trim();
        String nuevaProcedencia = vista.txtProcedencia.getText().trim();
        
        if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || nuevaProcedencia.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Complete todos los campos");
            return;
        }
        
        // Buscar y modificar en la lista
        for (Huesped h : listaHuespedes) {
            if (h.getId() == idActual) {
                h.setNombre(nuevoNombre);
                h.setApellido(nuevoApellido);
                h.setProcedencia(nuevaProcedencia);
                break;
            }
        }
        guardarEnArchivo();
        actualizarTabla();
        limpiarCampos();
        // Volver a seleccionar la misma fila
        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
            if ((int) vista.jTable1.getValueAt(i, 0) == idActual) {
                vista.jTable1.setRowSelectionInterval(i, i);
                break;
            }
        }
        JOptionPane.showMessageDialog(vista, "Huésped actualizado");
    }

    // Limpiar campos de texto
    private void limpiarCampos() {
        vista.txtNombre.setText("");
        vista.TxtApellidos.setText("");
        vista.txtProcedencia.setText("");
    }
}