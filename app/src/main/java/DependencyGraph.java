import java.util.List;

public interface DependencyGraph {
    void addDirect(String node, List<String> dependencies);

    List<String> dependenciesFor(String node);
}
