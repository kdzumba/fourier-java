package fourier.algorithms;

import fourier.models.Coordinate;
import fourier.models.ImageGraph;
import fourier.models.Node;

import java.util.*;

public class GraphAlgorithms
{
    public static List<Coordinate> nearestNeighbour(ImageGraph imageGraph)
    {
        var nodes = imageGraph.getNodes();
        List<Coordinate> path = new ArrayList<>();
        path.add(nodes.remove(0).getCoordinate());

        while(!nodes.isEmpty())
        {
            int nearestIndex=findNearestIndex(path.get(path.size() - 1), nodes);
            path.add(nodes.remove(nearestIndex).getCoordinate());
        }
        return path;
    }

    static int findNearestIndex(Coordinate current, List<Node> nodes) {
        double nearestDistSquared=Double.POSITIVE_INFINITY;
        int nearestIndex = -1;
        for (int i=0; i< nodes.size(); i++) {
            Coordinate next = nodes.get(i).getCoordinate();
            var distance = current.getDistanceTo(next);
            if(distance < nearestDistSquared) {
                nearestDistSquared = distance;
                nearestIndex = i;
            }
        }
        return nearestIndex;
    }
}
