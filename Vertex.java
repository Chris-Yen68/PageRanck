import java.util.LinkedList;

/**
 * Created by jeanlee on 2017/11/14.
 */
public class Vertex {
    private String name;

    public Vertex() {
    }

    public Vertex(String name) {

        this.name = name;
    }

    private double own;
    private double receive;
    private LinkedList<Vertex> children = new LinkedList<>();

    public double getOwn() {
        return own;
    }

    public void setOwn(double own) {
        this.own = own;
    }

    public double getReceive() {
        return receive;
    }

    public void setReceive(double receive) {
        this.receive = receive;
    }

    public LinkedList<Vertex> getChildren() {
        return children;
    }

    public void add(Vertex vertex){
        children.add(vertex);
    }

    public String getName() {
        return name;
    }
}
