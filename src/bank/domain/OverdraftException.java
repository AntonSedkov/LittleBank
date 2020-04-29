package bank.domain;

public class OverdraftException extends Exception {
    private double deficit;

    public double getDeficit() {
        return deficit;
    }

    public OverdraftException(double deficit, String message) {
        super(message);
        this.deficit = deficit;
    }
}
