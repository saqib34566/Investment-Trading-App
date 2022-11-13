
import java.util.Scanner;

public class MainApp
{

    public static void main(String[] args) {
        MainApp main = new MainApp();
        String answer = main.input("Do you want to start the application? (yes/no)");

        while (!(answer.equalsIgnoreCase("YES") | answer.equalsIgnoreCase("NO"))){
             answer = main.input("Do you want to start the application? (yes/no)");
        }

        if (answer.equalsIgnoreCase("YES")){
            LoginWindow loginWindow = new LoginWindow(); //start GUI app
        } else if (answer.equalsIgnoreCase("NO")){
            System.exit(0);
        } else {
            System.out.println("Please Enter 'yes' or 'no'");
        }

    }

    public String input(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        String input = scanner.nextLine();
        return input;
    }
}
