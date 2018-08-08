import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Comparator;

public class SJF_Scheduler
{
    private Queue <Job> jobQueue;
    private PriorityQueue <Job> waitingJobQueue;
    private int numOfJobs;
    private long[] waitTimes;

    public void schedule (Queue<Job> passedjobQueue, int passednumOfJobs)
    {
        jobQueue = passedjobQueue;
        Queue <Job> myclone = new LinkedList <Job>(jobQueue);
        numOfJobs = passednumOfJobs;
        waitTimes = new long[numOfJobs];

        Comparator<Job> jobComparator = new JobComparator();    // custom comparator class for priority queue
        waitingJobQueue = new PriorityQueue<Job>(numOfJobs, jobComparator);
        Queue <Job> executedJobQueue = new LinkedList<Job>();


        long currentTime = 0;
        int jobWaitIndex = 0;


        while (!jobQueue.isEmpty() || !waitingJobQueue.isEmpty()) // if there are any remaining jobs
        {
            while ( (!jobQueue.isEmpty()) && (jobQueue.peek().getArrivalTime() <= currentTime)) // while job has arrived
            {
                waitingJobQueue.add(jobQueue.peek());
                jobQueue.remove();
            }

            if ( !waitingJobQueue.isEmpty() )       // if there are jobs waiting
            {
                waitTimes[jobWaitIndex] = currentTime - (long)waitingJobQueue.peek().getArrivalTime();
                currentTime = currentTime + (long)waitingJobQueue.peek().getBurstTime();
                executedJobQueue.add(waitingJobQueue.peek());
                waitingJobQueue.remove();
            }
            else
            {
                currentTime = jobQueue.peek().getArrivalTime();
                currentTime = currentTime + jobQueue.peek().getBurstTime();
                executedJobQueue.add(jobQueue.peek());
                jobQueue.remove();
                waitTimes[jobWaitIndex] = 0;
            }
            jobWaitIndex++;
        }


        // printing stats
        System.out.println("\taT \t wT");
        for (int i = 0; i < numOfJobs; i++)
        {
            System.out.println(i + "\t" + executedJobQueue.peek().getArrivalTime() + "\t " + waitTimes[i]);
            executedJobQueue.remove();
        }

        long sumWaitTime = 0;
        for (int i = 0; i < numOfJobs; i++)
        {
            sumWaitTime = sumWaitTime + waitTimes[i];
        }
        double avgWaitTime = (double)sumWaitTime / (double)numOfJobs;

        System.out.printf("\n[Average Wait Time = %.3f", avgWaitTime); System.out.print("]");
    }

}
