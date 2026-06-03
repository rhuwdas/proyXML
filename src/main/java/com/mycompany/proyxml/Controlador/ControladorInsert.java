package com.mycompany.proyxml.Controlador;

import com.mycompany.proyxml.Modelo.Huesped;
import com.mycompany.proyxml.Modelo.InsertarXML;
import com.mycompany.proyxml.Vista.FrmHotel;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class ControladorInsert {
    List<Huesped> lista = new ArrayList<>();
    InsertarXML insert = new InsertarXML();
    FrmHotel frmHotel =  new FrmHotel();
    String nomArch = "Huesped";

    public ControladorInsert(FrmHotel frmHotel,InsertarXML insert  ) {
        this.lista = null;
        this.frmHotel = frmHotel;
        this.insert = insert;
    }
    public void insertarDatos(Huesped huesped) throws ParserConfigurationException, Exception{
        lista.add(huesped);
        try {
            this.insert.crearXML(nomArch, lista);
        } catch (TransformerException ex) {
            System.getLogger(ControladorInsert.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    
    
}
