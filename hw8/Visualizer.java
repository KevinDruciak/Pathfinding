package hw8;

import com.sun.deploy.net.MessageHeader;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Visualizer {

    private Map<String, Vertex<String>> vertices;
    private SparseGraph<String, String> graph;
    private String startName;
    private String endName;
    private PathVisualizer pathVisualizer;
    private String[][] array;
    private int i;
    private int j;
    private Graphics g;
    private LinkedList<Vertex<String>> path2 = new LinkedList<>();


    public Visualizer(Map<String, Vertex<String>> vertices, SparseGraph<String, String> graph, String startName, String endName, PathVisualizer pathVisualizer, Graphics g) {
        this.vertices = vertices;
        this.graph = graph;
        this.startName = startName;
        this.endName = endName;
        this.pathVisualizer = pathVisualizer;
        this.i = 0;
        this.j = 0;
        this.g = g;

        convertToArray();
        findPath();
        pathVisualizer.setA();
    }

    private void printPath() {

    }
    private void convertToArray() {
        array = new String[(int) Math.ceil(Math.sqrt(vertices.size()))][(int) Math.ceil(Math.sqrt(vertices.size()))];

        for (Vertex<String> v : graph.vertices()) {
            if (this.i == (int) Math.sqrt(vertices.size())) {
                if (j == 94) {
                    break;
                }
                this.j++;
                this.i = 0;
            }
            array[this.i][this.j] = v.get();
            graph.setIJ(v, i, j);
            //System.out.println(v.get());
            this.i++;
        }
    }
    private class VertexComparator implements Comparator<Vertex<String>> {
        public int compare(Vertex<String> v1, Vertex<String> v2) {
            return (int) (graph.getDistance(v1) - graph.getDistance(v2));
        }
    }


    private void findPath() {
        long first = System.currentTimeMillis();

        Thread thread1 = new Thread() {
            public void run() {
                Vertex<String> start = vertices.get(startName);
                Vertex<String> end = vertices.get(endName);
                PriorityQueue<Vertex<String>> pq = new PriorityQueue<>(new Visualizer.VertexComparator());



                double totalDist = -1;

                //start element
                pq.add(start);
                graph.setDistance(start, 0);

                //go through vertices
                for (int t = 0; t < vertices.size(); t++) {
                    for (int p = 0; p < 5000; p++) {
                        System.out.print(" ");
                    }

                    Vertex<String> w = pq.remove();
                    if (w == end) {
                        break;
                    }
                    graph.setExplored(w, true);
                    //render(g);
                    //go through each neighboring vertex
                    for (Edge<String> u : graph.outgoing(w)) { //unexplored
                        if (!graph.getExplored(graph.to(u))) {
                            double label = (double) graph.label(u);
                            double d = graph.getDistance(w) + label;
                            //if path better
                            if (d < graph.getDistance(graph.to(u))) {
                                graph.setDistance(graph.to(u), d);
                                graph.label(graph.to(u), u);
                                pq.add(graph.to(u));
                            }
                        }
                    }
                }
                //get total distance
                totalDist = graph.getDistance(end);
                // These method calls will create and print the path for you
                List<Edge<String>> path = getPath(end, start);
                printPath(path, totalDist);
            }
        };

        thread1.start();

        long last = System.currentTimeMillis();
        System.out.println(last - first);

    }

    private List<Edge<String>> getPath(Vertex<String> end,
                                              Vertex<String> start) {
        if (graph.label(end) != null) {
            List<Edge<String>> path = new ArrayList<>();

            Vertex<String> cur = end;
            Edge<String> road;
            while (cur != start) {
                road = (Edge<String>) graph.label(cur);  // unchecked cast ok
                path.add(road);
                cur = graph.from(road);
            }
            return path;
        }
        return null;
    }

    // Print the path found.
    private void printPath(List<Edge<String>> path,
                                  double totalDistance) {
        if (path == null) {
            System.out.println("No path found");
            return;
        }
        Thread thread2 = new Thread() {
            public void run() {
                System.out.println("Total Distance: " + totalDistance);
                for (int y = path.size() - 1; y >= 0; y--) {
                    //System.out.println(path.size());
                //for (int y = 0; y < path.size(); y++) {
                    for (int f = 0; f < 500000; f++) {
                        System.out.print(" ");
                    }
                    if (y == path.size() - 1) {
                            graph.setOrder(graph.from(path.get(y)), 0);
                            path2.addFirst(graph.from(path.get(y)));
                            graph.setY(graph.from(path.get(y)));
                            graph.setOrder(graph.to(path.get(y)), 1);
                            path2.addFirst(graph.to(path.get(y)));
                            graph.setY(graph.to(path.get(y)));

                        //System.out.println(a + " lol");
                    } else {
                        graph.setOrder(graph.to(path.get(y)), Math.abs((y) - path.size())); //
                        System.out.println(Math.abs((y) - path.size()));
                        //path2.add(graph.to(path.get(a)));
                        path2.addFirst(graph.to(path.get(y))); //
                        graph.setY(graph.to(path.get(y)));
                    }
                    //System.out.println(path.get(i).get() + " "
                    //        + graph.label(path.get(i)));
                    //System.out.println(graph.to(path.get(y)));
                    //graph.setY(graph.to(path.get(y)));
                    //graph.setY(graph.from(path.get(y)));

                }
            }
        };
        thread2.run();
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        for (int h = 0; h < (int) Math.sqrt(vertices.size()); h++) {
            for (int k = 0; k < (int) Math.sqrt(vertices.size()); k++) {

                g.setColor(Color.black);
                g.fillRect((int) (pathVisualizer.getWidth() / Math.sqrt(vertices.size()) * h), (int) (pathVisualizer.getHeight() / Math.sqrt(vertices.size()) * k), (int) (pathVisualizer.getWidth() / Math.sqrt(vertices.size()))-1, (int) (pathVisualizer.getHeight() / Math.sqrt(vertices.size()))-1);
                //g.fillOval((int) (1600 / Math.sqrt(vertices.size()) * h), (int) (900 / Math.sqrt(vertices.size()) * k), (int) (1600 / Math.sqrt(vertices.size())), (int) (900 / Math.sqrt(vertices.size())));
                //if (graph.label(array[i][j])
                if (graph.getExplored(vertices.get(array[h][k]))) {
                    g.setColor(Color.RED);
                    //g.fillOval((int) (1600 / Math.sqrt(vertices.size()) * h), (int) (900 / Math.sqrt(vertices.size()) * k), (int) (1600 / Math.sqrt(vertices.size())), (int) (900 / Math.sqrt(vertices.size())));
                    g.fillRect((int) (pathVisualizer.getWidth() / Math.sqrt(vertices.size()) * h), (int) (pathVisualizer.getHeight() / Math.sqrt(vertices.size()) * k), (int) (pathVisualizer.getWidth() / Math.sqrt(vertices.size()))-1, (int) (pathVisualizer.getHeight() / Math.sqrt(vertices.size()))-1);
                }
                if (graph.getY(vertices.get(array[h][k]))) {
                    g.setColor(Color.GREEN);
                    //g.fillOval((int) (1600 / Math.sqrt(vertices.size()) * h), (int) (900 / Math.sqrt(vertices.size()) * k), (int) (1600 / Math.sqrt(vertices.size())), (int) (900 / Math.sqrt(vertices.size())));
                    g.fillRect((int) (pathVisualizer.getWidth() / Math.sqrt(vertices.size()) * h), (int) (pathVisualizer.getHeight() / Math.sqrt(vertices.size()) * k), (int) (pathVisualizer.getWidth() / Math.sqrt(vertices.size()))-1, (int) (pathVisualizer.getHeight() / Math.sqrt(vertices.size()))-1);
                }
                if (vertices.get(array[h][k]) == vertices.get(startName)) {
                    //System.out.println(graph.getOrder(vertices.get(array[h][k])));
                    g.setColor(Color.BLUE);
                    g.fillRect((int) (pathVisualizer.getWidth() / Math.sqrt(vertices.size()) * h), (int) (pathVisualizer.getHeight() / Math.sqrt(vertices.size()) * k), (int) (pathVisualizer.getWidth() / Math.sqrt(vertices.size()))-1, (int) (pathVisualizer.getHeight() / Math.sqrt(vertices.size()))-1);
                }
                if (vertices.get(array[h][k]) == vertices.get(endName)) {
                    g.setColor(Color.YELLOW);
                    g.fillRect((int) (pathVisualizer.getWidth() / Math.sqrt(vertices.size()) * h), (int) (pathVisualizer.getHeight() / Math.sqrt(vertices.size()) * k), (int) (pathVisualizer.getWidth() / Math.sqrt(vertices.size()))-1, (int) (pathVisualizer.getHeight() / Math.sqrt(vertices.size()))-1);
                }
                for (int a = 0; a < this.path2.size(); a++) {
                    for (int b = a; b < this.path2.size(); b++) {
                        g.setColor(Color.GREEN);
                        //System.out.println(path2.size());
                        //System.out.println(graph.getOrder(path2.get(0)));
                        // if (path2.size() > 2) {
                            //System.out.println(graph.getOrder(path2.get(2)));
                        //}
                        //System.out.println(graph.getOrder(path2.get(a)) + " " + );

                        if (graph.getOrder(this.path2.get(a)) > -1 && graph.getOrder(this.path2.get(b)) > -1 && graph.getY(this.path2.get(a)) && graph.getY(this.path2.get(b))) {
                            if (graph.getOrder(this.path2.get(a)) == graph.getOrder(this.path2.get(b)) + 1) {
                                g.drawLine((int) (pathVisualizer.getWidth() / Math.sqrt(vertices.size()) * graph.getI(this.path2.get(a))),
                                        (int) (pathVisualizer.getHeight() / Math.sqrt(vertices.size()) * graph.getJ(this.path2.get(a))),
                                        (int) (pathVisualizer.getWidth() / Math.sqrt(vertices.size()) * graph.getI(this.path2.get(b))),
                                        (int) (pathVisualizer.getHeight() / Math.sqrt(vertices.size()) * graph.getJ(this.path2.get(b))));
                            }
                        }
                    }
                }
            }
        }
    }
}
