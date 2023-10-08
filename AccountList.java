import java.io.*;
import java.util.*;

public class AccountList {
    private transient ArrayList<Account> accounts = new ArrayList<>();
    private String FILE_ACCOUNTS_NAME; // = "Accounts.dat";
    public AccountList(String fname){
        FILE_ACCOUNTS_NAME = fname;
    }
    public int add(Account a){
        accounts.add(a);
        return accounts.size();
    }
    public int size() {
        return accounts.size();
    }

    public Account get(int x){
        if (x>=0 && x<size()){
            return accounts.get(x);
        } else
            return null;
    }
    public void showAccounts() {
        if (accounts.size() != 0) {
            int i = 1;
            for (Account a : accounts) {
                System.out.println("№:" + (i++) + '\t' + a.show());
            }
        } else System.out.println("Нет учетных записей.");
    }
    public void showBlockedAccounts(){
        if (accounts.size() != 0) {
            int i = 1;
            for (Account a : accounts) {
                if(a.blocked){
                System.out.println("№:" + (i++) + "    Логин: " + a.getLogin() + " \tПароль: " + a.getPassword());
                }
            }
        } else System.out.println("Нет учетных записей.");
    }

    private static <T> void RemoveAccount(ArrayList<T> list, int index) {
        list.remove(index);
    }
    public void deleteAccount(){
        if (size() != 0) {
            byte continueChoose;
            do {
                showAccounts();
                System.out.println(" Пожалуйста, выберите номер аккаунта, который вы хотите удалить: \n");
                byte ChooseKod = SafeInput.getByte();
                deleteAccount(ChooseKod - 1);
                System.out.println(" Хотите продолжить удалять аккаунты? (Нажмите 1 для продолжения) \n");
                continueChoose = SafeInput.getByte();
            } while (continueChoose == 1);
        } else System.out.println(" Нет аккаунтов.");
    }
    public void deleteAccount(int index) {
        Account acc;
        if (index >= 0 && index < accounts.size()) {
            acc=accounts.get(index);
            if(acc instanceof Admin){
                System.out.println("Вы не можете удалить администратора");
            }else {
                System.out.println("Вы хотите удалить этот аккаунт? (y/n)?");
                Scanner con = new Scanner(System.in);
                String choice = con.nextLine();
                if (choice.equals("y") || choice.equals("Y")) {
                    System.out.println("Аккаунт был удален.");
                    RemoveAccount(accounts, index);
                    saveAccounts();
                } else System.out.println("\nЛадно.");
            }
        } else System.out.println("\nНеправильное число.");
    }
    public void changeBlockAccount(){
        if (size() != 0) {
            byte continueChoose;
            do {
                showAccounts();
                System.out.println(" Пожалуйста, выберите номер аккаунта, который вы хотите (раз)заблокировать: \n");
                byte ChooseKod = SafeInput.getByte();
                changeBlockAccount(ChooseKod - 1);
                System.out.println(" Хотите продолжить изменять статус блокировки аккаунтов? (Нажмите 1 для продолжения) \n");
                continueChoose = SafeInput.getByte();
            } while (continueChoose == 1);
        } else System.out.println(" Нет аккаунтов.");
    }

    public void changeBlockAccount(int index) {
        Account acc;
        if (index >= 0 && index < accounts.size()) {
            acc=accounts.get(index);
            if(acc instanceof Admin){
                System.out.println("Вы не можете заблокировать администратора");
            }else {
                System.out.println(acc.show());
                if (acc.blocked) {
                    System.out.println("Вы хотите разблокировать этот аккаунт? (y/n)?");
                } else System.out.println("Вы хотите заблокировать этот аккаунт? (y/n)?\"");
                Scanner con = new Scanner(System.in);
                String choice = con.nextLine();
                if (choice.equals("y") || choice.equals("Y")) {
                    if (!acc.blocked) {
                        accounts.get(index).blocked = true;
                    } else accounts.get(index).blocked = false;
                    System.out.println("Статус блокировки " + acc.getLogin() + " был изменен");
                    saveAccounts();
                } else System.out.println("\nЛадно.");
            }
        } else System.out.println("\nНеправильное число.");
    }
    public void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_ACCOUNTS_NAME))) {
            for(Account a:accounts)
                oos.writeObject(a);
            System.out.println("\nФайл аккаунтов был обновлен.");
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }
    public void loadAccounts() {
        Account a;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_ACCOUNTS_NAME))) {
            do {
                a = (Account) ois.readObject();
                if (a != null)
                    accounts.add(a);
            } while (a != null);
        } catch (IOException exc) {
            System.out.println("");
        } catch (Exception exc) {
            System.out.println("Возникла непредвиденная ошибка: " + '\t' +
                    exc.getMessage());
            throw new RuntimeException(exc);
        }
    }
}
