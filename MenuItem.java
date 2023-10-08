public class MenuItem {
    private String title;
    private Command command;

    MenuItem(Command command, String title){
        this.command = command;
        setTitle(title);
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String show(){
        return title;
    }

    public boolean select(){
        // false - закончить цикл меню
        // true - продолжить цикл меню
        return command.execute();
    }
}