import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el algoritmo que va a usar:");
        String algoritmo = scanner.nextLine();
        System.out.println("Inserte la cadena que va a utilizar: ");
        String cadena = scanner.nextLine();
        System.out.println("Inserte los 0 deseados: ");
        int cerosDeseados = scanner.nextInt();
        System.out.println("Inserte el numero de hilos a ejecutar: ");
        int numThreads = scanner.nextInt();

        MineTask[] tasks = new MineTask[numThreads];
        int searchSpace = 1 << 28; // Espacio de búsqueda dividido en partes iguales

        for (int i = 0; i < numThreads; i++) {
            tasks[i] = new MineTask(algoritmo, cadena, cerosDeseados, searchSpace * i, searchSpace * (i + 1));
            new Thread(tasks[i]).start();
        }
        scanner.close();
        
    }
}
