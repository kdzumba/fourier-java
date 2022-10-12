package fourier.ui;

import fourier.Application;
import fourier.imageprocessing.ImageProcessor;
import fourier.interfaces.IPublisher;
import fourier.interfaces.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PreLoadedImagesPanel extends JPanel implements ISubscriber, IPublisher
{
    private URL activeURL = null;
    private final List<ISubscriber> subscribers = new ArrayList<>();
    public PreLoadedImagesPanel()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(UICommon.COMPONENT_BACKGROUND);

        var einsteinUrl = getClass().getClassLoader().getResource("preloadedimages/einstein.txt");
        var einsteinBtn = new PreloadedImageButton("Einstein", einsteinUrl);
        einsteinBtn.addSubscriber(this);
        this.add(einsteinBtn);

        var roseUrl = getClass().getClassLoader().getResource("preloadedimages/rose.txt");
        var roseBtn = new PreloadedImageButton("Rose", roseUrl);
        roseBtn.addSubscriber(this);
        this.add(roseBtn);

        var ballerinaUrl = getClass().getClassLoader().getResource("preloadedimages/ballerina.txt");
        var ballerinaBtn = new PreloadedImageButton("Ballerina", ballerinaUrl);
        ballerinaBtn.addSubscriber(this);
        this.add(ballerinaBtn);

        var personUrl = getClass().getClassLoader().getResource("preloadedimages/person_with_glasses.txt");
        var personBtn = new PreloadedImageButton("Person with Glasses", personUrl);
        personBtn.addSubscriber(this);
        this.add(personBtn);

        var friendsUrl = getClass().getClassLoader().getResource("preloadedimages/friends.txt");
        var friendsBtn = new PreloadedImageButton("Friends", friendsUrl);
        friendsBtn.addSubscriber(this);
        this.add(friendsBtn);

        var girlAndDogUrl = getClass().getClassLoader().getResource("preloadedimages/girl_and_dog.txt");
        var girlAndDogBtn = new PreloadedImageButton("Girl And Dog", girlAndDogUrl);
        girlAndDogBtn.addSubscriber(this);
        this.add(girlAndDogBtn);

        var yogaUrl = getClass().getClassLoader().getResource("preloadedimages/yoga.txt");
        var yogaBtn = new PreloadedImageButton("Yoga", yogaUrl);
        yogaBtn.addSubscriber(this);
        this.add(yogaBtn);

        var unicornUrl = getClass().getClassLoader().getResource("preloadedimages/unicorn.txt");
        var unicornBtn = new PreloadedImageButton("unicorn", unicornUrl);
        unicornBtn.addSubscriber(this);
        this.add(unicornBtn);

        var daenerysUrl = getClass().getClassLoader().getResource("preloadedimages/daenerys.txt");
        var daenerysBtn = new PreloadedImageButton("Daenerys", daenerysUrl);
        daenerysBtn.addSubscriber(this);
        this.add(daenerysBtn);

        var conclusionUrl = getClass().getClassLoader().getResource("preloadedimages/conclusion.txt");
        var conclusionBtn = new PreloadedImageButton("Conclusion", conclusionUrl);
        conclusionBtn.addSubscriber(this);
        this.add(conclusionBtn);
    }

    @Override
    public void update()
    {
        Application.imagePoints = ImageProcessor.loadImagePointsFromFile(this.activeURL);
        this.notifySubscribers();
    }

    @Override
    public void addSubscriber(ISubscriber subscriber)
    {
        this.subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber)
    {
        this.subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers()
    {
        this.subscribers.forEach(ISubscriber::update);
    }

    public class PreloadedImageButton extends JButton implements ActionListener, IPublisher
    {
        private final URL url;
        private final List<ISubscriber> subscribers = new ArrayList<>();

        public PreloadedImageButton(String text, URL url)
        {
            this.setText(text);
            this.url = url;
            this.addActionListener(this);
            this.setBackground(UICommon.COMPONENT_BACKGROUND);
            this.setForeground(Color.WHITE);
            this.setFont(UICommon.ALGO_FONT);
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT);
        }

        @Override
        public Dimension getMaximumSize(){
            return new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT);
        }

        @Override
        public Dimension getMinimumSize(){
            return new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            activeURL = this.url;
            this.notifySubscribers();
        }

        @Override
        public void addSubscriber(ISubscriber subscriber)
        {
            this.subscribers.add(subscriber);
        }

        @Override
        public void removeSubscriber(ISubscriber subscriber)
        {
            this.subscribers.remove(subscriber);
        }

        @Override
        public void notifySubscribers()
        {
            this.subscribers.forEach(ISubscriber::update);
        }
    }
}
