import java.io.*;
import java.util.*;
import java.util.stream.Stream;


public class TripList {
    private ArrayList<Trip> trips = new ArrayList<>();
    public static final String FILE_NAME = "Trips.dat";

    public int GetArraylistSize() {
        return trips.size();
    }

    public void addTrip() {
        trips.add(Trip.AddTrip());
    }

    public void calculation() {
        Calculate calc = (x, y) -> x + y;
        double summa = 0;
        for (Trip e : trips) {
            summa = calc.calculate(summa, e.getPrice());
        }
        System.out.println("Цена всех путевок: " + summa);
    }

    public void showTrips() {
        if (trips.size() != 0) {
            int i = 1;
            for (Trip e : trips) {
                System.out.println("№:" + (i++) + '\t' + e.show());
            }
        } else System.out.println("Нет добавленных путевок.");
    }

    public Stream<Trip> createStream() {
        Stream<Trip> stream = trips.stream();
        return stream;
    }

    private static <T> void RemoveTrip(ArrayList<T> list, int index) {
        list.remove(index);
    }

    public void payForFilteredTrips(ArrayList<Trip> fl) {
        for (Trip t : fl) {
            System.out.println(t.show());
        }
        System.out.println("\nВы хотите оплатить все выбранные путевки? (y/n)?");
        Scanner con = new Scanner(System.in);
        String choice = con.nextLine();
        if (choice.equals("y") || choice.equals("Y")) {
            trips.removeAll(fl);
            System.out.println(" Путевки были успешно оплачены.");
        }
        else System.out.println("Ладно.");
    }

    public void chooseTrip(int index) {
        if (index >= 0 && index < trips.size()) {
            trips.get(index).show();
            System.out.println("Вы хотите оплатить данную путевку? (y/n)?");
            Scanner con = new Scanner(System.in);
            String choice = con.nextLine();
            if (choice.equals("y") || choice.equals("Y")) {
                System.out.println(" Путевка была успешно оплачена.");
                RemoveTrip(trips, index);
            } else System.out.println("Ладно.");
        } else System.out.println("Нет путевки с таким номером.");
    }
    public void chooseTrip(){
        if (GetArraylistSize() != 0) {
            byte continueChoose;
            do {
                showTrips();
                System.out.println(" Пожалуйста, выберите номер путевки, которую вы хотите оплатить: \n");
                byte ChooseKod = SafeInput.getByte();
                chooseTrip(ChooseKod - 1);
                System.out.println(" Хотите продолжить выбирать путевки? (Нажмите 1 для продолжения) \n");
                continueChoose = SafeInput.getByte();
            } while (continueChoose == 1);
        } else System.out.println(" Нет добавленных путевок.");
    }

    public void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(trips);
            System.out.println("\n Файл путевок был обновлен.");
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }

    public void readFromFile() {
        ArrayList<Trip> t = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            t = (ArrayList<Trip>) ois.readObject();
        } catch (IOException exc) {
            System.out.println("");
        } catch (Exception exc) {
            System.out.println("Возникла непредвиденная ошибка: " + '\t' +
                    exc.getMessage());
            throw new RuntimeException(exc);
        }
        if (t != null) trips.addAll(t);
        OptionalInt m = trips.stream()
                        .mapToInt((a) ->  (int) a.id)
                        .max();
        if (m.isPresent()) Trip.setNext((byte) m.getAsInt());
    }
}