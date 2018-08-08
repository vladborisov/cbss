import java.util.Queue;
import java.util.LinkedList;

public class FCFS_Scheduler
{
    private Queue <Job> jobQueue;
    private int numOfJobs;
    private long[] waitTimes;

    public void schedule(Queue<Job> passedjobQueue, int passednumOfJobs)
    {
        jobQueue = passedjobQueue;
        Queue <Job> myclone = new LinkedList <Job>(jobQueue);
        numOfJobs = passednumOfJobs;
        waitTimes = new long[numOfJobs];

        long currentTime = 0;
        int jobWaitIndex = 0;

        // while there's something on the queue
        while ( !jobQueue.isEmpty() )
        {
            long difference = currentTime - (long)jobQueue.peek().getArrivalTime(); // calculate difference between current time and next arrival

            if (difference < 0)     // if job didn't arrive yet
            {
                waitTimes[jobWaitIndex] = 0;
                currentTime = (long)jobQueue.peek().getArrivalTime();
            }
            else                    // if job already arrived
                waitTimes[jobWaitIndex] = difference;

            currentTime = currentTime + (long)jobQueue.peek().getBurstTime();   // execute current job

            jobQueue.remove();
            jobWaitIndex++;
        }

        // Printing stats
        System.out.println("\taT \t wT");
        for (int i = 0; i < numOfJobs; i++)
        {
            System.out.println(i + "\t" + myclone.peek().getArrivalTime() + "\t " + waitTimes[i]);
            myclone.remove();
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
