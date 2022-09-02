package logic;

import model.Server;
import model.Task;

import java.util.List;

public class TimeStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task task) {
        int shortestTime = Integer.MAX_VALUE;
        for(Server server:servers)
            if(server.getWaitingPeriod().intValue() < shortestTime) shortestTime = server.getWaitingPeriod().intValue();

        for(Server server:servers)
            if(server.getWaitingPeriod().intValue() == shortestTime){
                server.addTask(task);
                break;
            }

    }
}
