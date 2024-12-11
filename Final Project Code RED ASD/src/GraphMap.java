import java.util.*;

public class GraphMap {
    private Map<String, List<String>> adjacencyList;

    public GraphMap() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(String from, String to) {
        adjacencyList.putIfAbsent(from, new ArrayList<>());
        adjacencyList.putIfAbsent(to, new ArrayList<>());
        adjacencyList.get(from).add(to);
        adjacencyList.get(to).add(from); // Assuming undirected graph
    }

    public void bfs(String start) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            System.out.println("Visiting: " + current);
            for (String neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }

    public List<String> getNeighbors(String node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }
}