package core;

import java.awt.*;

public class Vehicle {

    public Color color;
    public int roadIndex;     // current road
    public double t;          // position (0 → 1)
    public double speed;      // speed per update

    public double x, y;       // actual coordinates

    public Vehicle(int roadIndex, double speed, Color c) {
        this.roadIndex = roadIndex;
        this.speed = speed;
        this.color = c;
        this.t = 0;
    }

	public Vehicle(double x2, double y2, double speed2) {
		this.x = x2;
		this.y = y2;
		this.speed = speed2;
	}
}
