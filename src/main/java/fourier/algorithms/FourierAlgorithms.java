package fourier.algorithms;

import fourier.models.ComplexNumber;

import java.util.ArrayList;
import java.util.List;

public class FourierAlgorithms
{
    public static List<ComplexNumber> discreteFourierTransform(List<ComplexNumber> originalSignal)
    {
        var transformedSignal = new ArrayList<ComplexNumber>();
        var N = originalSignal.size();

        for(int k = 0; k < N; k++)
        {
            var sum = new ComplexNumber(0, 0, k);

            for(int n = 0; n < N; n++)
            {
                var theta = (2 * Math.PI * k * n) / N;
                var multiplier = new ComplexNumber(Math.cos(theta), -1 * Math.sin(theta), 0);
                sum = sum.add(originalSignal.get(n).multiply(multiplier));
            }

            sum.setReal(sum.getReal() / N);
            sum.setImaginary(sum.getImaginary() / N);
            transformedSignal.add(sum);
        }
        transformedSignal.sort((first, second) -> Double.compare(second.getMagnitude(), first.getMagnitude()));
        return transformedSignal;
    }
}
