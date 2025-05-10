import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataExporter {
    public static void exportarCSV(List<GPSData> datos, String archivo) {
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write("busId,timestamp,latitude,longitude,speed\n");
            for (GPSData d : datos) {
                writer.write(d.toCSV() + "\n");
            }
            System.out.println("Datos exportados a " + archivo);
        } catch (IOException e) {
            System.out.println("Error exportando CSV: " + e.getMessage());
        }
    }

    public static void exportarJSON(List<GPSData> datos, String archivo) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(datos, writer);
            System.out.println("Datos exportados a " + archivo);
        } catch (IOException e) {
            System.out.println("Error exportando JSON: " + e.getMessage());
        }
    }
}
