import java.util.Scanner;
import java.io.*;

class Trip implements Serializable{
    private static byte _nextId = 0;
    protected   byte id;
    protected String type;
    protected String country;
    protected String transport;
    protected String ship;
    protected byte number_of_days;
    protected byte number_of_stars;
    protected byte number_of_meals;
    protected byte number_of_procedures;
    protected byte number_of_people;
    protected double price;
    protected Trip() {
        this.id = ++_nextId;
    }
    public String show(){
        return "Has to be redefined below";
    };
    private static String SelectCountry() {
        Scanner con = new Scanner(System.in);
        System.out.println("Введите страну (1 - Греция; 2 - Египет; 3 - Турция; 4 - Франция; \n " +
                "5 - Германия (Иначе внутри страны):");
        byte userCountryKod = SafeInput.getByte();
        String userCountry = null;
        switch (userCountryKod) {
            case 1:
                userCountry = "Греция";
                break;
            case 2:
                userCountry = "Египет";
                break;
            case 3:
                userCountry = "Турция";
                break;
            case 4:
                userCountry = "Франция";
                break;
            case 5:
                userCountry = "Германия";
                break;
            default: userCountry = "Внутри страны";
                break;
        }
        return userCountry;
    }
    private static String SelectTransport(){
        Scanner con = new Scanner(System.in);
        System.out.println("Введите тип транспорта (1 - Самолет; 2 - Поезд; 3 - Автобус; 4 - Автомобиль;" +
                " 5 - Паром; (Иначе - самостоятельное передвижение): ");
        byte userTransportKod = SafeInput.getByte();
        String userTransport = null;
        switch (userTransportKod){
            case 1:
                userTransport= "Самолет";
                break;
            case 2:
                userTransport = "Поезд";
                break;
            case 3:
                userTransport = "Автобус";
                break;
            case 4:
                userTransport = "Автомобиль";
                break;
            case 5:
                userTransport = "Паром";
                break;
            default:userTransport = "Самостоятельное передвижение";
                break;
        }
        return userTransport;
    }
    private static String SelectShip(){
        Scanner con = new Scanner(System.in);
        System.out.println("Выберите тип корабля (1 - Лайнер; 2 - Параход; 3 - Паром; 4 - Яхта;" +
                "(Иначе - Титаник)");
        byte userShipKod = SafeInput.getByte();
        String userShip = null;
        switch (userShipKod){
            case 1:
                userShip= "Лайнер";
                break;
            case 2:
                userShip = "Пароход";
                break;
            case 3:
                userShip = "Паром";
                break;
            case 4:
                userShip = "Яхта";
                break;
            default:userShip = "Титаник";
                break;
        }
        return userShip;
    }
    private static Byte SelectNumberOfDays(){
        System.out.println("Введите количество дней: ");
        return SafeInput.getLimitedByte((byte)30);
    }
    private static Byte SelectNumberOfStars(){
        System.out.println("Введите количество звезд отеля: ");
        return SafeInput.getLimitedByte((byte)5);
    }
    private static Byte SelectNumberOfMeals(){
        System.out.println("Введите количество приемов пищи: ");
        return SafeInput.getLimitedByte((byte)3);
    }
    private static Byte SelectNumberOfProcedures(){
        System.out.println("Введите количество процедур: ");
        return SafeInput.getLimitedByte((byte)5);
    }
    private static Byte SelectNumberOfPeople(){
        System.out.println("Введите количество людей: ");
        return SafeInput.getLimitedByte((byte)10);
    }

    public static Trip AddTrip(){
        System.out.println("Введите тип путевки (1 - Отдых; 2 - Экскурсия; 3 - Шопинг;\n" +
                " 4 - Лечение; 5 - Круиз (Иначе Company special)): ");
        byte userTypeKod = SafeInput.getByte();
        String userType = null;
        Trip t=null;
        switch (userTypeKod) {
            case 1:
                userType = "Отдых";
                t = new Vacation(userType, SelectCountry(), SelectTransport(), SelectNumberOfDays(), SelectNumberOfStars(),
                        SelectNumberOfMeals(), SelectNumberOfPeople());
                break;
            case 2:
                userType = "Экскурсия";
                t = new Excursion(userType, SelectCountry(), SelectTransport(), SelectNumberOfDays(), SelectNumberOfPeople());
                break;
            case 3:
                userType = "Шопинг";
                t = new Shopping(userType, SelectCountry(), SelectTransport(), SelectNumberOfPeople());
                break;
            case 4:
                userType = "Лечение";
                t = new Medical(userType, SelectCountry(), SelectTransport(), SelectNumberOfDays(),SelectNumberOfProcedures(), SelectNumberOfPeople());
                break;
            case 5:
                userType = "Круиз";
                t = new Cruise(userType, SelectCountry(), SelectShip(), SelectNumberOfDays(),SelectNumberOfMeals(), SelectNumberOfPeople());
                break;
            default:userType = "Company special";
                t = new CompanySpecial(userType, SelectCountry(), SelectTransport(), SelectNumberOfDays(), SelectNumberOfStars(),
                        SelectNumberOfMeals(),SelectNumberOfProcedures(), SelectNumberOfPeople());
                break;
        }
        System.out.println("Путевка была создана.");
        return t;
    }
    public String getType(){
        return type;
    }
    public String getCountry(){
        return country;
    }
    public String getTransport(){
        return transport;
    }
    public String getShip(){
        return ship;
    }
    public byte getNumOfDays(){
        return number_of_days;
    }
    public byte getNumOfStars(){
        return number_of_stars;
    }
    public byte getNumOfMeals(){
        return number_of_meals;
    }
    public byte getNumOfProcedures(){
        return number_of_procedures;
    }

    public byte getId() {
        return id;
    }

    public byte getNumOfPeople(){
        return number_of_people;
    }

    public double getPrice() {
        return price;
    }

    static void setNext(byte n){
        _nextId = n;
    }
}
class Vacation extends Trip {
    public Vacation(String Type, String Country, String Transport, byte NODays, byte NOStars, byte NOMeals, byte NOPeople) {
        super();
        this.type = Type;
        this.country = Country;
        this.transport = Transport;
        this.number_of_days = NODays;
        this.number_of_stars = NOStars;
        this.number_of_meals = NOMeals;
        this.number_of_people = NOPeople;
        price = number_of_people * number_of_days *number_of_stars*(5 * number_of_meals + 40);
    }
    public String show() {
        return "Путевка № " + getId()+": "+getType() + ", "  + getCountry() + ", " + getTransport() + ", " + getNumOfDays() + " дней, отель " + getNumOfStars() + " звезд(ы,а), " + getNumOfMeals()+ " прием(ов,a) пищи в день, " +  getNumOfPeople()+ " человек(а), " + getPrice() + "$";
    }
}
class Excursion extends Trip {
    public Excursion(String Type, String Country, String Transport,byte NODays, byte NOPeople) {
        super();
        this.type = Type;
        this.country = Country;
        this.transport = Transport;
        this.number_of_days = NODays;
        this.number_of_people = NOPeople;
        price = number_of_people* number_of_days * 25;
    }
    public String show() {
        return "Путевка № " + getId()+": "+getType() + ", "  + getCountry() + ", " + getTransport() + ", " + getNumOfDays() + " дней/день/дня, " +  getNumOfPeople()+ " человек(а), " + getPrice() + "$";
    }
}
class Shopping extends Trip {
    public Shopping(String Type, String Country, String Transport, byte NOPeople) {
        super();
        this.type = Type;
        this.country = Country;
        this.transport = Transport;
        this.number_of_days=1;
        this.number_of_people = NOPeople;
        price = number_of_people * 20;
    }
    public String show() {
        return "Путевка № " + getId()+": "+getType() + ", "  + getCountry() + ", " + getTransport() + ", " +  getNumOfPeople()+ " человек(а), " + getPrice() + "$";
    }
}
class Medical extends Trip {
    public Medical(String Type, String Country, String Transport, byte NODays, byte NOProcedures, byte NOPeople) {
        super();
        this.type = Type;
        this.country = Country;
        this.transport = Transport;
        this.number_of_days = NODays;
        this.number_of_people = NOPeople;
        this.number_of_meals=3;
        this.number_of_procedures = NOProcedures;
        price = number_of_people * number_of_procedures * number_of_days * 50;
    }
    public String show() {
        return "Путевка № " + getId()+": "+ getType() + ", "  + getCountry() + ", " + getTransport() + ", " + getNumOfDays() + " дней/день/дня, " + getNumOfPeople()+ " человек(а), " + getNumOfProcedures() +" процедур(ы,а), " + getPrice() + "$";
    }
}
class Cruise extends Trip {
    public Cruise(String type, String country, String ship, byte NODays, byte NOMeals, byte NOPeople) {
        super();
        this.type = type;
        this.country = country;
        this.ship = ship;
        this.number_of_days = NODays;
        this.number_of_meals = NOMeals;
        this.number_of_people = NOPeople;
        price = number_of_people * number_of_days* (45+10*number_of_meals);
    }

    public String show() {
        return "Путевка № " + getId()+": "+getType() + ", "  + getCountry() + ", " + getShip() + ", " + getNumOfDays() + " дней/день/дня, " + getNumOfMeals()+ " прием(ов,а) пищи в день, " +  getNumOfPeople()+ " человек(а), " + getPrice() + "$";
    }
}
class CompanySpecial extends Trip {
    public CompanySpecial(String Type, String Country, String Transport, byte NODays, byte NOStars, byte NOMeals,byte NOProcedures, byte NOPeople) {
        super();
        this.type = Type;
        this.country = Country;
        this.transport = Transport;
        this.number_of_days=1;
        this.number_of_stars = NOStars;
        this.number_of_meals = NOMeals;
        this.number_of_procedures = NOProcedures;
        this.number_of_people = NOPeople;
        price = number_of_people * number_of_stars *(5 * number_of_meals +60+15*number_of_procedures);
    }
    public String show() {
        return "Путевка № " + getId()+": "+getType() + ", "  + getCountry() + ", " + getTransport() + ", " + getNumOfDays() + " дней/день/дня,отель " + getNumOfStars() + " звезд(ы,а), " + getNumOfMeals()+ " прием(ов,a) пищи в день, " + getNumOfProcedures() +" процедур(ы,а), " +  getNumOfPeople()+ " человек(а), " + getPrice() + "$";
    }
}