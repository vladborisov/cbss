import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Comparator;

public class SRTF_Scheduler
{
    private Queue <JobWrapped> jobQueue;
    private PriorityQueue <JobWrapped> waitingJobQueue;
    private int numOfJobs;

    public void schedule (Queue<Job> passedJobQueue, int passednumOfJObs)
    {
        Queue <Job> myclone = new LinkedList<Job>(passedJobQueue);
        numOfJobs = passednumOfJObs;
        Queue <JobWrapped> jobQueue = new LinkedList<JobWrapped>();

        // wrapper class to encapsulate wait time and preemption tracking
        while (!passedJobQueue.isEmpty())
        {
            JobWrapped tempJob = new JobWrapped();
            tempJob.setArrivalTime(passedJobQueue.peek().getArrivalTime());
            tempJob.setBurstTime(passedJobQueue.peek().getBurstTime());
            passedJobQueue.remove();
            jobQueue.add(tempJob);
        }

        Comparator <JobWrapped> jobWrappedComparator = new JobWrappedComparator();
        waitingJobQueue = new PriorityQueue <JobWrapped> (numOfJobs, jobWrappedComparator);
        Queue <JobWrapped> executedJobQueue = new LinkedList<JobWrapped>();

        int currentTime = 0;

        while ( !jobQueue.isEmpty() || !waitingJobQueue.isEmpty() )         // As long as there are uncompleted jobs
        {
            if(!jobQueue.isEmpty() && waitingJobQueue.isEmpty())                    // ++++ If Something on jobQueue, Nothing on waitingJobQueue
            {
                currentTime = jobQueue.peek().getArrivalTime();
                waitingJobQueue.add(jobQueue.poll());
                if (jobQueue.isEmpty())                                                      // If jobQueue is empty
                {
                    currentTime += waitingJobQueue.peek().getBurstTime();
                    waitingJobQueue.peek().setBurstTime(0);
                    executedJobQueue.add(waitingJobQueue.poll());

                }
                else if ( waitingJobQueue.peek().getBurstTime() + currentTime > jobQueue.peek().getArrivalTime() )          // If a job would arrive before current finishes executing
                {
                    currentTime = jobQueue.peek().getArrivalTime();
                    waitingJobQueue.peek().setPreemptedAt(currentTime);
                    jobQueue.peek().setPreemptedAt(currentTime);
                    waitingJobQueue.peek().setBurstTime( waitingJobQueue.peek().getBurstTime() - ( jobQueue.peek().getArrivalTime() - waitingJobQueue.peek().getArrivalTime() ) );
                    waitingJobQueue.add(jobQueue.poll());
                }
                else                                                                                                        // If nothing arrives before current finishes executing
                {
                    currentTime += waitingJobQueue.peek().getBurstTime();
                    waitingJobQueue.peek().setBurstTime(0);
                    executedJobQueue.add(waitingJobQueue.poll());
                }
            }
            else if (jobQueue.isEmpty() && !waitingJobQueue.isEmpty())              // ++++ If jobQueue empty, Something on waitingJobQueue
            {
                waitingJobQueue.peek().setWaitedSoFar( waitingJobQueue.peek().getWaitedSoFar() + (currentTime - waitingJobQueue.peek().getPreemptedAtTime()) );
                currentTime += waitingJobQueue.peek().getBurstTime();
                waitingJobQueue.peek().setBurstTime(0);
                executedJobQueue.add(waitingJobQueue.poll());
            }
            else if (!jobQueue.isEmpty() && !waitingJobQueue.isEmpty())             // ++++ If something on both jobQueue & waitingJobQueue
            {
                if (waitingJobQueue.peek().getBurstTime() + currentTime > jobQueue.peek().getArrivalTime())                 // If a job would arrive before current finishes executing
                {
                    waitingJobQueue.peek().setWaitedSoFar( waitingJobQueue.peek().getWaitedSoFar() + (currentTime - waitingJobQueue.peek().getPreemptedAtTime()) );
                    waitingJobQueue.peek().setBurstTime(waitingJobQueue.peek().getBurstTime() - (jobQueue.peek().getArrivalTime() - currentTime));
                    currentTime = jobQueue.peek().getArrivalTime();
                    jobQueue.peek().setPreemptedAt(currentTime);
                    waitingJobQueue.peek().setPreemptedAt(currentTime);
                    waitingJobQueue.add(jobQueue.poll());
                }
                else                                                                                                        // If nothing arrives before current finishes executing
                {
                    waitingJobQueue.peek().setWaitedSoFar( waitingJobQueue.peek().getWaitedSoFar() + (currentTime - waitingJobQueue.peek().getPreemptedAtTime()) );
                    currentTime += waitingJobQueue.peek().getBurstTime();
                    waitingJobQueue.peek().setBurstTime(0);
                    executedJobQueue.add(waitingJobQueue.poll());
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

