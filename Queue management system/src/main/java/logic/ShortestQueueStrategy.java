package logic;

import model.Server;
import model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task task) {
        int shortestQueueLength = Integer.MAX_VALUE;
        for(Server server:servers)
            if (server.getQueueLength().intValue() < shortestQueueLength)shortestQueueLength = server.getQueueLength().intValue();

        for(Server server:servers)
            if(server.getQueueLength().intValue() == shortestQueueLength){
                server.addTask(task);
                break;
            }

    }
}
