import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ShopSystem shopSystem = new ShopSystem();

        while (true) {
            System.out.print("Введите команду (help для списка):");
            String command = scanner.nextLine().trim();
            if (command.equals("exit")) {
                System.out.println("Программа завершена.");
                break;
            }
            shopSystem.processCommand(command);
        }
        scanner.close();
    }
}