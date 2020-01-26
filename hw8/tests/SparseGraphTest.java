package hw8.tests;

import hw8.Graph;
import hw8.SparseGraph;

public class SparseGraphTest extends GraphTest {

    @Override
    protected Graph<String, String> createGraph() {
        return new SparseGraph<>();
    }
}
