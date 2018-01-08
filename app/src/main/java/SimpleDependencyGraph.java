import java.util.*;

public class SimpleDependencyGraph implements DependencyGraph {
    HashMap<String, Set<String>> graph = new HashMap<>();

    @Override
    public void addDirect(String node, List<String> dependencies) {
        graph.put(node, new HashSet<>(dependencies));
    }

    @Override
    public List<String> dependenciesFor(String node) {
        List<String> dependencies = new ArrayList<String>();
        dependencies.addAll(dependenciesForHelper(node, new HashSet<>()));
        return dependencies;
    }

    private Set<String> dependenciesForHelper(String node, Set<String> fullDep) {
        if (!graph.containsKey(node)) {
            return Collections.emptySet();
        }

        Set<String> directDep = graph.get(node);

        for (String d : directDep) {
            if (fullDep.add(d)) {
                Set<String> s = dependenciesForHelper(d, fullDep);
                fullDep.addAll(s);
            }
        }

        return fullDep;
    }
}
