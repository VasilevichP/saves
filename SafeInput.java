import java.util.InputMismatchException;
import java.util.Scanner;

public class SafeInput {
    public SafeInput() {
    }

    public static byte getByte(){
        Scanner con = new Scanner(System.in);
        byte kod;
        while(true) {
        try{
            kod = con.nextByte();
            return kod;
        }catch (InputMismatchException e){
            con.nextLine();
            System.out.println(" Введено нечисловое значение. Пожалуйста, введите заново.");
        }
        }
    }
    public static byte getLimitedByte(byte max){
        byte kod;
        while(true) {
            try {
                kod = getByte();
                if (kod<=0 || kod>max) throw new MyException(max);
                return kod;
            }catch(MyException e) {
            }
        }
    }
}
