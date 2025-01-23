import java.util.Scanner;
import java.util.ArrayList;

public class Slipstream {
    public static void main(String[] args) {
        String logo = "____________________________________________________________\n"
                + " Hello! I'm Slipstream\n"
                + " What can I do for you?\n"
                + "____________________________________________________________\n";
        System.out.println(logo);

        // Initialise a scanner to read user's input
        Scanner sc = new Scanner(System.in);

        // Create a list of tasks
        ArrayList<String> tasks = new ArrayList<>();

        while (true) {
            // Read the user's input
            String input = sc.nextLine();

            // If user wants to exit using 'bye', exit the program
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________\n"
                        + " Bye. Hope to see you again soon!\n"
                        + "____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                // Show all the tasks in the list
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                }
                System.out.println("____________________________________________________________");
            } else {
                // Add the user's input to the list
                tasks.add(input);
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + input);
                System.out.println("____________________________________________________________");
            }
        }
    }
}