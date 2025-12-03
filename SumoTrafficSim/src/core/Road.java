package core;

import java.util.ArrayList;
import java.util.List;

public class Road {

    public String id;
    public Node start;
    public Node end;

    public List<Lane> lanes = new ArrayList<>(); 

    public Road(String id, Node start, Node end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public Road(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public double length() {
        return Math.hypot(end.x - start.x, end.y - start.y);
    }
}
