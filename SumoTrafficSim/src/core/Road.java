package core;

public class Road {

    public Node start;
    public Node end;

    public Road(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public double length() {
        return Math.hypot(end.x - start.x, end.y - start.y);
    }
}
