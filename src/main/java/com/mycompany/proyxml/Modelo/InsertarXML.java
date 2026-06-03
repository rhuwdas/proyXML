package com.mycompany.proyxml.Modelo;

import java.io.File;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class InsertarXML {

    public void crearXML(String nomArchivo, List<Huesped> listaHuespedes) 
            throws TransformerException, Exception {
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        
        Element raiz = document.createElement("HUESPEDES");
        document.appendChild(raiz);
        
        for (Huesped h : listaHuespedes) {
            Element itemHuesped = document.createElement("HUESPED");
            
            Element idNode = document.createElement("ID");
            idNode.appendChild(document.createTextNode(String.valueOf(h.getId())));
            
            Element nombreNode = document.createElement("NOMBRE");
            nombreNode.appendChild(document.createTextNode(h.getNombre()));
            
            Element apellidoNode = document.createElement("APELLIDO");
            apellidoNode.appendChild(document.createTextNode(h.getApellido()));
            
            Element procedenciaNode = document.createElement("PROCEDENCIA");
            procedenciaNode.appendChild(document.createTextNode(h.getProcedencia()));
            
            itemHuesped.appendChild(idNode);
            itemHuesped.appendChild(nombreNode);
            itemHuesped.appendChild(apellidoNode);
            itemHuesped.appendChild(procedenciaNode);
            raiz.appendChild(itemHuesped);
        }
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(nomArchivo + ".xml"));
        transformer.transform(source, result);
    }
}