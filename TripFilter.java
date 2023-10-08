import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TripFilter {
    private ArrayList<Trip> sourceList;
    private ArrayList<Trip> filteredList;
    private boolean isByType, isByCountry, isByTransport;
    private String typeValue, countryValue, transportValue;
    private TripSorter sorter;

    TripFilter(){
        removeAllFilters();
    }

    public void setSource(ArrayList<Trip> l){
        sourceList = l;
    }
    public void setSource(TripList tl){
        sourceList = (ArrayList<Trip>) tl.createStream().collect(Collectors.toList());
        filteredList = new ArrayList<Trip>(sourceList);
    }
    public void setSorter(TripSorter s){
        sorter = s;
    }
    public void removeAllFilters(){
        isByType = false;
        isByCountry = false;
        isByTransport = false;
        typeValue =  countryValue =  transportValue = "";
    }


    public void setByType(String filterValue){
        isByType = true;
        typeValue = filterValue;
    }
    public void removeByType(){
        isByType = false;
        typeValue = "";
    }
    public void setByCountry(String filterValue){
        isByCountry = true;
        countryValue = filterValue;
    }
    public void removeByCountry(){
        isByCountry = false;
        countryValue = "";
    }
    public void setByTransport(String filterValue){
        isByTransport = true;
        transportValue = filterValue;
    }
    public void removeByTransport(){
        isByTransport = false;
        transportValue = "";
    }

    private Stream<Trip> filterByType(Stream<Trip> s){
        if (isByType)
            return s.filter(x -> x.getType().equals(typeValue));
        else
            return s;
    }
    private Stream<Trip> filterByCountry(Stream<Trip> s){
        if (isByCountry)
            return s.filter(x -> x.getCountry().equals(countryValue));
        else
            return s;
    }
    private Stream<Trip> filterByTransport(Stream<Trip> s){
        if (isByTransport)
            return s.filter(x -> x.getTransport().equals(transportValue));
        else
            return s;
    }

    public ArrayList<Trip> apply(){
        Stream<Trip> s = sourceList.stream();
        s = filterByType(s);
        s = filterByCountry(s);
        s = filterByTransport(s);
        filteredList = (ArrayList<Trip>) s.collect(Collectors.toList());
        sorter.setSource(filteredList);
        sorter.start();
        return filteredList;
    }
    public void displayList(){
        filteredList.stream().forEach(x -> System.out.println(x.show()));
    }
    public ArrayList<Trip> toList(){
        return filteredList;
    }
}
