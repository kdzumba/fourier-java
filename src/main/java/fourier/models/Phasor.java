package fourier.models;

import java.util.Objects;

public class Phasor
{
    private Coordinate terminal;
    private double frequency;
    public Phasor(Coordinate terminal, double frequency)
    {
        this.terminal = terminal;
        this.frequency = frequency;
    }
    public double getMagnitude()
    {
        return Math.sqrt(Math.pow(terminal.getX(), 2) + Math.pow(terminal.getY(), 2));
    }
    public double getPhase()
    {
        return Math.atan2(terminal.getY(), terminal.getX());
    }

    public Coordinate getTerminal(){return terminal;}
    public void setTerminal(Coordinate terminal){this.terminal = terminal;}
    public double getFrequency(){return frequency;}
    public void setFrequency(double frequency) {this.frequency = frequency;}


    public Phasor multiply(Phasor other)
    {
        var thisX = terminal.getX();
        var thisY = terminal.getY();
        var otherX = other.terminal.getX();
        var otherY = other.terminal.getY();

        var real = thisX * otherX - thisY * otherY;
        var imaginary = thisX * otherY + thisY * otherX;
        return new Phasor(new Coordinate(real, imaginary), frequency);
    }

    public Phasor add(Phasor other)
    {
        var real = terminal.getX() + other.terminal.getX();
        var imaginary = terminal.getY() + other.terminal.getY();
        return new Phasor(new Coordinate(real, imaginary), frequency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phasor phasor = (Phasor) o;

        if (Double.compare(phasor.frequency, frequency) != 0) return false;
        return Objects.equals(terminal, phasor.terminal);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = terminal != null ? terminal.hashCode() : 0;
        temp = Double.doubleToLongBits(frequency);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
