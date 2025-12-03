package core;

import java.util.List;

public class Lane {
    public String id;
    public List<Double> shape; // flat list x1,y1,x2,y2,...
	public int[] xs;
	public int[] ys;
	public double length;

    public Lane(String id, List<Double> shape) {
        this.id = id;
        this.shape = shape;
    }
}
