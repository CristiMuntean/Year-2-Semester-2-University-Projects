package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private final int serverId;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private AtomicInteger queueLength;
    private boolean exit;

    public Server(int serverId){
        this.serverId = serverId;
        this.tasks = new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(0);
        this.queueLength = new AtomicInteger(0);
        this.exit = false;
    }

    public void addTask(Task newTask){
        try {
            this.tasks.put(newTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.waitingPeriod.addAndGet(newTask.getServiceTime());
        this.queueLength.incrementAndGet();
    }

    @Override
    public void run() {
        while(!exit){
            try {
                Task currentTask;
                if(this.queueLength.intValue() > 0){
                    currentTask = this.tasks.element();
                    int sleepTime = currentTask.getServiceTime();
                    Thread.sleep(1000L * sleepTime);
                    currentTask = this.tasks.take();
                    this.waitingPeriod.addAndGet(-sleepTime);
                    this.queueLength.getAndDecrement();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public AtomicInteger getQueueLength() {
        return queueLength;
    }

    @Override
    public String toString() {
        StringBuilder currentTasks = new StringBuilder();
        for(Task task: this.tasks){
            currentTasks.append(task);
            currentTasks.append(" ");
        }
        return "Id:" + this.serverId + ", Waiting time:" + this.getWaitingPeriod() + ", Queue Length:" +
                this.getQueueLength() + ", Tasks:" + currentTasks;
    }

    public void stop(){
        this.exit = true;
    }
}
