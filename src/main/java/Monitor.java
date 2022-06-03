public class Monitor {
    public void goWaiting() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void ressurect() {
        notify();
    }
}
