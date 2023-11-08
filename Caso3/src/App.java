import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el algoritmo que va a usar: (SHA-256/SHA-512))");
        String algoritmo = scanner.nextLine();
        System.out.println("Inserte la cadena que va a utilizar:");
        String cadena = scanner.nextLine();
        System.out.println("Inserte los 0 deseados:");
        int cerosDeseados = scanner.nextInt();
        System.out.println("Inserte el numero de hilos a ejecutar: (1/2)");
        int numThreads = scanner.nextInt();

        MineTask[] tasks = new MineTask[numThreads];
        Monitor monitor = new Monitor();

        if (numThreads==1) {
            tasks[0] = new MineTask(1, algoritmo, cadena, cerosDeseados, true, true, monitor);
            new Thread(tasks[0]).start();
        } else {
            tasks[0] = new MineTask(1, algoritmo, cadena, cerosDeseados, true, false, monitor);
            tasks[1] = new MineTask(2, algoritmo, cadena, cerosDeseados, false, true, monitor);
            for (int i = 0; i < numThreads; i++) {
                new Thread(tasks[i]).start();
            }
        }
        scanner.close();
        
    }
}
