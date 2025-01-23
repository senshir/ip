import java.util.Scanner;

public class Slipstream {
    public static void main(String[] args) {
        String logo = "____________________________________________________________\n"
                + " Hello! I'm Slipstream\n"
                + " What can I do for you?\n"
                + "____________________________________________________________\n"
                + " Bye. Hope to see you again soon!\n"
                + "____________________________________________________________";
        System.out.println(logo);

        // Initialise a scanner to read user's input
        Scanner sc = new Scanner(System.in);

        while (true) {
            // Read the user's input
            String input = sc.nextLine();

            // If user wants to exit using 'bye', exit the program
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________\n"
                        + " Bye. Hope to see you again soon!\n"
                        + "____________________________________________________________");
                break;
            } else {
                // Repeat the user's input
                System.out.println("____________________________________________________________\n"
                        + " " + input + "\n"
                        + "____________________________________________________________");
            }
        }
    }
}