import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class SortThread extends Thread implements TripSortStrategy{
    ArrayList<Trip> sortList;
    Comparator<Trip> comparator;
    private Thread thread;
    SortThread(){
        thread = new Thread(this);
    }
    SortThread(Stream<Trip> tripList, Comparator<Trip> c){
        this();
        setSource((ArrayList<Trip>) tripList.collect(Collectors.toList()));
        setComparator(c);
    }

    public void setComparator(Comparator<Trip> c){
        comparator = c;
    }

    @Override
    public void setSource(ArrayList<Trip> l) {
        sortList = l;
    }

    public void run(){
        synchronized (sortList) {
            Collections.sort(sortList, comparator);
        }
    }
    public void start(){
        if (thread == null || thread.getState() == State.TERMINATED)
            thread = new Thread(this);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e){
            System.out.println(e);
        }
        //display();
    }
    public void display(){
        int i =1;
        for (Trip e : sortList) System.out.println("№:" + (i++) + '\t' + e.show());
        System.out.println("\n");
    }
    public ArrayList<Trip> toList(){
        return sortList;
    }
}
