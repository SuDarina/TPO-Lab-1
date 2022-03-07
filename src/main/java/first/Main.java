package first;

public class Main {
    public static void main(String[] args) {
        SinTailor st = new SinTailor(0.01);
        System.out.println(st.sin(Math.PI * 4.1));
        System.out.println(Math.sin(2 * Math.PI));
        System.out.println(Math.sin(-4 * Math.PI));
    }
}
