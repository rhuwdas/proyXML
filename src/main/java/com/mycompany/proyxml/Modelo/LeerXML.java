package com.mycompany.proyxml.Modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LeerXML {

    public List<Huesped> leerXML(String nomArchivo) {
        List<Huesped> lista = new ArrayList<>();
        try {
            File archivo = new File(nomArchivo + ".xml");
            if (!archivo.exists()) {
                return lista; // vacía
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(archivo);
            document.getDocumentElement().normalize();
            
            NodeList nodos = document.getElementsByTagName("HUESPED");
            for (int i = 0; i < nodos.getLength(); i++) {
                Node nodo = nodos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    int id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    String nombre = element.getElementsByTagName("NOMBRE").item(0).getTextContent();
                    String apellido = element.getElementsByTagName("APELLIDO").item(0).getTextContent();
                    String procedencia = element.getElementsByTagName("PROCEDENCIA").item(0).getTextContent();
                    lista.add(new Huesped(id, nombre, apellido, procedencia));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}