package core;

public class TrafficLight {
    public String id;            // SUMO traffic light ID
    public int x, y;
    public String state = "R";   // R = Red, G = Green
    public double timer = 0;     // Time since last toggle

    public final double greenDuration = 3.0;   // seconds
    public final double redDuration   = 3.0;   // seconds
    
    // offset relative to node
    public int offsetX = 15;
    public int offsetY = -15;

    public TrafficLight(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(double dt) {
        timer += dt;

        if (state.equals("G") && timer >= greenDuration) {
            state = "R";
            timer = 0;
        }
        else if (state.equals("R") && timer >= redDuration) {
            state = "G";
            timer = 0;
        }
    }
}
