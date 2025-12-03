package core;

public class Node {
    public String id;
    public int x, y;

    public Node(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Node(int x, int y) {
        this.id = "";
        this.x = x;
        this.y = y;
    }
}
