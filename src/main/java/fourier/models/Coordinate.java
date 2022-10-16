package fourier.models;
public class Coordinate
{
    private final double x;
    private final double y;

    public Coordinate(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX() {return x;}
    public double getY() {return y;}

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

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
