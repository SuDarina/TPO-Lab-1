package first;

public class SinTailor {

    private final double fault;

    public SinTailor(double fault) {
        this.fault = fault;
    }

    public double getFault() {
        return fault;
    }

    private double sinTailor(double x, int n){
        double result = 0;
        for (int i = 1; i <= n; i++) {
            result += Math.pow(-1, i - 1) * (Math.pow(x, 2 * i - 1) / getFactorial(2 * i - 1));
        }
        return result;
    }

    public double sin(double x) {
        double previous = 0;
        double current = 2;
        int n = 0;

        x = Math.abs(x) >= Math.PI * 2 ? (x - (Math.PI * 2 * (int) (x / (Math.PI * 2)))) : x;

        while(Math.abs(current - previous) > this.getFault()){
            previous = current;
            current = sinTailor(x, ++n);
        }
        return current;
    }

    public long getFactorial(int n) {
        if (n <= 2) {
            return n;
        }
        return n * getFactorial(n - 1);
    }

}
