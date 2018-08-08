At last! Something new.

Inspired by an OS course is this job manager that applies distinct scheduling algorithms to generated sets of jobs & uses various metrics to compare performance. You'll find it here.


CBSS was implemented over the course of the last couple of weeks as a rudimentary CPU scheduling simulator in Java 8. The program simulates four distinct scheduling algorithms, including First-Come/First-Served, Shortest Job First, Shortest Remaining Time First, and Round Robin, the latter two being preemptive.

CBSS will ask the user for a maximum job boundary as well as a quantum (for use in Round Robin), and randomly generate a set of jobs and their pertinent information, with some variables being functions of others. All four algorithms are simulated with that set, and average waiting times for each are printed. I decided to use average waiting time as the primary metric of algorithm performance comparison as other metrics (like response time, turnaround time, and throughput) rely heavily on the presence of I/O or other external factors which would be outside of the scope of this simulation.

On a more definite note, I quickly faced the limitations of various Java APIs that forced a completely restructuring of the code base. I initially had hoped to call the system clock and use threads and interrupts to schedule the algorithms, though was pushed instead to emulate time due to the constraints of all Java clock functionality. As I continued implementing various components, it was rewarding to develop a robust methodology for developing the logic, with a case-centered approach, several distinct tracing paradigms, and, inevitably, a considerable amount of debugging.

- Vladislav Borisov -
