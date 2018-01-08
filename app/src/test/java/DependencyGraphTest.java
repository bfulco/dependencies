import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public abstract class DependencyGraphTest {

    protected DependencyGraph dependencyGraph;

    public static class SimpleDependencyGraphTest extends DependencyGraphTest{

        @Before
        public void setup() {
            dependencyGraph = new SimpleDependencyGraph();
        }
    }

    @Test
    public void test_basic() {
        dependencyGraph.addDirect("A", Arrays.asList("B", "C"));
        dependencyGraph.addDirect("B", Arrays.asList("C", "E"));
        dependencyGraph.addDirect("C", Arrays.asList("G"));
        dependencyGraph.addDirect("D", Arrays.asList("A", "F"));
        dependencyGraph.addDirect("E", Arrays.asList("F"));
        dependencyGraph.addDirect("F", Arrays.asList("H"));
        dependencyGraph.addDirect("G", Collections.emptyList());
        dependencyGraph.addDirect("H", Collections.emptyList());

        assertEquals(Arrays.asList("B", "C", "E", "F", "G", "H"), dependencyGraph.dependenciesFor("A"));
        assertEquals(Arrays.asList("C", "E", "F", "G", "H"), dependencyGraph.dependenciesFor("B"));
        assertEquals(Arrays.asList("G"), dependencyGraph.dependenciesFor("C"));
        assertEquals(Arrays.asList("A", "B", "C", "E", "F", "G", "H"), dependencyGraph.dependenciesFor("D"));
        assertEquals(Arrays.asList("F", "H"), dependencyGraph.dependenciesFor("E"));
        assertEquals(Arrays.asList("H"), dependencyGraph.dependenciesFor("F"));
    }

    @Test
    public void addEmptyGraph_hasEmtpyDependencies() {
        dependencyGraph.addDirect("A", Collections.emptyList());

        assertEquals(Collections.emptyList(), dependencyGraph.dependenciesFor("A"));
    }

    @Test
    public void addFlatGraph_hasCorrectDependencies() {
        dependencyGraph.addDirect("A", Arrays.asList("B"));
        dependencyGraph.addDirect("C", Arrays.asList("D"));

        assertEquals(Arrays.asList("B"), dependencyGraph.dependenciesFor("A"));
        assertEquals(Arrays.asList("D"), dependencyGraph.dependenciesFor("C"));
    }

    @Test
    public void addSimpleGraph_hasSimpleDependencies() {
        dependencyGraph.addDirect("A", Arrays.asList("B"));
        dependencyGraph.addDirect("B", Arrays.asList("C"));

        assertEquals(Arrays.asList("B", "C"), dependencyGraph.dependenciesFor("A"));
        assertEquals(Arrays.asList("C"), dependencyGraph.dependenciesFor("B"));
    }

    @Test
    public void addCyclicGraph_hasCyclicDependencies() {
        dependencyGraph.addDirect("A", Arrays.asList("B"));
        dependencyGraph.addDirect("B", Arrays.asList("A"));

        assertEquals(Arrays.asList("A", "B"), dependencyGraph.dependenciesFor("A"));
        assertEquals(Arrays.asList("A", "B"), dependencyGraph.dependenciesFor("B"));
    }
}
