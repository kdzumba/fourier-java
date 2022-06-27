import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class FourierSeries extends JFrame implements ActionListener
{
    private final int LEFT = 100;
    private final int UP = 200;
    private final int WINDOW_WIDTH = 1900;
    private final int WINDOW_HEIGHT = 1200;
    private final int CURSOR_X_TRANSLATION = 400;
    private final int TIMER_DELAY = 10;
    private final int AMPLITUDE_SCALER = 400;
    private float angle = 0f;
    private final double HARMONIC_FREQUENCY = 0.05;
    private final List<HarmonicVector> harmonics;
    private final List<Double> waveHeights;
    private Image doubleBufferImage;
    private Graphics doubleBufferGraphics;
    public FourierSeries(String title)
    {
        this.waveHeights = new ArrayList<>();
        this.harmonics = new ArrayList<>();

        addHarmonics(1);

        Timer timer = new Timer(TIMER_DELAY, this);
        timer.start();

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle(title);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addHarmonics(int harmonicCount)
    {
        for(int n = 0; n < harmonicCount; n++)
        {
            var coefficient = 2 * n + 1;
            var amplitude = AMPLITUDE_SCALER / (coefficient * Math.PI);
            HarmonicVector harmonic;

            if(n == 0)
            {
                harmonic = new HarmonicVector(new Pair(LEFT, UP), new Pair(LEFT + amplitude * 2, UP + amplitude), amplitude, coefficient);
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

    public void drawCursor(Graphics2D g, Pair initialPoint, Pair terminalPoint)
    {
        g.draw(new Line2D.Double(initialPoint.x, initialPoint.y, terminalPoint.x, terminalPoint.y));
    }

    private void removeOffScreenPoints()
    {
        //Use traditional for loop here to avoid ConcurrentModificatoinException
        for(int index = 0; index < waveHeights.size(); index++)
        {
            if(index + CURSOR_X_TRANSLATION > WINDOW_WIDTH)
            {
                waveHeights.remove(waveHeights.get(index));
            }
        }
    }
    @Override
    public void paint(Graphics g)
    {
        doubleBufferImage = createImage(getWidth(), getHeight());
        doubleBufferGraphics = doubleBufferImage.getGraphics();

        super.paint(doubleBufferGraphics);
        Graphics2D g2d = (Graphics2D) doubleBufferGraphics;
        doubleBufferGraphics.setColor(Color.WHITE);

        for(int n = 0; n < harmonics.size(); n++)
        {
            var harmonic = harmonics.get(n);
            if(n == 0)
            {
                harmonic.setTerminalPoint(new Pair(harmonic.getInitialPoint().x + harmonic.getRadius() + harmonic.getRadius() * Math.cos(harmonic.getFrequency() * angle),
                        harmonic.getInitialPoint().y + harmonic.getRadius() + harmonic.getRadius() * Math.sin(harmonic.getFrequency() * angle)));
            }
            else
            {
                harmonic.setInitialPoint(new Pair(harmonics.get(n - 1).getTerminalPoint().x - harmonic.getRadius(), harmonics.get(n - 1).getTerminalPoint().y - harmonic.getRadius()));
                harmonic.setTerminalPoint(new Pair(harmonic.getInitialPoint().x + harmonic.getRadius() + harmonic.getRadius() * Math.cos(harmonic.getFrequency() * angle),
                        harmonic.getInitialPoint().y + harmonic.getRadius() + harmonic.getRadius() * Math.sin(harmonic.getFrequency() * angle)));
            }

            harmonic.paintComponent(g2d);

            if(harmonic.getIsLastHarmonic())
            {
                drawCursor(g2d, harmonic.getTerminalPoint(), new Pair(CURSOR_X_TRANSLATION, harmonic.getTerminalPoint().y));
                this.waveHeights.add(0, harmonic.getTerminalPoint().y);
            }
        }

        this.removeOffScreenPoints();
        waveHeights.forEach(height -> g2d.draw(new Line2D.Double(CURSOR_X_TRANSLATION + waveHeights.indexOf(height), height, CURSOR_X_TRANSLATION + waveHeights.indexOf(height), height)));
        g.drawImage(doubleBufferImage, 0, 0, this);
    }

    public static void main(String[] args) {
        new FourierSeries("Fourier Series");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        angle += HARMONIC_FREQUENCY;
        this.repaint();
    }
}
