package fourier.algorithms;

import fourier.models.Coordinate;

import java.util.*;

public class GraphAlgorithms
{
    public static List<Coordinate> nearestNeighbour(List<Coordinate> coordinates)
    {
        List<Coordinate> path = new ArrayList<>();
        var random = new Random();
        var startIndex = random.nextInt(coordinates.size());
        path.add(coordinates.remove(startIndex));

        while(!coordinates.isEmpty())
        {
            int nearestIndex=findNearestIndex(path.get(path.size() - 1), coordinates);
            path.add(coordinates.remove(nearestIndex));
        }
        return path;
    }
    static int findNearestIndex(Coordinate current, List<Coordinate> coordinates) {
        double nearestDistance = Double.POSITIVE_INFINITY;
        int nearestIndex = -1;

        for (int i = 0; i < coordinates.size(); i++)
        {
            Coordinate next = coordinates.get(i);
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
