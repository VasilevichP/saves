import java.util.Comparator;

public class TripComparator  {
   public static Comparator<Trip> ComparatorByNODays(){
       return new Comparator<Trip>() {
           @Override
           public int compare(Trip t1, Trip t2) {
               if (t1.getNumOfDays() < t2.getNumOfDays()) return -1;
               else if (t1.getNumOfDays() > t2.getNumOfDays()) return 1;
               else return 0;
           }
       };
   }
    public static Comparator<Trip> ComparatorByNOMeals(){
        return new Comparator<Trip>() {
            @Override
            public int compare(Trip t1, Trip t2) {
                if (t1.getNumOfMeals() < t2.getNumOfMeals()) return -1;
                else if (t1.getNumOfMeals() > t2.getNumOfMeals()) return 1;
                else return 0;
            }
        };
    }
    public static Comparator<Trip> ComparatorByNOPeople(){
        return new Comparator<Trip>() {
            @Override
            public int compare(Trip t1, Trip t2) {
                if (t1.getNumOfPeople() < t2.getNumOfPeople()) return -1;
                else if (t1.getNumOfPeople() > t2.getNumOfPeople()) return 1;
                else return 0;
            }
        };
    }
    public static Comparator<Trip> ComparatorByPrice(){
        return new Comparator<Trip>() {
            @Override
            public int compare(Trip t1, Trip t2) {
                if (t1.getPrice() < t2.getPrice()) return -1;
                else if (t1.getPrice() > t2.getPrice()) return 1;
                else return 0;
            }
        };
    }
    public static Comparator<Trip> ComparatorByID(){
        return new Comparator<Trip>() {
            @Override
            public int compare(Trip t1, Trip t2) {
                if (t1.getId() < t2.getId()) return -1;
                else if (t1.getId() > t2.getId()) return 1;
                else return 0;
            }
        };
    }
}
