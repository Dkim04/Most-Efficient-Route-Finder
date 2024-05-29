package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class Graph<T> {
  private Map<T, List<T>> adjacencyMap;

  public Graph() {
    this.adjacencyMap = new HashMap<>();
  }

  public void addVertex(T node) {
    adjacencyMap.putIfAbsent(node, new LinkedList<>());
  }

  public void addEdge(T node1, T node2) {
    addVertex(node1);
    addVertex(node2);
    adjacencyMap.get(node1).add(node2);
  }

  public List<T> findShortestPath(T start, T end) {
    // use BFS to find the shortest path from the start to the end while keeping a seperate
    // parentmap so that we are able to get the path we took to get to the destination country

    if (!adjacencyMap.containsKey(start)) {
      return null; // Start node not present in the graph
    }

    Set<T> visited = new HashSet<>();
    Map<T, T> parentMap = new HashMap<>();
    Queue<T> queue = new LinkedList<>();
    queue.add(start);
    visited.add(start);
    parentMap.put(start, null);

    while (!queue.isEmpty()) {
      T current = queue.poll();
      for (T neighbor : adjacencyMap.get(current)) {
        if (!visited.contains(neighbor) && !neighbor.equals(end)) {
          visited.add(neighbor);
          queue.add(neighbor);
          parentMap.put(neighbor, current);
        } else if (neighbor.equals(end)) {
          List<T> route = new ArrayList<>();
          route.add(neighbor);
          T node = current;
          while (node != null) {
            route.add(node);
            node = parentMap.get(node);
          }
          Collections.reverse(route);
          return route;
        }
      }
    }
    return null;
  }
}
