package fourier.models;

import fourier.rendering.Renderer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Data
public class Epicycle
{
    private Coordinate center;
    private Coordinate terminal;
    private Phasor component;

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

    public static List<Epicycle> generateEpicycles(List<Phasor> signal)
    {
        var epicycles = new ArrayList<Epicycle>();

        for(int n = 0; n < signal.size(); n++)
        {
            var epicycle = new Epicycle();
            epicycle.setComponent(signal.get(n));

            if(n == 0)
            {
                epicycle.setCenter(new Coordinate(0, 0));
                var terminalX = epicycle.getComponent().getTerminal().getX();
                var terminalY =  epicycle.getComponent().getTerminal().getY();
                epicycle.setTerminal(new Coordinate(terminalX , terminalY));
            }
            else
            {
                var previous = epicycles.get(n - 1);
                epicycle.setCenter(previous.getTerminal());
                var terminalX = epicycle.getCenter().getX() + epicycle.getComponent().getTerminal().getX();
                var terminalY = epicycle.getCenter().getY() + epicycle.getComponent().getTerminal().getY();
                epicycle.setTerminal(new Coordinate(terminalX, terminalY));
            }
            epicycles.add(n, epicycle);
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

            //The terminal point of the last epicycle in our epicycles list is the one that draws our image
            if(n == epicycles.size() - 1)
            {
                var drawingPoint = new Coordinate(epicycles.get(n).getTerminal().getX(), epicycles.get(n).getTerminal().getY());
                Renderer.drawing.add(0, drawingPoint);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Epicycle epicycle = (Epicycle) o;

        if (!Objects.equals(center, epicycle.center)) return false;
        if (!Objects.equals(terminal, epicycle.terminal)) return false;
        return Objects.equals(component, epicycle.component);
    }

    @Override
    public int hashCode() {
        int result = center != null ? center.hashCode() : 0;
        result = 31 * result + (terminal != null ? terminal.hashCode() : 0);
        result = 31 * result + (component != null ? component.hashCode() : 0);
        return result;
    }
}
