package fourier.models;

import fourier.rendering.Renderer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Epicycle
{
    private Coordinate center = new Coordinate();
    private Coordinate terminal = new Coordinate();
    private ComplexNumber component = new ComplexNumber();

    public double getPhase()
    {
        return component.getPhase();
    }

    public double getRadius()
    {
        return component.getMagnitude();
    }

    public double getFrequency()
    {
        return component.getFrequency();
    }

    public static List<Epicycle> generateEpicycles(List<ComplexNumber> signal)
    {
        var N = signal.size();
        var epicycles = new ArrayList<Epicycle>();

        for(int n = 0; n < N; n++)
        {
            var epicycle = new Epicycle();
            epicycle.setComponent(signal.get(n));

            if(n == 0)
            {
                epicycle.setCenter(new Coordinate(0, 0));
                var terminalX = epicycle.getComponent().getReal();
                var terminalY =  epicycle.getComponent().getImaginary();
                epicycle.setTerminal(new Coordinate(terminalX , terminalY));
            }
            else
            {
                var previous = epicycles.get(n - 1);
                epicycle.setCenter(previous.getTerminal());
                var terminalX = epicycle.getCenter().getX() + epicycle.getComponent().getReal();
                var terminalY = epicycle.getCenter().getY() + epicycle.getComponent().getImaginary();
                epicycle.setTerminal(new Coordinate(terminalX, terminalY));
            }
            epicycles.add(epicycle);
        }
        return epicycles;
    }

    public static void updateEpicycles(List<Epicycle> epicycles, double animationTimer)
    {
        var N = epicycles.size();
        for(int n = 0; n < N; n++)
        {
            var current = epicycles.get(n);
            if(n != 0)
            {
                var previous = epicycles.get(n - 1);
                current.setCenter(previous.getTerminal());
            }

            var phase = current.getPhase();
            var freq = current.getFrequency();
            var radius = current.getRadius();

            var terminalX = current.getCenter().getX() + radius * Math.cos(freq * animationTimer + phase);
            var terminalY = current.getCenter().getY() + radius * Math.sin(freq * animationTimer + phase);
            current.setTerminal(new Coordinate(terminalX, terminalY));

            if(n == epicycles.size() - 1)
            {
                var drawingPoint = new Coordinate(epicycles.get(n).getTerminal().getX(), epicycles.get(n).getTerminal().getY() + 15);
                Renderer.drawing.add(0, drawingPoint);
            }
        }
    }
}
