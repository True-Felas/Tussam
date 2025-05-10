import java.util.List;

public class BusRoute {
    public String busId;
    public List<double[]> paradas;

    public BusRoute(String busId, List<double[]> paradas) {
        this.busId = busId;
        this.paradas = paradas;
    }
}
