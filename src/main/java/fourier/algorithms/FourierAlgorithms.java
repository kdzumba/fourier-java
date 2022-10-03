package fourier.algorithms;

import fourier.models.Coordinate;
import fourier.models.Phasor;

import java.util.Arrays;
import java.util.List;

public class FourierAlgorithms
{

    public static Phasor[] convertToPhasor(List<Coordinate> cartesian)
    {
        var interval = 4;
        var phasors = new Phasor[cartesian.size() / interval];

        var omission = 0;
        if(cartesian.size() % interval != 0)
            omission = interval;

        for(int n = 0; n < cartesian.size() - omission; n += interval)
        {
            var real = cartesian.get(n).getX();
            var imaginary = cartesian.get(n).getY();
            var complex = new Phasor(new Coordinate(real, imaginary), 0);
            phasors[n / interval] = complex;
        }
        return phasors;
    }
    public static Phasor[] discreteFourierTransform(List<Coordinate> imageCoordinates)
    {
        var phasorSignal = convertToPhasor(imageCoordinates);
        var transformedSignal = new Phasor[phasorSignal.length];
        var N = phasorSignal.length;

        for(int k = 0; k < N; k++)
        {
            var sum = new Phasor(new Coordinate(0, 0), k);
            for(int n = 0; n < N; n++)
            {
                var theta = (2 * Math.PI * k * n) / N;
                var multiplier = new Phasor(new Coordinate(Math.cos(theta), -1 * Math.sin(theta)), 0);
                sum = sum.add(phasorSignal[n].multiply(multiplier));
            }
            sum.setTerminal(new Coordinate(sum.getTerminal().getX() / N, sum.getTerminal().getY() / N));
            transformedSignal[k] = sum;
        }
        Arrays.sort(transformedSignal, (first, second) -> Double.compare(second.getMagnitude(), first.getMagnitude()));
        return transformedSignal;
    }
}
