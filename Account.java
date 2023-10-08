import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Account implements Serializable {
    static final long SerialVersionUID = 1;
    public static final String FILE_ACCOUNTS_NAME = "Accounts.dat";
    protected TripList tripList;
    protected boolean blocked = false;
    protected String login;
    protected String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean getBlocked() {
        return blocked;
    }

    static public Account authorize() {
        Account acc;
        if (!fileExists()) createFirstAdmin();
        acc = showAuthorizationMenu();
        return acc;
    }

    static public boolean fileExists() {
        File f = new File(FILE_ACCOUNTS_NAME);
        return f.exists();
    }

    public static void createFirstAdmin() {
        System.out.println("\nСоздание первого администратора");
        Account acc = null;
        byte kod;
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите логин: ");
        String userLogin = sc.nextLine();
        System.out.println("Введите пароль: ");
        String userPassword = sc.nextLine();
        acc = new Admin(userLogin, userPassword);
        writeToFile(acc);
        System.out.println("\nБыл создан первый админ!");
    }
    static private Account showAuthorizationMenu() {
        Account acc = null;
        AccountList a = new AccountList("");
        String userLogin;
        new AuthorizationMenu("\nПожалуйста, выберите действие:", a).show();
        acc = a.get(0);
        return acc;
    }

    public void showMenu() {

    }

    public static void writeToFile(Account acc) {
        ArrayList<Account> t = new ArrayList<Account>();
        Account a;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_ACCOUNTS_NAME))) {
            do {
                a = (Account) ois.readObject();
                if (a != null)
                    t.add(a);
            } while (a != null);
        } catch (IOException exc) {
            System.out.println("");
        } catch (Exception exc) {
            System.out.println("Возникла непредвиденная ошибка: " + '\t' +
                    exc.getMessage());
            throw new RuntimeException(exc);
        }
        t.add(acc);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_ACCOUNTS_NAME))) {
            for (Account e : t)
                oos.writeObject(e);
            System.out.println(" Файл был обновлен.");
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }

    protected static Account seekAccount(String accLogin) {
        Account acc;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_ACCOUNTS_NAME))) {
            do {
                acc = (Account) ois.readObject();
                if (acc != null) {
                    if (accLogin.toUpperCase().equals(acc.getLogin().toUpperCase())) {
                        return acc;
                    }
                }
            } while (acc != null);
        } catch (IOException exc) {
            System.out.println("");
        } catch (Exception exc) {
            System.out.println("Возникла непредвиденная ошибка: " + '\t' +
                    exc.getMessage());
            throw new RuntimeException(exc);
        }
        return null;
    }

    protected static Account signIn() {
        Account acc;
        String userLogin;
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите логин: ");
        userLogin = sc.nextLine();
        acc = Account.seekAccount(userLogin);
        if (acc == null) {
            System.out.println("\nНет такого логина.\n");
        } else if (acc.blocked) {
            System.out.println("\nВас заблокировали.\n");
            acc = null;
        } else {
            System.out.println("Введите пароль: ");
            String userPassword = sc.nextLine();
            if (!(userPassword.equals(acc.getPassword()))) {
                System.out.println("\nНеправильный пароль\n");
                acc = null;
            }
        }
        return acc;
    }

    protected static Account signUp() {
        Account acc;
        String userLogin;
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите логин: ");
        userLogin = sc.nextLine();
        acc = Account.seekAccount(userLogin);
        if (acc != null) {
            System.out.println("\nТакой логин уже существует.\n");
            acc = null;
        } else {
            System.out.println("Введите пароль:");
            String userPassword = sc.nextLine();
            acc = new User(userLogin, userPassword);
            Account.writeToFile(acc);
        }
        return acc;
    }

    public String show() {
        return "Логин: " + getLogin() + "\tПароль: " + getPassword() + "\tСтатус блокировки: " + getBlocked();
    }

    protected void readTripList() {
        if (tripList == null) tripList = new TripList();
        tripList.readFromFile();
    }

    protected void filterMenu() {
        if (tripList.GetArraylistSize() != 0) {
            TripSorter sorter= new TripSorter();
            new SortFilteredListMenu(" Выберите тип сортировки отфильтрованного списка:", sorter).show();
            TripFilter filter= new TripFilter();
            filter.setSource(tripList);
            filter.setSorter(sorter);
            new FilterMenu("Выберите тип фильтрации путевок",filter,this).show();
        } else System.out.println(" Нет добавленных путевок.");
    }

    protected void sortMenu() {
        if (tripList.GetArraylistSize() != 0) {
            TripSorter sorter= new TripSorter();
            sorter.setSource(tripList);
            new SortMenu(" Пожалуйста, выберите тип сортировки:", sorter).show();
        } else System.out.println(" Нет добавленных путевок.");
    }

    public void payTrips(ArrayList<Trip> list){
        tripList.payForFilteredTrips(list);
    }
}

class Admin extends Account {
    static final long SerialVersionUID = 2;
    public transient AccountList accountList;

    public Admin(String userLogin, String userPassword) {
        login = userLogin;
        password = userPassword;
    }

    protected void accountMenu() {
        new AccountMenu(" Пожалуйста, выберите действие:", this).show();
    }

    public void showMenu() {
        readAccountList();
        readTripList();
        new AdminMenu(" Добро пожаловать в меню администратора.", this).show();
        tripList.writeToFile();
    }

    private void readAccountList() {
        if (accountList == null) accountList = new AccountList(FILE_ACCOUNTS_NAME);
        accountList.loadAccounts();
    }
}

class User extends Account {
    static final long SerialVersionUID = 3;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        blocked = false;
    }

    public void showMenu() {
        readTripList();
        new UserMenu(" Добро пожаловать в меню пользователя.", this).show();
        tripList.writeToFile();
    }
}