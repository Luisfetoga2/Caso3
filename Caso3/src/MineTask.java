import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MineTask implements Runnable {

    private int id;
    private final String algorithm;
    private final String data;
    private final int zeros;
    private String start;
    private String end;
    private static boolean found = false;
    private Monitor monitor;
    private MessageDigest md;
    private int i;

    public MineTask(int id, String algorithm, String data, int zeros, boolean firstHalf, boolean secondHalf, Monitor monitor) {
        this.id = id;
        this.algorithm = algorithm;
        this.data = data;
        this.zeros = zeros;
        this.monitor = monitor;
        try {
            this.md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (firstHalf) {
            this.start = "a";
            this.i = 0;
            if (secondHalf) {
                this.end = "zzzzzzz";
            } else {
                this.end = "naaaaaa";
            }
        } else {
            this.start = "naaaaaa";
            this.end = "zzzzzzz";
            this.i = 6;
        }
    }

    @Override
    public void run() {
        String v = start;
        String input;

        long startTime = System.currentTimeMillis();

        while (monitor.getFound() == false) {

            // print every 5 seconds
            if (System.currentTimeMillis() % 5000 == 0) {
                System.out.println("Thread " + this.id + ": " + v);
            }
            
            input = data + v;
            String hash = calculateHash(input);
            if (startsWithZeros(hash, zeros)) {
                long endTime = System.currentTimeMillis();
                // print result
                monitor.printResult(id, startTime, endTime, hash, data, v, zeros);
                return;
            } 
            else if (v.equals(end)) {
                System.out.println("Thread " + this.id + ": no se encontró resultado en su rango de busqueda.");
                return;
            } else {
                v = nextString(v);
            }
        }
        System.out.println("Thread " + this.id + ": detenido.");
    }

    private String nextString(String v) {
        char[] charArray = v.toCharArray();
        
        // Incrementa el último carácter o cambia 'z' por 'a' y propaga si es necesario
        while (i >= 0) {
            if (charArray[i] == 'z') {
                charArray[i] = 'a';
                i--;
            } else {
                charArray[i]++;
                i = v.length() - 1;
                break;
            }
        }
        // Si todos los caracteres eran 'z', agrega un 'a' al principio
        if (i == -1) {
            i = v.length() - 1;
            return "a" + new String(charArray);
        } else {
            return new String(charArray);
        }
    }
    
    private String calculateHash(String input) {
        byte[] hashBytes = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private boolean startsWithZeros(String hash, int zeros) {
        for (int x = 0; x < zeros; x++) {
            if (hash.charAt(x) != '0') {
                return false;
            }
        }
        return true;
    }
}


