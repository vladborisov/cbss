
public class JobWrapped extends Job
{
    private int waitedSoFar = 0;
    private int preemptedAtTime = 0;
    private int finishedAt = 0;
    private int comesInAt = 0;

    void setWaitedSoFar (int wT)
    {
        waitedSoFar = wT;
    }

    int getWaitedSoFar ()
    {
        return waitedSoFar;
    }

    void setPreemptedAt (int paT)
    {
        preemptedAtTime = paT;
    }

    int getPreemptedAtTime ()
    {
        return preemptedAtTime;
    }

    void setFinishedAt (int fA)
    {
        finishedAt = fA;
    }

    int getFinishedAt()
    {
        return finishedAt;
    }

    void setComesInAt (int cI)
    {
        comesInAt = cI;
    }

    int getComesInAt()
    {
        return comesInAt;
    }

}
