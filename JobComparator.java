import java.util.Comparator;

public class JobComparator implements Comparator <Job>
{
    @Override
    public int compare (Job x, Job y)
    {
        if (x.getBurstTime() < y.getBurstTime())
            return -1;
        else if (x.getBurstTime() > y.getBurstTime())
            return 1;
        else if (x.getBurstTime() == y.getBurstTime())
        {
            if (x.getArrivalTime() < y.getArrivalTime())
                return -1;
            else if ( x.getArrivalTime() > y.getArrivalTime() )
                return 1;
        }
        return 0;
    }

}
