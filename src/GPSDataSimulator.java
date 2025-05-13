import java.util.*;

public class GPSDataSimulator {
    private static List<GPSData> allDataList = new ArrayList<>();
    private static List<BusRoute> rutas;
    private static boolean stopSimulation = false;

    public static void main(String[] args) {
        inicializarRutas();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n |MENÚ PRINCIPAL|");
            System.out.println("1. Simular recorrido y mostrar");
            System.out.println("2. Exportar datos a CSV"); //Para exportar, primero debes generar con 1
            System.out.println("3. Exportar datos a JSON");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> simularRecorrido(scanner);
                case 2 -> DataExporter.exportarCSV(allDataList, "gps_data.csv");
                case 3 -> DataExporter.exportarJSON(allDataList, "gps_data.json");
                case 4 -> System.out.println("Gracias por confiar.");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 4);
    }

    private static void inicializarRutas() {
        rutas = Arrays.asList(
                new BusRoute("Bus_01", Arrays.asList(
                        new double[]{40.4168, -3.7100},
                        new double[]{40.4168, -3.7000},
                        new double[]{40.4168, -3.6900}
                )),
                new BusRoute("Bus_02", Arrays.asList(
                        new double[]{40.4268, -3.7100},
                        new double[]{40.4268, -3.7000},
                        new double[]{40.4268, -3.6900}
                )),
                new BusRoute("Bus_03", Arrays.asList(
                        new double[]{40.4368, -3.7100},
                        new double[]{40.4368, -3.7000},
                        new double[]{40.4368, -3.6900}
                ))
        );
    }

    private static void simularRecorrido(Scanner scanner) {
        System.out.println("Presione Enter para finalizar la simulación en cualquier momento...");

        Thread inputThread = new Thread(() -> {
            scanner.nextLine();
            stopSimulation = true;
        });

        inputThread.start();

        Thread simulationThread = new Thread(() -> {
            allDataList.clear();
            Simulador.simular(rutas, allDataList);
        });

        simulationThread.start();

        while (!stopSimulation) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            simulationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Simulación detenida.");
    }
}
