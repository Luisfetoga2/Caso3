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

    public MineTask(int id, String algorithm, String data, int zeros, boolean firstHalf, boolean secondHalf) {
        this.id = id;
        this.algorithm = algorithm;
        this.data = data;
        this.zeros = zeros;

        if (firstHalf) {
            this.start = "a";
            if (secondHalf) {
                this.end = "zzzzzzz";
            } else {
                this.end = "naaaaaa";
            }
        } else {
            this.start = "naaaaaa";
            this.end = "zzzzzzz";
        }
    }

    @Override
    public void run() {
        String v = start;
        String input;

        Boolean completed = false;

        long startTime = System.currentTimeMillis();

        while (!completed) {

            input = data + v;
            String hash = calculateHash(input, algorithm);
            if (startsWithZeros(hash, zeros)) {
                completed = true;
            } 
            else if (v.equals(end)) {
                completed = true;
            } else {
                v = nextString(v);
            }
        }

        if (!v.equals(end)) {
            long endTime = System.currentTimeMillis();
            System.out.println("Thread: " + this.id + "\n" +
                               "Time: " + (endTime - startTime) + "ms" + "\n" +
                               "Hash: " + calculateHash(data + v, algorithm) + "\n" +
                               "Input: " + data + v+ "\n" +
                               "V: " + v);
        } else {
            System.out.println("Thread " + this.id + ": no se encontró resultado.");
        }
    }

    private String nextString(String v) {
        char[] charArray = v.toCharArray();
        int i = charArray.length - 1;
        // Incrementa el último carácter o cambia 'z' por 'a' y propaga si es necesario
        while (i >= 0) {
            if (charArray[i] == 'z') {
                charArray[i] = 'a';
                i--;
            } else {
                charArray[i]++;
                break;
            }
        }
        // Si todos los caracteres eran 'z', agrega un 'a' al principio
        if (i == -1) {
            return "a" + new String(charArray);
        } else {
            return new String(charArray);
        }
    }
    
    private String calculateHash(String input, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    private boolean startsWithZeros(String hash, int zeros) {
        for (int i = 0; i < zeros; i++) {
            if (hash.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }
}


