package cpp.lab2.logic;

import cpp.lab2.Publisher;
import cpp.lab2.Subscriber;

import java.util.*;

public class ClientBase implements Publisher {
    private LinkedHashMap<Client, Vector<Vector<Object>>> clientsInfo;
    private LinkedList<Subscriber> subscribers;
    //    private LinkedList<Client> clientsInfo;
    private Map.Entry<Client, Vector<Vector<Object>>> currentClientInfo;

    public ClientBase() {
        subscribers = new LinkedList<>();
        clientsInfo = new LinkedHashMap<>();
    }

    public void addNewClient(Client client) {
        clientsInfo.put(client, new Vector<>());
    }

    /**
     * @param client
     */
    public void switchClient(Client client) {
        Iterator<Map.Entry<Client, Vector<Vector<Object>>>> iterator =
                clientsInfo.entrySet().iterator();
        while(iterator.hasNext()) {
            currentClientInfo = iterator.next();
            if(currentClientInfo.getKey().equals(client)) {
                break;
            }
        }
        for(Subscriber subscriber : subscribers) {
            subscriber.updateClientMoney(currentClientInfo.getKey().getMoney());
            subscriber.updateClientName(currentClientInfo.getKey().getName());

            subscriber.updateClientType(currentClientInfo.getKey().getType());

            subscriber.updateClientOrders(currentClientInfo.getValue());
        }
    }

    public void addOrderInfo( int id, String name, int amount, double cost){
        Vector<Object> orderInfo = new Vector<>();
        orderInfo.add(id);
        orderInfo.add(name);
        orderInfo.add(amount);
        orderInfo.add(cost);
        currentClientInfo.getValue().add(orderInfo);
        for(Subscriber subscriber : subscribers){
            subscriber.addClientOrders(orderInfo);
        }
    }

    public void updateCurrentClientMoney(double money) {
        currentClientInfo.getKey().setMoney(money);
        for(Subscriber subscriber : subscribers) {
            subscriber.updateClientMoney(currentClientInfo.getKey().getMoney());
        }
    }

    public Client getCurrentClient() {
        return currentClientInfo.getKey();
    }


    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unSubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }
}
