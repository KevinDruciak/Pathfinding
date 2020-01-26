package hw8;

import hw8.exceptions.InsertionException;
import hw8.exceptions.PositionException;
import hw8.exceptions.RemovalException;

import java.util.ArrayList;
import java.util.List;

/**
    An implementation of a directed graph using incidence lists
    for sparse graphs where most things aren't connected.
    @param <V> Vertex element type.
    @param <E> Edge element type.
*/
public class SparseGraph<V, E> implements Graph<V, E> {

    /**
     * Class for a vertex of type V.
     *
     * @param <V> the type of the vertex
     */
    private final class VertexNode<V> implements Vertex<V> {
        V data;
        Graph<V, E> owner;
        List<Edge<E>> outgoing;
        List<Edge<E>> incoming;
        Object label;
        double distance;
        boolean explored;
        boolean y;
        int order = -1;
        int a;
        int b;

        /**
         * Constructor for a new vertex.
         *
         * @param v vertex value
         */
        VertexNode(V v) {
            this.data = v;
            this.outgoing = new ArrayList<>();
            this.incoming = new ArrayList<>();
            this.label = null;
            this.distance = Double.POSITIVE_INFINITY;
            this.explored = false;
            this.y = false;
            this.a = -10;
            this.b = -10;
        }

        @Override
        public V get() {
            return this.data;
        }

        @Override
        public void put(V v) {
            this.data = v;
        }
    }

    /**
     * Class for an edge of type E.
     *
     * @param <E> the type of the edge
     */
    private final class EdgeNode<E> implements Edge<E> {
        E data;
        Graph<V, E> owner;
        VertexNode<V> from;
        VertexNode<V> to;
        Object label;

        /**
         * Constructor for a new edge.
         *
         * @param f from vertex
         * @param t to vertex
         * @param e edge value
         */
        EdgeNode(VertexNode<V> f, VertexNode<V> t, E e) {
            this.from = f;
            this.to = t;
            this.data = e;
            this.label = null;
        }

        @Override
        public E get() {
            return this.data;
        }

        @Override
        public void put(E e) {
            this.data = e;
        }
    }

    public void setOrder(Vertex<V> v, int order) {
        this.convert(v).order = order;
    }

    public int getI(Vertex<V> v) {
        return this.convert(v).a;
    }

    public int getJ(Vertex<V> v) {
        return this.convert(v).b;
    }

    public void setIJ(Vertex<V> v, int i, int j) {
        this.convert(v).a = i;
        this.convert(v).b = j;
    }

    public int getOrder(Vertex<V> v) {
        return this.convert(v).order;
    }

    /**
     * Vertices of the graph.
     */
    private List<Vertex<V>> vertices;

    /**
     * Edges of the graph.
     */
    private List<Edge<E>> edges;

    /**
     * Constructor for instantiating a graph.
     */
    public SparseGraph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    /**
     * Gets the current distance of vertex.
     *
     * @param v the vertex
     * @return its distance
     */
    public double getDistance(Vertex<V> v) {
        return this.convert(v).distance;
    }

    /**
     * Sets the vertex's new distance.
     *
     * @param v the vertex
     * @param d its new distance
     */
    public void setDistance(Vertex<V> v, double d) {
        this.convert(v).distance = d;
    }

    /**
     * Gets the visited status of vertex.
     *
     * @param v the vertex
     * @return its visited status
     */
    public boolean getExplored(Vertex<V> v) {
        return this.convert(v).explored;
    }

    public boolean getY(Vertex<V> v) {
        return this.convert(v).y;
    }

    public void setY(Vertex<V> v) {
        this.convert(v).y = true;
    }

    /**
     * Sets the visited status of vertex.
     *
     * @param v the vertex
     * @param b the value to set it to
     */
    public void setExplored(Vertex<V> v, boolean b) {
        this.convert(v).explored = b;
    }

    /**
     * Checks if vertex belongs to the graph.
     *
     * @param toTest the vertex to test
     */
    private void checkOwner(VertexNode<V> toTest) {
        if (toTest.owner != this) {
            throw new PositionException();
        }
    }

    /**
     * Checks if edge belongs to the graph.
     *
     * @param toTest the edge to test
     */
    private void checkOwner(EdgeNode<E> toTest) {
        if (toTest.owner != this) {
            throw new PositionException();
        }
    }

    /**
     * Converts the vertex back to a VertexNode to use internally.
     *
     * @param v the vertex to be converted
     * @return the VertexNode
     * @throws PositionException if vertex invalid
     */
    private VertexNode<V> convert(Vertex<V> v) throws PositionException {
        try {
            VertexNode<V> gv = (VertexNode<V>) v;
            this.checkOwner(gv);
            return gv;
        } catch (ClassCastException ex) {
            throw new PositionException();
        }
    }

    /**
     * Converts an edge back to an EdgeNode to use internally.
     *
     * @param e the edge to be converted
     * @return the EdgeNode
     * @throws PositionException if edge invalid
     */
    private EdgeNode<E> convert(Edge<E> e) throws PositionException {
        try {
            EdgeNode<E> ge = (EdgeNode<E>) e;
            this.checkOwner(ge);
            return ge;
        } catch (ClassCastException ex) {
            throw new PositionException();
        }
    }

    @Override
    public Vertex<V> insert(V v) {
        VertexNode<V> vertex = new VertexNode(v);
        vertex.owner = this;
        this.vertices.add((Vertex<V>) vertex);
        return vertex;
    }

    @Override
    public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
            throws PositionException, InsertionException {
        if (from == null || to == null) {
            throw new PositionException();
        }

        VertexNode<V> f = this.convert(from);
        VertexNode<V> t = this.convert(to);

        if (f == t) {
            throw new InsertionException();
        }

        for (Edge<E> i : f.outgoing) {
            if (this.convert(i).to == t) {
                throw new InsertionException();
            }
        }

        EdgeNode<E> edge = new EdgeNode(f, t, e);
        f.outgoing.add(edge);
        t.incoming.add(edge);
        edge.owner = this;
        this.edges.add((Edge<E>) edge);
        return edge;
    }

    @Override
    public V remove(Vertex<V> v) throws PositionException,
            RemovalException {
        if (v == null) {
            throw new PositionException();
        }

        VertexNode<V> vertex = this.convert(v);
        if (vertex.outgoing.size() == 0 && vertex.incoming.size() == 0) {
            this.vertices.remove(v);
            vertex.owner = null;
        } else {
            throw new RemovalException();
        }
        return vertex.data;
    }

    @Override
    public E remove(Edge<E> e) throws PositionException {
        if (e == null) {
            throw new PositionException();
        }

        EdgeNode<E> edge = this.convert(e);
        edge.from.outgoing.remove(e);
        edge.to.incoming.remove(e);
        edge.owner = null;
        this.edges.remove(edge);
        return edge.data;
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return this.vertices;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        return this.edges;
    }

    @Override
    public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
        if (v == null) {
            throw new PositionException();
        }
        VertexNode<V> vertex = this.convert(v);
        return vertex.outgoing;
    }

    @Override
    public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
        if (v == null) {
            throw new PositionException();
        }
        VertexNode<V> vertex = this.convert(v);
        return vertex.incoming;
    }

    @Override
    public Vertex<V> from(Edge<E> e) throws PositionException {
        EdgeNode<E> edge = this.convert(e);
        return edge.from;
    }

    @Override
    public Vertex<V> to(Edge<E> e) throws PositionException {
        EdgeNode<E> edge = this.convert(e);
        return edge.to;
    }

    @Override
    public void label(Vertex<V> v, Object l) throws PositionException {
        VertexNode<V> vertex = this.convert(v);
        vertex.label = l;
    }

    @Override
    public void label(Edge<E> e, Object l) throws PositionException {
        EdgeNode<E> edge = this.convert(e);
        edge.label = l;
    }

    @Override
    public Object label(Vertex<V> v) throws PositionException {
        VertexNode<V> vertex = this.convert(v);
        if (vertex.label != null) {
            return vertex.label;
        }
        return null;
    }

    @Override
    public Object label(Edge<E> e) throws PositionException {
        EdgeNode<E> edge = this.convert(e);
        if (edge.label != null) {
            return edge.label;
        }
        return null;
    }

    @Override
    public void clearLabels() {
        for (Vertex<V> vertex : this.vertices) {
            VertexNode<V> vert = this.convert(vertex);
            vert.label = null;
        }
        for (Edge<E> eEdge : this.edges) {
            EdgeNode<E> edge = this.convert(eEdge);
            edge.label = null;
        }
    }

    private String vertexString(Vertex<V> v) {
        return "\"" + v.get() + "\"";
    }

    private String verticesToString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex<V> v : this.vertices) {
            sb.append("  ").append(vertexString(v)).append("\n");
        }
        return sb.toString();
    }

    private String edgeString(Edge<E> e) {
        return String.format("%s -> %s [label=\"%s\"]",
                this.vertexString(this.from(e)),
                this.vertexString(this.to(e)),
                e.get());
    }

    private String edgesToString() {
        String edgs = "";
        for (Edge<E> e : this.edges) {
            edgs += "    " + this.edgeString(e) + ";\n";
        }
        return edgs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n")
                .append(this.verticesToString())
                .append(this.edgesToString())
                .append("}");
        return sb.toString();
    }
}
