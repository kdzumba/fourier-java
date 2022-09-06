package fourier.interfaces;

import fourier.models.Coordinate;

import java.util.List;

public interface IPublisher
{
    void addSubscriber(ISubscriber subscriber);
    void removeSubscriber(ISubscriber subscriber);
    void notifySubscribers();
}
