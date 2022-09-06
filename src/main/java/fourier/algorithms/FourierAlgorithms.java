package fourier.algorithms;

import fourier.models.ComplexNumber;

import java.util.Arrays;

public class FourierAlgorithms
{
    public static ComplexNumber[] discreteFourierTransform(ComplexNumber[] originalSignal)
    {
        var transformedSignal = new ComplexNumber[originalSignal.length];
        var N = originalSignal.length;

        for(int k = 0; k < N; k++)
        {
            var sum = new ComplexNumber(0, 0, k);
            for(int n = 0; n < N; n++)
            {
                var theta = (2 * Math.PI * k * n) / N;
                var multiplier = new ComplexNumber(Math.cos(theta), -1 * Math.sin(theta), 0);
                sum = sum.add(originalSignal[n].multiply(multiplier));
            }
            sum.setReal(sum.getReal() / N);
            sum.setImaginary(sum.getImaginary() / N);
            transformedSignal[k] = sum;
        }
        Arrays.sort(transformedSignal, (first, second) -> Double.compare(second.getMagnitude(), first.getMagnitude()));
        return transformedSignal;
    }
}
