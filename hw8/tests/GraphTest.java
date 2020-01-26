package hw8.tests;

import hw8.exceptions.InsertionException;
import hw8.exceptions.PositionException;
import hw8.exceptions.RemovalException;
import hw8.Edge;
import hw8.Graph;
import hw8.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Graph Test Suite.
 */
public abstract class GraphTest {

    /**
     * Graph being tested.
     */
    protected Graph<String, String> graph;

    /**
     * Creates the specific graph to be tested.
     * @return the graph
     */
    protected abstract Graph<String, String> createGraph();

    /**
     * Creates a new graph before every test method.
     */
    @Before
    public void setupGraph() {
        this.graph = createGraph();
    }

    /**
     * Tests inserting one vertex.
     */
    @Test
    public void insertVertex() {
        Vertex<String> v1 = graph.insert("a");

        assertEquals("a", v1.get());
    }

    /**
     * Tests inserting multiple vertices.
     */
    @Test
    public void insertMultipleVerticies() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");
        Vertex<String> v3 = graph.insert("c");

        assertEquals("a", v1.get());
        assertEquals("b", v2.get());
        assertEquals("c", v3.get());
    }

    /**
     * Tests inserting one edge.
     */
    @Test
    public void insertEdge() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");
        Edge<String> e1 = graph.insert(v1, v2, "c");

        assertEquals("c", e1.get());
    }

    /**
     * Tests inserting multiples edges.
     */
    @Test
    public void insertMultipleEdges() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");
        Vertex<String> v3 = graph.insert("c");

        Edge<String> e1 = graph.insert(v1, v2, "d");
        Edge<String> e2 = graph.insert(v1, v3, "e");

        assertEquals("d", e1.get());
        assertEquals("e", e2.get());
    }

    /**
     * Tests inserting self loop.
     *
     * @expected InsertionException if self loop found
     */
    @Test(expected = InsertionException.class)
    public void insertSelfLoop() {
        Vertex<String> v1 = graph.insert("a");

        graph.insert(v1, v1, "b");
    }

    /**
     * Tests inserting duplicate edge.
     *
     * @expected InsertionException if duplicate found.
     */
    @Test(expected = InsertionException.class)
    public void insertDuplicateEdge() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");

        graph.insert(v1, v2, "c");
        graph.insert(v1, v2, "d");
    }

    /**
     * Tests inserting vertex with invalid position.
     *
     * @expected PositionException if invalid vertex found
     */
    @Test(expected = PositionException.class)
    public void insertInvalidVertexPosition() {
        Graph<String, String> graph2 = createGraph();

        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph2.insert("b");

        graph.insert(v1, v2, "c");
    }

    /**
     * Tests removing one vertex from graph.
     */
    @Test
    public void removeVertex() {
        Vertex<String> v1 = graph.insert("a");
        int count = 0;
        for (Vertex<String> v : graph.vertices()) {
            count++;
        }
        assertEquals(1, count);

        graph.remove(v1);
        int count2 = 0;
        for (Vertex<String> v : graph.vertices()) {
            count2++;
        }
        assertEquals(0, count2);
    }

    /**
     * Tests removing multiple vertices from graph.
     */
    @Test
    public void removeMultipleVertices() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");
        Vertex<String> v3 = graph.insert("c");
        Vertex<String> v4 = graph.insert("d");
        Vertex<String> v5 = graph.insert("e");
        int count = 0;
        for (Vertex<String> v : graph.vertices()) {
            count++;
        }
        assertEquals(5, count);

        graph.remove(v1);
        graph.remove(v2);
        graph.remove(v3);
        graph.remove(v4);
        graph.remove(v5);
        int count2 = 0;
        for (Vertex<String> v : graph.vertices()) {
            count2++;
        }
        assertEquals(0, count2);
    }

    /**
     * Tests removing vertex with invalid position.
     *
     * @expected PositionException if invalid vertex found.
     */
    @Test(expected = PositionException.class)
    public void removeInvalidVertexPosition() {
        Graph<String, String> graph2 = createGraph();
        Vertex<String> v1 = graph2.insert("a");

        graph.remove(v1);
    }

    /**
     * Tests removing vertex with incident edges.
     *
     * @expected RemovalException if vertex has incident edges
     */
    @Test(expected = RemovalException.class)
    public void removeVertexWithIncidentEdges() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");
        Edge<String> e1 = graph.insert(v1, v2, "c");

        graph.remove(v1);
    }

    /**
     * Tests removing one edge.
     */
    @Test
    public void removeEdge() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");

        Edge<String> e1 = graph.insert(v1, v2, "c");
        int count = 0;
        for (Edge<String> e : graph.edges()) {
            count++;
        }
        assertEquals(1, count);

        graph.remove(e1);
        int count2 = 0;
        for (Edge<String> e : graph.edges()) {
            count2++;
        }
        assertEquals(0, count2);
    }

    /**
     * Tests removing multiple edges.
     */
    @Test
    public void removeMultipleEdges() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");
        Vertex<String> v3 = graph.insert("c");
        Vertex<String> v4 = graph.insert("d");
        Vertex<String> v5 = graph.insert("e");

        Edge<String> e1 = graph.insert(v1, v2, "f");
        Edge<String> e2 = graph.insert(v2, v1, "g");
        Edge<String> e3 = graph.insert(v1, v3, "h");
        Edge<String> e4 = graph.insert(v3, v1, "i");
        Edge<String> e5 = graph.insert(v4, v5, "j");

        int count = 0;
        for (Edge<String> e : graph.edges()) {
            count++;
        }
        assertEquals(5, count);

        graph.remove(e1);
        graph.remove(e2);
        graph.remove(e3);
        graph.remove(e4);
        graph.remove(e5);
        int count2 = 0;
        for (Edge<String> v : graph.edges()) {
            count2++;
        }
        assertEquals(0, count2);
    }

    /**
     * Tests removing an edge with invalid position.
     *
     * @expected PositionException is invalid edge is found
     */
    @Test(expected = PositionException.class)
    public void removeInvalidEdgePosition() {
        Graph<String, String> graph2 = createGraph();

        Vertex<String> v1 = graph2.insert("a");
        Vertex<String> v2 = graph2.insert("b");
        Edge<String> e1 = graph2.insert(v1, v2, "c");

        graph.remove(e1);
    }

    /**
     * Tests iterating over vertices.
     */
    @Test
    public void iterateOverVertices() {
        Vertex<String> v1 = graph.insert("1");
        Vertex<String> v2 = graph.insert("2");
        Vertex<String> v3 = graph.insert("3");
        Vertex<String> v4 = graph.insert("4");
        Vertex<String> v5 = graph.insert("5");

        Edge<String> e1 = graph.insert(v1, v2, "6");
        Edge<String> e2 = graph.insert(v2, v1, "7");
        Edge<String> e3 = graph.insert(v1, v3, "8");
        Edge<String> e4 = graph.insert(v3, v1, "9");
        Edge<String> e5 = graph.insert(v4, v5, "10");

        ArrayList<String> test = new ArrayList<String>();
        for (Vertex<String> v : graph.vertices()) {
            test.add(v.get());
        }
        Collections.sort(test);
        int count = 1;
        for (String i : test) {
            assertEquals(Integer.toString(count), i);
            count++;
        }
    }

    /**
     * Tests iterating over edges.
     */
    @Test
    public void iterateOverEdges() {
        Vertex<String> v1 = graph.insert("1");
        Vertex<String> v2 = graph.insert("2");
        Vertex<String> v3 = graph.insert("3");
        Vertex<String> v4 = graph.insert("4");
        Vertex<String> v5 = graph.insert("5");

        Edge<String> e1 = graph.insert(v1, v2, "11");
        Edge<String> e2 = graph.insert(v2, v1, "12");
        Edge<String> e3 = graph.insert(v1, v3, "13");
        Edge<String> e4 = graph.insert(v3, v1, "14");
        Edge<String> e5 = graph.insert(v4, v5, "15");

        ArrayList<String> test = new ArrayList<String>();
        for (Edge<String> e : graph.edges()) {
            test.add(e.get());
        }
        Collections.sort(test);
        int count = 11;
        for (String i : test) {
            assertEquals(Integer.toString(count), i);
            count++;
        }
    }

    /**
     * Tests iterating over outgoing incident edges.
     */
    @Test
    public void iterateOverOutgoingEdges() {
        Vertex<String> v1 = graph.insert("1");
        Vertex<String> v2 = graph.insert("2");
        Vertex<String> v3 = graph.insert("3");
        Vertex<String> v4 = graph.insert("4");
        Vertex<String> v5 = graph.insert("5");

        Edge<String> e1 = graph.insert(v1, v2, "11");
        Edge<String> e2 = graph.insert(v2, v1, "12");
        Edge<String> e3 = graph.insert(v1, v3, "13");
        Edge<String> e4 = graph.insert(v3, v1, "14");
        Edge<String> e5 = graph.insert(v4, v5, "15");
        Edge<String> e6 = graph.insert(v1, v5, "16");

        ArrayList<String> test = new ArrayList<String>();
        for (Edge<String> e : graph.outgoing(v1)) {
            test.add(e.get());
        }
        Collections.sort(test);
        StringBuilder testString = new StringBuilder();
        for (String i : test) {
            testString.append(i);
        }

        assertEquals("111316", testString.toString());
    }

    /**
     * Tests iterating over outgoing incident edges of invalid vertex.
     */
    @Test(expected = PositionException.class)
    public void iterateOverOutgoingEdgesInvalidVertex() {
        Graph<String, String> graph2 = createGraph();
        Vertex<String> v1 = graph2.insert("a");

        for (Edge<String> e : graph.outgoing(v1)) {
            break;
        }
    }

    /**
     * Tests iterating over incoming incident edges.
     */
    @Test
    public void iterateOverIncomingEdges() {
        Vertex<String> v1 = graph.insert("1");
        Vertex<String> v2 = graph.insert("2");
        Vertex<String> v3 = graph.insert("3");
        Vertex<String> v4 = graph.insert("4");
        Vertex<String> v5 = graph.insert("5");

        Edge<String> e1 = graph.insert(v1, v2, "11");
        Edge<String> e2 = graph.insert(v2, v1, "12");
        Edge<String> e3 = graph.insert(v1, v3, "13");
        Edge<String> e4 = graph.insert(v3, v1, "14");
        Edge<String> e5 = graph.insert(v4, v5, "15");
        Edge<String> e6 = graph.insert(v5, v1, "16");

        ArrayList<String> test = new ArrayList<String>();
        for (Edge<String> e : graph.incoming(v1)) {
            test.add(e.get());
        }
        Collections.sort(test);
        StringBuilder testString = new StringBuilder();
        for (String i : test) {
            testString.append(i);
        }

        assertEquals("121416", testString.toString());
    }

    /**
     * Tests iterating over incoming incident edges of invalid vertex.
     */
    @Test(expected = PositionException.class)
    public void iterateOverIncomingEdgesInvalidVertex() {
        Graph<String, String> graph2 = createGraph();
        Vertex<String> v1 = graph2.insert("a");

        for (Edge<String> e : graph.incoming(v1)) {
            break;
        }
    }

    /**
     * Tests from component of edge.
     */
    @Test
    public void testStartVertexOfEdge() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");
        Edge<String> e1 = graph.insert(v1, v2, "c");

        assertEquals(v1, graph.from(e1));
    }

    /**
     * Tests from component of invalid edge.
     *
     * @expected PositionException if invalid edge is found
     */
    @Test(expected = PositionException.class)
    public void testStartVertexOfEdgeInvalidEdge() {
        Graph<String, String> graph2 = createGraph();
        Vertex<String> v1 = graph2.insert("a");
        Vertex<String> v2 = graph2.insert("b");

        Edge<String> e1 = graph2.insert(v1, v2, "c");
        graph.from(e1);
    }

    /**
     * Tests to component of edge.
     */
    @Test
    public void testEndVertexOfEdge() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");
        Edge<String> e1 = graph.insert(v1, v2, "c");

        assertEquals(v2, graph.to(e1));
    }

    /**
     * Test to component of invalid edge.
     *
     * @expected PositionException if invalid edge is found
     */
    @Test(expected = PositionException.class)
    public void testEndVertexOfEdgeInvalidEdge() {
        Graph<String, String> graph2 = createGraph();
        Vertex<String> v1 = graph2.insert("a");
        Vertex<String> v2 = graph2.insert("b");

        Edge<String> e1 = graph2.insert(v1, v2, "c");
        graph.to(e1);
    }

    /**
     * Tests labeling a vertex.
     */
    @Test
    public void labelVertex() {
        Vertex<String> v1 = graph.insert("a");
        graph.label(v1, "v1");

        assertEquals("v1", graph.label(v1));
    }

    /**
     * Tests labeling an invalid vertex.
     *
     * @expected PositionException is invalid vertex is found.
     */
    @Test(expected = PositionException.class)
    public void labelVertexInvalidPosition() {
        Graph<String, String> graph2 = createGraph();
        Vertex<String> v1 = graph2.insert("a");

        graph.label(v1, "v1");
    }

    /**
     * Tests labeling an edge.
     */
    @Test
    public void labelEdge() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");
        Edge<String> e1 = graph.insert(v1, v2, "c");
        graph.label(e1, "e1");

        assertEquals("e1", graph.label(e1));
    }

    /**
     * Tests labeling an invalid edge.
     *
     * @expected PositionException if invalid edge is found.
     */
    @Test(expected = PositionException.class)
    public void labelEdgeInvalidPosition() {
        Graph<String, String> graph2 = createGraph();
        Vertex<String> v1 = graph2.insert("a");
        Vertex<String> v2 = graph2.insert("b");
        Edge<String> e1 = graph2.insert(v1, v2, "c");

        graph.label(e1, "e1");
    }

    /**
     * Tests getter for labeled vertex.
     */
    @Test
    public void getVertexLabel() {
        Vertex<String> v1 = graph.insert("a");
        graph.label(v1, "v1");

        String label = (String) graph.label(v1);
        assertEquals("v1", label);
    }

    /**
     * Tests getter for labeled invalid vertex.
     *
     * @expected PositionException if invalid vertex is found.
     */
    @Test(expected = PositionException.class)
    public void getVertexLabelInvalidPosition() {
        Graph<String, String> graph2 = createGraph();
        Vertex<String> v1 = graph2.insert("a");
        graph2.label(v1, "v1");

        graph.label(v1);
    }

    /**
     * Tests getter for labeled edge.
     */
    @Test
    public void getEdgeLabel() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");
        Edge<String> e1 = graph.insert(v1, v2, "c");
        graph.label(e1, "e1");

        String label = (String) graph.label(e1);
        assertEquals("e1", label);
    }

    /**
     * Test getter for labeled invalid edge.
     *
     * @expected PositionException is invalid edge is found
     */
    @Test(expected = PositionException.class)
    public void getEdgeLabelInvalidPosition() {
        Graph<String, String> graph2 = createGraph();
        Vertex<String> v1 = graph2.insert("a");
        Vertex<String> v2 = graph2.insert("b");
        Edge<String> e1 = graph2.insert(v1, v2, "c");
        graph2.label(e1, "e1");

        graph.label(e1);
    }

    /**
     * Tests getter for null-labeled edge.
     */
    @Test
    public void getNullEdgeLabel() {
        Vertex<String> v1 = graph.insert("a");

        String label = (String) graph.label(v1);
        assertNull(label);
    }

    /**
     * Tests getter for null-labeled vertex.
     */
    @Test
    public void getNullVertexLabel() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");
        Edge<String> e1 = graph.insert(v1, v2, "c");

        String label = (String) graph.label(e1);
        assertNull(label);
    }

    /**
     * Tests clearing all labels.
     */
    @Test
    public void testClearAllLabels() {
        Vertex<String> v1 = graph.insert("a");
        Vertex<String> v2 = graph.insert("b");
        Edge<String> e1 = graph.insert(v1, v2, "c");

        graph.label(v1, "v1");
        graph.label(v2, "v2");
        graph.label(e1, "e1");

        assertEquals("v1", graph.label(v1));
        assertEquals("v2", graph.label(v2));
        assertEquals("e1", graph.label(e1));

        graph.clearLabels();

        assertNull(graph.label(v1));
        assertNull(graph.label(v2));
        assertNull(graph.label(e1));
    }
}
