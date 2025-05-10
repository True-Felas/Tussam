public class GPSData {
    public String busId;
    public String timestamp;
    public double latitude;
    public double longitude;
    public int speed;

    public GPSData(String busId, String timestamp, double latitude, double longitude, int speed) {
        this.busId = busId;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
    }

    public String toCSV() {
        return busId + "," + timestamp + "," + latitude + "," + longitude + "," + speed;
    }
}
