import java.util.ArrayList;
import java.util.Comparator;

public interface TripSortStrategy {
void setComparator(Comparator<Trip> c);
void setSource(ArrayList<Trip> l);
void start();
void display();
ArrayList<Trip> toList();

}
