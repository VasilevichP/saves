import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class TripSorter {
    private ArrayList<Trip> sortList;
    private Comparator<Trip> comparator;
    private TripSortStrategy sorter;

    TripSorter(){

    }
    public void setSorter(TripSortStrategy s) {
        sorter = s;
    }
    public void setComparator(Comparator<Trip> c){
        comparator = c;
    }

    public void setSource(ArrayList<Trip> l){
        sortList = new ArrayList<Trip>(l);
    }
    public void setSource(TripList l){
        sortList = (ArrayList<Trip>) l.createStream().collect(Collectors.toList());
    }
    public void start(){
        if(comparator != null && sortList != null && sorter != null) {
            sorter.setComparator(comparator);
            sorter.setSource(sortList);
            sorter.start();
        }
    }
    public void display(){
        if(comparator != null && sortList != null && sorter != null) {
            sorter.setComparator(comparator);
            sorter.setSource(sortList);
            sorter.start();
            sorter.display();
        }
    }
    public ArrayList<Trip> toList(){
        if(comparator != null && sortList != null && sorter != null) {
            sorter.setComparator(comparator);
            sorter.setSource(sortList);
            sorter.start();
            return sorter.toList();
        }
        else return null;
    }

}
