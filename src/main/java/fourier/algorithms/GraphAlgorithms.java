package fourier.algorithms;

import fourier.models.Coordinate;
import fourier.models.ImageGraph;
import fourier.models.Node;

import java.util.*;

public class GraphAlgorithms
{
    public static List<Coordinate> nearestNeighbour(ImageGraph imageGraph)
    {
        Queue<Node> toBeVisited = new PriorityQueue<>(Comparator.comparingInt(Node::getPriority));
        List<Coordinate> visited = new ArrayList<>();

        var nodes = imageGraph.getNodes();
        toBeVisited.addAll(nodes);

        while(!toBeVisited.isEmpty())
        {
            var current = toBeVisited.remove();
            current.setVisited(true);
            visited.add(current.getCoordinate());

            Random random = new Random();
            int nextIndex = random.nextInt(current.getNeighbours().size());
            var next = current.getNeighbours().get(nextIndex);
            next.setPriority(Integer.MAX_VALUE);
            next.setParent(current);
        }
        return visited;
    }
}
