package fourier.interfaces;
public interface IPublisher
{
    void addSubscriber(ISubscriber subscriber);
    void removeSubscriber(ISubscriber subscriber);
    void notifySubscribers();
}
