package logic;

import model.SelectionPolicy;
import model.Server;
import model.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimulationManager implements Runnable{
    public int timeLimit;
    public int maxArrivalTime;
    public int minArrivalTime;
    public int maxServiceTime;
    public int minServiceTime;
    public int numberOfServers;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy;

    private Scheduler scheduler;
    private CopyOnWriteArrayList<Task> generatedTasks;
    private String message;
    private boolean isRunning;

    public SimulationManager(int numberOfClients, int numberOfServers, int minArrivalTime, int maxArrivalTime,
                             int minServiceTime, int maxServiceTime, int timeLimit, String selectionPolicy){
        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        this.timeLimit = timeLimit;
        this.selectStrategy(selectionPolicy);
        this.isRunning = true;

        this.scheduler = new Scheduler(this.numberOfServers,this.selectionPolicy);
        this.generatedTasks = new CopyOnWriteArrayList<>();
        generateNRandomTasks(this.numberOfClients);
        this.message = "";
    }

    private void selectStrategy(String strategy){
        if(strategy.equals("Shortest time"))
            this.selectionPolicy = SelectionPolicy.SHORTEST_TIME;
        else this.selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
    }

    private void generateNRandomTasks(int nTasks){
        Random random = new Random();
        for(int i=0;i<nTasks;i++){
            this.generatedTasks.add(new Task(i,random.nextInt(minArrivalTime,maxArrivalTime+1),
                    random.nextInt(minServiceTime,maxServiceTime+1)));
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void run() {
        float avgServiceTime = 0, avgWaitingTime = 0, maxWaitingTime = 0;
        int peakHour = -1;
        for(int i=0;i<this.numberOfClients;i++)
            avgServiceTime += this.generatedTasks.get(i).getServiceTime();
        avgServiceTime /= this.numberOfClients;
        try{
            File log = new File("log.txt");
            FileWriter writer = new FileWriter(log);
            if(!log.exists())
                log.createNewFile();
            StringBuilder guiMessage = new StringBuilder();
            writer.write("\n-------Simulation begins-------\n");
            guiMessage.append("\n-------Simulation begins-------\n");
            this.setMessage(guiMessage);
            int currentTime = 0;
            while(currentTime <= this.timeLimit && !isFinished()){
                for(Task task:this.generatedTasks){
                    if(task.getArrivalTime() == currentTime){
                        this.scheduler.dispatchTask(task);
                        this.generatedTasks.remove(task);
                    }
                }
                guiMessage.delete(0, guiMessage.length());
                guiMessage.append("<html>");
                float avgWaitCurrTime = 0;
                guiMessage.append("\n---Time:").append(currentTime).append("---<br/>").append("-------Waiting Clients-------<br/>");
                writer.write("\n---Time:"+currentTime+"---\n");
                writer.write("-------Waiting Clients-------\n");
                StringBuilder remainingTasks = new StringBuilder();
                for(Task task:this.generatedTasks){
                    remainingTasks.append(task);
                    remainingTasks.append(" ");
                }
                writer.write(remainingTasks + "\n");
                guiMessage.append(remainingTasks).append("<br/>").append("-------Servers-------<br/>");
                writer.write("-------Servers-------\n");
                for(Server server:this.scheduler.getServers()){
                    writer.write(server.toString() + "\n");
                    guiMessage.append(server).append("<br/>");
                    avgWaitCurrTime += server.getWaitingPeriod().floatValue();
                }
                avgWaitCurrTime /= this.numberOfServers;
                if(avgWaitCurrTime > maxWaitingTime){
                    maxWaitingTime = avgWaitCurrTime;
                    peakHour = currentTime;
                }
                avgWaitingTime += avgWaitCurrTime;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentTime++;
                guiMessage.append("</html>");
                this.setMessage(guiMessage);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guiMessage.delete(0,guiMessage.length());
            guiMessage.append("<html>");
            writer.write("\n---Time:"+currentTime+"---\n");
            guiMessage.append("---Time:").append(currentTime).append("---<br/>").append("-------End of simulation-------<br/>");
            writer.write("-------End of simulation-------" + "\n");
            avgWaitingTime /= currentTime;
            writer.write("Average service time:" + avgServiceTime + "\n");
            writer.write("Average waiting time:" + avgWaitingTime + "\n");
            writer.write("Peak hour:" + peakHour + ", with a waiting time of:" + maxWaitingTime + "\n");
            guiMessage.append("Average service time:").append(avgServiceTime).append("<br/>");
            guiMessage.append("Average waiting time:").append(avgWaitingTime).append("<br/>");
            guiMessage.append("Peak hour:").append(peakHour).append(", with a waiting time of:").append(maxWaitingTime).append("<br/></html>");
            this.setMessage(guiMessage);
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        for (Server server: scheduler.getServers())
            server.stop();
        this.isRunning = false;
    }

    private boolean isFinished(){
        boolean currentlyInQueue = false;
        for(Server server: scheduler.getServers())
            if(server.getQueueLength().intValue()>0)currentlyInQueue = true;
        return this.generatedTasks.size() == 0 && !currentlyInQueue;
    }

    private void setMessage(StringBuilder message) {
        this.message = message.toString();
    }
}
