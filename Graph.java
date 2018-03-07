import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jeanlee on 2017/11/14.
 */
public class Graph {
    private Map<String,Vertex> vertexs = new HashMap<>();
    private int times = 0;

    public void addVertex(String line){
        String[] verTexGroup = line.split(", ");
        if (!vertexs.containsKey(verTexGroup[0])) {
            Vertex vertex = new Vertex(verTexGroup[0]);
            vertexs.put(vertex.getName(), vertex);
            addChild(vertex,verTexGroup[1]);
        }else {
            addChild(vertexs.get(verTexGroup[0]),verTexGroup[1]);
        }
    }

    public void addChild(Vertex parent,String line){
        if (vertexs.containsKey(line)){
            parent.add(vertexs.get(line));
        }else {
            Vertex vertex= new Vertex(line);
            parent.add(vertex);
            vertexs.put(vertex.getName(),vertex);
        }
    }

    public void update(){
        int count = 0;
        boolean canContinueToMinError = true;
        for (Vertex vertex : vertexs.values()) {
            vertex.setReceive(0);
            vertex.setOwn(div(1.0,vertexs.size()));
        }
        while (count < 100 && canContinueToMinError) {
            for (Vertex vertex : vertexs.values()) {
                for (Vertex child : vertex.getChildren()) {
                    child.setReceive(add(mul(mul(div(1.0,vertex.getChildren().size()),vertex.getOwn()),0.65),child.getReceive()));
                }
            }
            for (Vertex vertex : vertexs.values()) {
                vertex.setReceive(add(vertex.getReceive(),0.35));
            }
            for (Vertex vertex : vertexs.values()) {
                if (sub(vertex.getReceive(),vertex.getOwn())  < 0.000001 ){
                    canContinueToMinError =false;
                }
            }
            for (Vertex vertex : vertexs.values()) {
                vertex.setOwn(vertex.getReceive());
                vertex.setReceive(0);
            }
            count++;
        }
        times = count;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public Map<String, Vertex> sortedResult(){
        CompareByValue comparedValue = new CompareByValue(vertexs);
        TreeMap<String,Vertex> sorted = new TreeMap<>(comparedValue);
        sorted.putAll(vertexs);
        return sorted;

    }

    class CompareByValue implements Comparator<String>{
        Map<String,Vertex> maps;

        public CompareByValue(Map<String, Vertex> maps) {
            this.maps = maps;
        }

        @Override
        public int compare(String a, String b) {
            if (maps.get(a).getOwn() <=  maps.get(b).getOwn()){
                return 1;
            }else {
                return -1;
            }
        }
    }
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static double div(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,6,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
}
