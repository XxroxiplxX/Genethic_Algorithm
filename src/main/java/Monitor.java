public class Monitor {
    private int threadCounter;
    public Monitor() {
        threadCounter = 0;
    }
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
    public void incrementThreadCounter() {
        threadCounter++;
    }
    public int getThreadCounter() {
        return threadCounter;
    }
}
