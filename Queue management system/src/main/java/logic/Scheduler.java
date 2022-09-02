package logic;

import model.SelectionPolicy;
import model.Server;
import model.Task;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Server> servers;
    private int noServers;
    private Strategy strategy;
    private ArrayList<Thread> serverThreads;

    public Scheduler(int maxNoServers, SelectionPolicy selectionPolicy){
        this.servers = new ArrayList<>();
        this.noServers = maxNoServers;
        this.serverThreads = new ArrayList<>();
        changePolicy(selectionPolicy);
        for(int i=0;i<this.noServers;i++){
            this.servers.add(new Server(i));
            serverThreads.add(new Thread(this.servers.get(i)));
            serverThreads.get(i).start();
        }
    }


    public void changePolicy(SelectionPolicy selectionPolicy){
        if(selectionPolicy == SelectionPolicy.SHORTEST_QUEUE){
            this.strategy = new ShortestQueueStrategy();
        }
        else if(selectionPolicy == SelectionPolicy.SHORTEST_TIME){
            this.strategy = new TimeStrategy();
        }
    }

    public void dispatchTask(Task task){
        this.strategy.addTask(this.servers,task);
    }

    public ArrayList<Server> getServers(){
        return this.servers;
    }
}
