import java.util.List;

public class GPSMapPrinter {
    private static final int WIDTH = 60;
    private static final double MIN_LON = -3.71;
    private static final double MAX_LON = -3.69;

    public static void imprimirMapa(List<GPSData> dataList, List<double[]> paradas) {
        for (GPSData data : dataList) {
            char[] mapa = new char[WIDTH];
            java.util.Arrays.fill(mapa, '.');

            // Marcar paradas
            for (double[] p : paradas) {
                int x = calcularPosX(p[1]);
                if (x >= 0 && x < WIDTH) mapa[x] = '*';
            }

            int pos = calcularPosX(data.longitude);
            String idNormalizado = data.busId.trim().toLowerCase();

            char letra = switch (idNormalizado) {
                case "bus_01" -> 'A';
                case "bus_02" -> 'B';
                case "bus_03" -> 'C';
                default -> '?';
            };
            if (pos >= 0 && pos < WIDTH) mapa[pos] = letra;

            System.out.println("Bus " + letra);
            System.out.println(new String(mapa));
            System.out.println("[" + data.timestamp + "] Velocidad: " + data.speed + " km/h");
            System.out.printf("Lat: %.5f | Lon: %.5f\n", data.latitude, data.longitude);
            System.out.println();
        }
    }

    private static int calcularPosX(double lon) {
        double frac = (lon - MIN_LON) / (MAX_LON - MIN_LON);
        return (int)(frac * (WIDTH - 1));
    }
}
