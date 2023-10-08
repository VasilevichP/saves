public class Main {
    public static void main(String[] args) {
        Account account;
        do {
            account = Account.authorize();
            if (!(account == null)) {
                account.showMenu();
            }
        }while (account!=null);
    }
}