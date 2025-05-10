import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Simulador {
    private static final int DELAY_MS = 1500;
    private static final int PASOS_POR_TRAMO = 5;
    private static final int MIN_SPEED = 20;
    private static final int MAX_SPEED = 100;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static void simular(List<BusRoute> rutas, List<GPSData> allDataList) {
        Random random = new Random();
        LocalDateTime baseTime = LocalDateTime.now();

        for (BusRoute ruta : rutas) {
            LocalDateTime currentTime = baseTime;
            List<double[]> paradas = ruta.paradas;

            for (int i = 0; i < paradas.size() - 1; i++) {
                double[] inicio = paradas.get(i);
                double[] fin = paradas.get(i + 1);

                for (int paso = 0; paso < PASOS_POR_TRAMO; paso++) {
                    double lat = inicio[0] + (fin[0] - inicio[0]) * paso / PASOS_POR_TRAMO;
                    double lon = inicio[1] + (fin[1] - inicio[1]) * paso / PASOS_POR_TRAMO;
                    int speed = MIN_SPEED + random.nextInt(MAX_SPEED - MIN_SPEED + 1);
                    String timestamp = currentTime.format(FORMATTER);

                    GPSData data = new GPSData(ruta.busId, timestamp, lat, lon, speed);
                    allDataList.add(data);

                    List<GPSData> datosActuales = datosActualesPorBus(allDataList);
                    GPSMapPrinter.imprimirMapa(datosActuales, paradas);
                    currentTime = currentTime.plusMinutes(1);
                    esperar();
                }

                // Parada
                for (int p = 0; p < 3; p++) {
                    GPSData parada = new GPSData(ruta.busId, currentTime.format(FORMATTER), fin[0], fin[1], 0);
                    allDataList.add(parada);

                    List<GPSData> datosActuales = datosActualesPorBus(allDataList);
                    GPSMapPrinter.imprimirMapa(datosActuales, paradas);
                    currentTime = currentTime.plusMinutes(1);
                    esperar();
                }
            }
        }
    }

    private static void esperar() {
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<GPSData> datosActualesPorBus(List<GPSData> lista) {
        Map<String, GPSData> ultimos = new HashMap<>();
        for (GPSData d : lista) {
            ultimos.put(d.busId, d);
        }
        return new ArrayList<>(ultimos.values());
    }
}
