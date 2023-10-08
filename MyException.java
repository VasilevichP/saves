    public class MyException extends Exception{
        public MyException (byte m){
            System.out.println(" Ошибка!Нужно ввести число в диапазоне от 1 до "+ m);
        }
    }
