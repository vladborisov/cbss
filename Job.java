

public class Job implements Comparable <Job>
{
    private int arrivalTime;
    private int burstTime;

    void setArrivalTime(int aT)
    {
        arrivalTime = aT;
    }

    void setBurstTime(int bT)
    {
        burstTime = bT;
    }

    int getArrivalTime()
    {
        return arrivalTime;
    }

    int getBurstTime()
    {
        return burstTime;
    }

    public int compareTo(Job compareJob)
    {
        int compareArrivalTime = ((Job) compareJob).getArrivalTime();

        //ascending order
        return this.arrivalTime - compareArrivalTime;
    }
}