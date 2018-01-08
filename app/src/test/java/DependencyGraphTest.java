import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class DependencyGraphTest {

    @Test
    public void test_basic() {
        DependencyGraph graph = new DependencyGraph();
        graph.addDirect("A", Arrays.asList("B", "C"));
        graph.addDirect("B", Arrays.asList("C", "E"));
        graph.addDirect("C", Arrays.asList("G"));
        graph.addDirect("D", Arrays.asList("A", "F"));
        graph.addDirect("E", Arrays.asList("F"));
        graph.addDirect("F", Arrays.asList("H"));
        graph.addDirect("G", Collections.emptyList());
        graph.addDirect("H", Collections.emptyList());

        assertEquals(Arrays.asList("B", "C", "E", "F", "G", "H"), graph.dependenciesFor("A"));
        assertEquals(Arrays.asList("C", "E", "F", "G", "H"), graph.dependenciesFor("B"));
        assertEquals(Arrays.asList("G"), graph.dependenciesFor("C"));
        assertEquals(Arrays.asList("A", "B", "C", "E", "F", "G", "H"), graph.dependenciesFor("D"));
        assertEquals(Arrays.asList("F", "H"), graph.dependenciesFor("E"));
        assertEquals(Arrays.asList("H"), graph.dependenciesFor("F"));
    }

    @Test
    public void addEmptyGraph_hasEmtpyDependencies() {
        DependencyGraph graph = new DependencyGraph();
        graph.addDirect("A", Collections.emptyList());

        assertEquals(Collections.emptyList(), graph.dependenciesFor("A"));
    }

    @Test
    public void addFlatGraph_hasCorrectDependencies() {
        DependencyGraph graph = new DependencyGraph();
        graph.addDirect("A", Arrays.asList("B"));
        graph.addDirect("C", Arrays.asList("D"));

        assertEquals(Arrays.asList("B"), graph.dependenciesFor("A"));
        assertEquals(Arrays.asList("D"), graph.dependenciesFor("C"));
    }

    @Test
    public void addSimpleGraph_hasSimpleDependencies() {
        DependencyGraph graph = new DependencyGraph();
        graph.addDirect("A", Arrays.asList("B"));
        graph.addDirect("B", Arrays.asList("C"));

        assertEquals(Arrays.asList("B", "C"), graph.dependenciesFor("A"));
        assertEquals(Arrays.asList("C"), graph.dependenciesFor("B"));
    }

    @Test
    public void addCyclicGraph_hasCyclicDependencies() {
        DependencyGraph graph = new DependencyGraph();
        graph.addDirect("A", Arrays.asList("B"));
        graph.addDirect("B", Arrays.asList("A"));

        assertEquals(Arrays.asList("B"), graph.dependenciesFor("A"));
        assertEquals(Arrays.asList("A"), graph.dependenciesFor("B"));
    }
}
