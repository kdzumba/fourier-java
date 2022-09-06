package fourier.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImageGraph
{
    private List<Node> nodes = new ArrayList<>();

    public ImageGraph(List<Coordinate> points)
    {
        //Create a node for each image point
        for(int index = 0; index < points.size(); index++)
        {
            var node = new Node();
            node.setVisited(false);
            node.setCoordinate(points.get(index));

            if(index == 0)
                node.setPriority(Integer.MAX_VALUE);
            nodes.add(node);
        }

        //set the neighbour nodes for each node. This is the slowest bit
        //when drawing from an image
        nodes.forEach(node -> {
            for(Node potentialNeighbour : nodes)
            {
                if(node.getCoordinate().getDistanceTo(potentialNeighbour.getCoordinate()) <= Math.sqrt(2))
                {
                    node.getNeighbours().add(potentialNeighbour);
                }
            }
        });
    }
}
