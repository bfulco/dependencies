import java.util.*;

public class DependencyGraph {
    HashMap<String, Set<String>> graph = new HashMap<>();

    public void addDirect(String node, List<String> dependencies) {
        graph.put(node, new HashSet<>(dependencies));
    }

    public List<String> dependenciesFor(String node) {
        List<String> dependencies = new ArrayList<String>();
        dependencies.addAll(dependenciesForHelper(node));
        return dependencies;
    }

    private Set<String> dependenciesForHelper(String node) {
        if(!graph.containsKey(node)) {
            return Collections.emptySet();
        }

        Set<String> directDep = graph.get(node);
        Set<String> fullDep = new HashSet<>(directDep);

        for(String d : directDep) {
            if(fullDep.contains(d)) {
                continue;
            }
            Set<String> s = dependenciesForHelper(d);
            fullDep.addAll(s);
        }

        return fullDep;
    }
}
