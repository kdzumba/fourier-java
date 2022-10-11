package fourier.algorithms;

import fourier.models.Coordinate;
import fourier.models.Phasor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FourierAlgorithms
{

    private static List<Phasor> convertToPhasor(List<Coordinate> cartesian)
    {

        var interval = cartesian.size() > 15000 ? 3 : 2;
        var phasors = new ArrayList<Phasor>();

        var omission = 0;
        if(cartesian.size() % interval != 0)
            omission = interval;

        for(int n = 0; n < cartesian.size() - omission; n += interval)
        {
            var real = cartesian.get(n).getX();
            var imaginary = cartesian.get(n).getY();
            var complex = new Phasor(new Coordinate(real, imaginary), 0);
            phasors.add(n/interval, complex);
        }
        return phasors;
    }
    public static List<Phasor> discreteFourierTransform(List<Coordinate> imageCoordinates)
    {
        var input = convertToPhasor(imageCoordinates);
        var output = new ArrayList<Phasor>();
        var N = input.size(); //length of input = length of output

        for(int k = 0; k < N; k++)
        {
            var sum = new Phasor(new Coordinate(0, 0), k);
            for(int n = 0; n < N; n++)
            {
                //x_n[cos(2 * PI * k * n)/N - sin(2 * PI * k * n)/N]
                var theta = (2 * Math.PI * k * n) / N;
                var multiplier = new Phasor(new Coordinate(Math.cos(theta), -1 * Math.sin(theta)), 0);

                //Loop expression to the right of the summation symbol
                sum = sum.add(input.get(n).multiply(multiplier));
            }
            sum.setTerminal(new Coordinate(sum.getTerminal().getX() / N, sum.getTerminal().getY() / N));
            output.add(k, sum);
        }
        output.sort((first, second) -> Double.compare(second.getMagnitude(), first.getMagnitude()));
        return output;
    }
}
