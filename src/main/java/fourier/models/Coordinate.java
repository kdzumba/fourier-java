package fourier.models;

import lombok.Data;

@Data
public class Coordinate
{
    private double x;
    private double y;

    public Coordinate(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getDistanceTo(Coordinate p2)
    {
        return Math.sqrt(Math.pow(p2.x - x, 2) + Math.pow(p2.y - y, 2));
    }
    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof  Coordinate))
            return false;
        return ((Coordinate)o).x == this.x && ((Coordinate)o).y == this.y;
    }
}
