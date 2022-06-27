import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class FourierSeries extends JFrame implements ActionListener
{
    private float angle = 0f;
    private final List<Double> waveHeights;
    private final PeriodicWaves waves = new PeriodicWaves();
    public FourierSeries(String title)
    {
        this.waveHeights = new ArrayList<>();
        Timer timer = new Timer(Constants.TIMER_DELAY, this);
        timer.start();

        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setTitle(title);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void drawCursor(Graphics2D g, Pair initialPoint, Pair terminalPoint)
    {
        g.draw(new Line2D.Double(initialPoint.x, initialPoint.y, terminalPoint.x, terminalPoint.y));
    }

    public void drawAxis(Graphics2D g, Pair origin)
    {
        g.setColor(Color.white);
        g.draw(new Line2D.Double(0, origin.y, Constants.WINDOW_WIDTH, origin.y));
        g.draw(new Line2D.Double(origin.x, Constants.WINDOW_HEIGHT, origin.x, 0));
    }
    private void removeOffScreenPoints()
    {
        //Use traditional for loop here to avoid ConcurrentModificatoinException
        for(int index = 0; index < waveHeights.size(); index++)
        {
            if(index + Constants.CURSOR_X_TRANSLATION > Constants.WINDOW_WIDTH)
            {
                waveHeights.remove(waveHeights.get(index));
            }
        }
    }
    @Override
    public void paint(Graphics g)
    {
        Image doubleBufferImage = createImage(getWidth(), getHeight());
        Graphics doubleBufferGraphics = doubleBufferImage.getGraphics();

        super.paint(doubleBufferGraphics);
        Graphics2D g2d = (Graphics2D) doubleBufferGraphics;

        //TODO: co-ordinates for the origin here are reaaaalllly random numbers that just happen to fit, need to do this right
        drawAxis(g2d, new Pair(245, 295));
        doubleBufferGraphics.setColor(Color.WHITE);

        var harmonics = waves.getSawToothWave(100);

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
                drawCursor(g2d, harmonic.getTerminalPoint(), new Pair(Constants.CURSOR_X_TRANSLATION, harmonic.getTerminalPoint().y));
                this.waveHeights.add(0, harmonic.getTerminalPoint().y);
            }
        }

        this.removeOffScreenPoints();
        waveHeights.forEach(height -> g2d.draw(new Line2D.Double(Constants.CURSOR_X_TRANSLATION + waveHeights.indexOf(height), height, Constants.CURSOR_X_TRANSLATION + waveHeights.indexOf(height), height)));
        g.drawImage(doubleBufferImage, 0, 0, this);
    }
    public static void main(String[] args) {
        new FourierSeries("Fourier Series");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        angle += Constants.HARMONIC_FREQUENCY;
        this.repaint();
    }
}
