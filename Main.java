import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Queue;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int maxNumOfJobs = -1;
        int quantum = -1;

        // INPUTS
        System.out.println("  _____ _____  _    _    _____      _              _    _____ _ ");
        System.out.println(" / ____|  __ \\| |  | |  / ____|    | |            | |  / ____(_)          ");
        System.out.println("| |    | |__) | |  | | | (___   ___| |__   ___  __| | | (___  _ _ __ ___  ");
        System.out.println("| |    |  ___/| |  | |  \\___ \\ / __| '_ \\ / _ \\/ _` |  \\___ \\| | '_ ` _ \\ ");
        System.out.println("| |____| |    | |__| |  ____) | (__| | | |  __/ (_| |  ____) | | | | | | |");
        System.out.println(" \\_____|_|     \\____/  |_____/ \\___|_| |_|\\___|\\__,_| |_____/|_|_| |_| |_|");
        System.out.println("                                  - Vladislav Borisov & Benjamin Karasik -");
        System.out.println("\nWelcome to CPU Scheduler Simulator 1.0!\n");

        while (maxNumOfJobs <= 0)
        {
            System.out.print("Please choose the maximum number of jobs: ");
            maxNumOfJobs = sc.nextInt();
            if (maxNumOfJobs <= 0)
                System.out.println("Input Invalid! Enter a positive number of jobs.");
        }

        System.out.println();

        while (quantum <= 0)
        {
            System.out.print("Please choose the quantum for Round Robin: ");
            quantum = sc.nextInt();
            if (quantum <= 0)
                System.out.println("Input Invalid! Enter a positive quantum.");
        }

        // RANDOM GENERATION
        Random rand = new Random();

        int numOfJobs = rand.nextInt(maxNumOfJobs) + 1;      // Random Num of Jobs 1->max
        Job[] jobsList = new Job[numOfJobs];        // Instantiating Array of Jobs

        for (int i = 0; i < numOfJobs; i++)         // Initializing each Job in Job Array w/Rand arrival/burst time
        {
            jobsList[i] = new Job();
            jobsList[i].setArrivalTime(rand.nextInt(maxNumOfJobs*10));
            jobsList[i].setBurstTime(rand.nextInt(maxNumOfJobs*2) + 1);
        }

        // SORTING BY ARRIVAL TIME
        Arrays.sort(jobsList);          // Java API Sorter

        Queue <Job> jobQueue = new LinkedList<Job>();          // Creating New Structure (Queue) to Use for Jobs

        for (int i = 0; i < numOfJobs; i++)         // Removing Duplicate Arrival Times while Transferring Jobs to Queue
        {
            if (i == numOfJobs - 1)
                jobQueue.add(jobsList[i]);
            else if ( jobsList[i].getArrivalTime() != jobsList[i+1].getArrivalTime() )
                jobQueue.add(jobsList[i]);
        }

        numOfJobs = jobQueue.size();

        // INSTANTIATION/INITIALIZATION OF QUEUES
        Queue <Job> fcfs_jobs = new LinkedList<Job>(jobQueue);      // the use of separate queues is necessary to prevent overwriting of data via Java's pass by value-reference class object dynamic
        Queue <Job> sjf_jobs = new LinkedList<Job>(jobQueue);
        Queue <Job> srtf_jobs = new LinkedList<Job>(jobQueue);
        Queue <Job> rr_jobs = new LinkedList<Job>(jobQueue);

        System.out.println("\nHere are your randomly generated jobs:\n");
        System.out.println(" KEY |");
        System.out.println("-----\taT\tArrival Time");
        System.out.println("\t\tbT\tBurst Time");
        System.out.println("\t\twT\tWait Time");

        System.out.println("\n\taT \t bT");
        for (int i = 0; i < numOfJobs; i++)
        {
            System.out.println(i + "\t" + jobsList[i].getArrivalTime() + "\t " + jobsList[i].getBurstTime());
        }

        System.out.println("\n Press [ENTER] to proceed with scheduling!");
        try
        {
            System.in.read();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // PASSING TO EACH SCHEDULING ALGORITHM
        System.out.println("\n\n- FCFS Scheduling -");
        FCFS_Scheduler fcfs_scheduler = new FCFS_Scheduler();
        fcfs_scheduler.schedule(fcfs_jobs, numOfJobs);

        System.out.println("\n\n\n- SJF Scheduling -");
        SJF_Scheduler sjf_scheduler = new SJF_Scheduler();
        sjf_scheduler.schedule(sjf_jobs, numOfJobs);


        System.out.println("\n\n\n- SRTF Scheduling -");
        SRTF_Scheduler srtf_scheduler = new SRTF_Scheduler();
        srtf_scheduler.schedule(srtf_jobs, numOfJobs);

        System.out.println("\n\n\n- RR Scheduling -");
        RR_Scheduler rr_scheduler = new RR_Scheduler();
        rr_scheduler.schedule(rr_jobs, numOfJobs, quantum);
    }
}