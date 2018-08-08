import java.util.Queue;
import java.util.LinkedList;



public class RR_Scheduler
{
    private Queue <JobWrapped> jobQueue;
    private int numOfJobs;


    public void schedule (Queue<Job> passedJobQueue, int passednumOfJobs, int quantum)
    {
        Queue <Job> myclone = new LinkedList<Job>(passedJobQueue);
        numOfJobs = passednumOfJobs;
        Queue <JobWrapped> jobQueue = new LinkedList<JobWrapped>();

        // wrapper class to encapsulate wait times and preemption tracking
        while (!passedJobQueue.isEmpty())
        {
            JobWrapped tempJob = new JobWrapped();
            tempJob.setArrivalTime(passedJobQueue.peek().getArrivalTime());
            tempJob.setBurstTime(passedJobQueue.peek().getBurstTime());
            passedJobQueue.remove();
            jobQueue.add(tempJob);
        }

        Queue <JobWrapped> executedJobQueue = new LinkedList<JobWrapped>();
        Queue <JobWrapped> executingJobQueue = new LinkedList<JobWrapped>();

        int currentTime = 0;

        while (!jobQueue.isEmpty() || !executingJobQueue.isEmpty())         // ++ As long as there are uncompleted jobs
        {
            if (!jobQueue.isEmpty() && executingJobQueue.isEmpty())            // if there is something on the jobQueue & nothing on executing
            {
                if (currentTime >= jobQueue.peek().getArrivalTime())        //if its job has arrived
                {
                    jobQueue.peek().setWaitedSoFar(currentTime - jobQueue.peek().getArrivalTime());
                    jobQueue.peek().setPreemptedAt(currentTime);
                    jobQueue.peek().setComesInAt(currentTime);
                    executingJobQueue.add(jobQueue.poll());
                }
                else    // if job hasn't yet arrived
                {
                    currentTime = jobQueue.peek().getArrivalTime();
                    jobQueue.peek().setPreemptedAt(currentTime);
                    jobQueue.peek().setComesInAt(currentTime);
                    executingJobQueue.add(jobQueue.poll());
                }
            }

            if (!executingJobQueue.isEmpty() && executingJobQueue.peek().getBurstTime() > quantum)      // If current job's RBT is greater than quantum
            {
                executingJobQueue.peek().setWaitedSoFar( executingJobQueue.peek().getWaitedSoFar() + (currentTime - executingJobQueue.peek().getPreemptedAtTime()) );
                currentTime += quantum;
                executingJobQueue.peek().setBurstTime(executingJobQueue.peek().getBurstTime() - quantum);
                while ( !jobQueue.isEmpty() && currentTime >= jobQueue.peek().getArrivalTime())                 //If something has arrived
                {
                    jobQueue.peek().setPreemptedAt(jobQueue.peek().getArrivalTime());
                    jobQueue.peek().setComesInAt(currentTime);
                    executingJobQueue.add(jobQueue.poll());
                }
                executingJobQueue.peek().setPreemptedAt(currentTime);
                executingJobQueue.add(executingJobQueue.poll());
            }
            else if (!executingJobQueue.isEmpty() && executingJobQueue.peek().getBurstTime() <= quantum)   // If current job's RBT is smaller than or equal to quantum
            {

                executingJobQueue.peek().setWaitedSoFar( executingJobQueue.peek().getWaitedSoFar() + (currentTime - executingJobQueue.peek().getPreemptedAtTime()) );
                currentTime += executingJobQueue.peek().getBurstTime();
                executingJobQueue.peek().setBurstTime(0);
                executingJobQueue.peek().setFinishedAt(currentTime);
                executedJobQueue.add(executingJobQueue.poll());

                if (!jobQueue.isEmpty() && currentTime >= jobQueue.peek().getArrivalTime() )        // if something has arrived
                {
                    while ( !jobQueue.isEmpty() && currentTime >= jobQueue.peek().getArrivalTime())
                    {
                        jobQueue.peek().setPreemptedAt(jobQueue.peek().getArrivalTime());
                        jobQueue.peek().setComesInAt(currentTime);
                        executingJobQueue.add(jobQueue.poll());
                    }
                }

            }
        }

        // printing stats
        long sumWaitTime = 0;
        System.out.println("\taT \t wT");

        for (int i = 0; i < numOfJobs; i++)
        {
            System.out.println(i + "\t" + executedJobQueue.peek().getArrivalTime() + "\t " + executedJobQueue.peek().getWaitedSoFar());
            sumWaitTime += executedJobQueue.peek().getWaitedSoFar();
            executedJobQueue.remove();
        }

        double avgWaitTime = (double)sumWaitTime / (double)numOfJobs;
        System.out.printf("\n[Average Wait Time = %.3f", avgWaitTime); System.out.print("]");

    }

}
