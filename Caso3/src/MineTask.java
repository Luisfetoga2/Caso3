import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MineTask implements Runnable {
        private final String algorithm;
        private final String data;
        private final int zeros;
        private final int start;
        private final int end;

        public MineTask(String algorithm, String data, int zeros, int start, int end) {
            this.algorithm = algorithm;
            this.data = data;
            this.zeros = zeros;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int v = start; v < end; v++) {
                String input = data + v;
                String hash = calculateHash(input, algorithm);
                if (startsWithZeros(hash, zeros)) {
                    System.out.println("Valor encontrado: " + v);
                    return;
                }
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


