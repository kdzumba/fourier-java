import fourier.common.Constants;

import java.util.ArrayList;
import java.util.List;

public class PeriodicWaves
{
    private final List<HarmonicVector> harmonics;
    public PeriodicWaves()
    {
        harmonics = new ArrayList<>();
    }
    public List<HarmonicVector> getSquareWave(int harmonicCount)
    {
        harmonics.clear();

        for(int n = 0; n < harmonicCount; n++)
        {
            var coefficient = 2 * n + 1;
            var amplitude = Constants.AMPLITUDE_SCALE / (coefficient * Math.PI);
            generateHarmonicComponent(n, amplitude, coefficient, harmonicCount);
        }
        return harmonics;
    }

    public List<HarmonicVector> getSawToothWave(int harmonicCount)
    {
        harmonics.clear();

        for(int n = 0; n < harmonicCount; n++)
        {
            var coefficient = n + 1;
            var amplitude = Constants.AMPLITUDE_SCALE / (coefficient * Math.PI);
            generateHarmonicComponent(n, amplitude, coefficient, harmonicCount);
        }
        return harmonics;
    }

    private void generateHarmonicComponent(int n, double amplitude, int coefficient, int harmonicCount)
    {
        HarmonicVector harmonic;

        if(n == 0)
        {
            harmonic = new HarmonicVector(new Pair(Constants.HORIZONTAL_TRANSLATION, Constants.VERTICAL_TRANSLATION),
                    new Pair(Constants.HORIZONTAL_TRANSLATION + amplitude * 2, Constants.VERTICAL_TRANSLATION + amplitude), amplitude, coefficient);
        }
        else
        {
            harmonic = new HarmonicVector(new Pair(harmonics.get(n - 1).getTerminalPoint().x - amplitude, harmonics.get(n - 1).getTerminalPoint().y - amplitude),
                    new Pair(harmonics.get(n - 1).getTerminalPoint().x + amplitude, harmonics.get(n - 1).getTerminalPoint().y),
                    amplitude, coefficient);
        }

        if(n == harmonicCount - 1)
        {
            harmonic.setIsLastHarmonic(true);
        }

        this.harmonics.add(harmonic);
    }
}
