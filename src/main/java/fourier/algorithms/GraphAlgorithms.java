package fourier.algorithms;

import fourier.models.Coordinate;
import fourier.models.Node;

import java.util.*;

public class GraphAlgorithms
{
    public static List<Coordinate> nearestNeighbour(List<Node> nodes)
    {
        List<Coordinate> path = new ArrayList<>();
        var random = new Random();
        var startIndex = random.nextInt(nodes.size());
        path.add(nodes.remove(startIndex).getCoordinate());

        while(!nodes.isEmpty())
        {
            int nearestIndex=findNearestIndex(path.get(path.size() - 1), nodes);
            path.add(nodes.remove(nearestIndex).getCoordinate());
        }
        return path;
    }

    static int findNearestIndex(Coordinate current, List<Node> nodes) {
        double nearestDistance = Double.POSITIVE_INFINITY;
        int nearestIndex = -1;

        for (int i = 0; i < nodes.size(); i++)
        {
            Coordinate next = nodes.get(i).getCoordinate();
            var distance = current.getDistanceTo(next);
            if(distance < nearestDistance)
            {
                nearestDistance = distance;
                nearestIndex = i;
            }
        }
        return nearestIndex;
    }
}
