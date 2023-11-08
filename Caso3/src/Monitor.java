public class Monitor {

    private boolean found = false;

    public boolean getFound() {
        return found;
    }

    public synchronized void printResult(int id, long startTime,long endTime, String hash, String data, String v, int zeros) {
        if (found) {
            return;
        }
        found = true;
        System.out.println("Thread: " + id + "\n" +
                            "Time: " + (endTime - startTime) + "ms" + "\n" +
                            "Hash: " + hash + "\n" +
                            "Input: " + data + v+ "\n" +
                            "V: " + v);
    }
    
}
