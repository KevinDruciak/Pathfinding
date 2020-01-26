package hw8;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class PathVisualizer extends Canvas implements Runnable {

    private int width;
    private int height;
    private double ratio;
    private int a = 0;

    private Thread thread;
    private boolean running = false;
    private Graphics g;
    private StreetSearcher streetSearcher;
    private Visualizer visualizer;
    String fileName;
    String startName;
    String endName;

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setA() {
        this.a = 1;
    }
    public PathVisualizer() {
        setResolution(1600, 900);
        new Window(this.width, this.height, "Pathfinding Visualization", this);
        this.requestFocus();
        loadGraph();
        visualizer = new Visualizer(streetSearcher.getVertices(), streetSearcher.getGraph(), startName, endName, this, g);
    }

    private void loadGraph() {
        fileName = "C:\\Users\\bakad\\Desktop\\cs226\\homework\\hw8\\data\\baltimore.streets.txt";
        startName = "-76.6250,39.3061";
        endName = "-76.6417,39.3028";
        //fileName = "C:\\Users\\bakad\\Desktop\\cs226\\homework\\hw8\\data\\campus.paths.txt";
        //startName = "-76.617195,39.327692";
        //endName = "-76.616769,39.329399";
        //fileName = "C:\\Users\\bakad\\Desktop\\cs226\\homework\\hw8\\data\\test1.txt";
        //startName = "98";
        //endName = "85";
        streetSearcher = new StreetSearcher(fileName, startName, endName);
    }

    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;
        ratio = (double)width / (double)height;
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
       try {
           thread.join();
           running = false;
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
            double amountOfTicks = 300.0;
            double ns = 1000000000 / amountOfTicks;
            double delta = 0;
            long timer = System.currentTimeMillis();
            int frames = 0;
            while (running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                while (delta >= 1) {
                    tick();
                    delta--;
                }
                if (running)
                    render();
                frames++;
                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    //System.out.println("FPS: " + frames);
                    frames = 0;
                }
        }
        stop();
    }

    private void tick() {

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, this.width, this.height);


        if (a == 1) {
            visualizer.render(g);
        }
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new PathVisualizer();
    }
}
