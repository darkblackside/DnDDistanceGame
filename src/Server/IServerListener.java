package Server;

/**
 * Created by bmers on 20.06.2016.
 */
public interface IServerListener
{
    public void sendActionToUser(String firstServerMessage, String... serverMessage);
}
