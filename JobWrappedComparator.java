import java.util.Comparator;

public class JobWrappedComparator implements Comparator <JobWrapped>
{
    @Override
    public int compare (JobWrapped x, JobWrapped y)
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
