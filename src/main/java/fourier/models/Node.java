package fourier.models;

import com.sun.source.doctree.SeeTree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node
{
    private Coordinate coordinate;
    private boolean visited;
    private Node parent;
    private int priority = Integer.MIN_VALUE;
    private Set<Node> neighbours = new HashSet<>();

    @Override
    public boolean equals(Object other)
    {
        if(!(other instanceof Node))
            return false;
        return ((Node) other).getCoordinate().equals(this.coordinate);
    }

    @Override
    public String toString()
    {
        return "(" + coordinate.getX() + " , " + coordinate.getY() + ")";
    }
}
