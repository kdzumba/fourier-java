package fourier.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@NoArgsConstructor
public class Coordinate {
    private double x;
    private double y;
    private Color pixelColor;

    public Coordinate(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Coordinate(double x, double y, Color c)
    {
        this.x = x;
        this.y = y;
        this.pixelColor = c;
    }

    public Coordinate rotate(double angle)
    {
        this.x = this.x * Math.cos(angle) - this.y * Math.sin(angle);
        this.y = this.x * Math.sin(angle) + this.y * Math.cos(angle);
        return this;
    }

    public Coordinate translate(double xTranslation, double yTranslation)
    {
        this.x = this.x + xTranslation;
        this.y = this.y + yTranslation;
        return this;
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
