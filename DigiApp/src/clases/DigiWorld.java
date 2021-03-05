/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author Miguel Matul <https://github.com/MigueMat4>
 */
public final class DigiWorld {
    private static final String DIGIMON_API_URL = "https://digimon-api.vercel.app/api/digimon/";
    private List<Digimon> digimons;
    private final DefaultTableModel tabla;
    
    public DigiWorld(DefaultTableModel model){
        tabla = model;
    }
    
    public void descargarDatos() throws IOException, InterruptedException{
        System.out.println("Conectando a la API...");
        // código para conectarse a la API y descargar los datos.
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Accept", "application/json")
                .uri(URI.create(DIGIMON_API_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("¡Conexión exitosa! Descargando datos...");
        ObjectMapper mapper = new ObjectMapper();
        // obtener listado de Digimon
        digimons = mapper.readValue(response.body(), new TypeReference<List<Digimon>>() {});
        // ordenar listado de Digimon
        // <Inserte su código aquí>
        Collections.sort(digimons, new Comparator<Digimon>() {
            @Override
            public int compare(Digimon d1, Digimon d2) {
                return new String(d1.getName()).compareTo(new String(d2.getName()));
            }
        });
        // agregar listado de Digimon a la tabla
        digimons.forEach((digimon) -> {
            tabla.addRow(new Object[]{digimon.getName(), digimon.getLevel()});
        });
        System.out.println("¡Datos descargados!");
    }
    
    public List<Digimon> getDigimons() {
        return digimons;
    }
}
