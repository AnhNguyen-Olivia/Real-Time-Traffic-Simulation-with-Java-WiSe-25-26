
import java.util.logging.Logger;
public class Vehicle {

    private static final Logger logger = Logger.getLogger(Vehicle.class.getName());

    private String id;
    private String edge;
    private double speed;
    private String color;

    public Vehicle(String id) {
        this.id = id;
    }

    public void update(String edge, double speed, String color) {
        this.edge = edge;
        this.speed = speed;
        this.color = color;
        logger.fine("Updated vehicle " + id + " (edge=" + edge + ", speed=" + speed + ")");
    }

    public String getId() {
        return id;
    }

    public String getEdge() {
        return edge;
    }

    public double getSpeed() {
        return speed;
    }

    public String getColor() {
        return color;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        logger.fine("Speed set for " + id + " to " + speed);
    }

    @Override
    public String toString() {
        return "Vehicle{id='" + id + "', edge='" + edge +
                "', speed=" + speed + ", color='" + color + "'}";
    }
}
