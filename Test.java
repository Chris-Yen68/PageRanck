import file.FileReader;
import file.FileWriter;

import java.io.File;
import java.util.Map;

/**
 * Created by jeanlee on 2017/11/14.
 */
public class Test {
    public static void main(String[] args) {
        FileReader fileReader =  new FileReader(new File("linear/links.txt"));
        FileWriter fileWriter = FileWriter.on("files/Output6.txt");
        Graph graph = new Graph();
        for (String line : fileReader) {
            graph.addVertex(line);
        }
        graph.update();

        int outTimes = graph.getTimes();
        Map<String,Vertex> vertexs = graph.sortedResult();
        for (Vertex vertex : vertexs.values()) {
            fileWriter.println(vertex.getName() + ", " + vertex.getOwn());
        }
        fileWriter.close();
    }
}
