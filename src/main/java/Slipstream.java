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
        ArrayList<Task> taskList = new ArrayList<>();

        while (true) {
            // Read the user's input
            String input = sc.nextLine();
            String[] commandAndTask = input.split(" ", 2);
            String command = commandAndTask[0];

            // Switch for different commands
            switch (command) {
                case "bye":
                    System.out.println("____________________________________________________________\n"
                            + " Bye. Hope to see you again soon!\n"
                            + "____________________________________________________________");
                    sc.close();
                    return;

                case "list":
                    System.out.println("____________________________________________________________");
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < taskList.size(); i++) {
                        Task task = taskList.get(i);
                        System.out.println("    " + (i + 1) + ".[" + task.getStatusIcon() + "] "
                                                  + task.description);
                    }
                    System.out.println("____________________________________________________________");
                    break;

                case "mark":
                    // Find the integer in the string and minus 1 to get the index
                    int taskIndex1 = Integer.parseInt(commandAndTask[1]) - 1;
                    taskList.get(taskIndex1).markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   [" + taskList.get(taskIndex1).getStatusIcon() + "] "
                                              + taskList.get(taskIndex1).description);
                    System.out.println("____________________________________________________________");
                    break;
                case "unmark":
                    // Find the integer in the string and minus 1 to get the index
                    int taskIndex2 = Integer.parseInt(commandAndTask[1]) - 1;
                    taskList.get(taskIndex2).markAsNotDone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   [" + taskList.get(taskIndex2).getStatusIcon() + "] "
                                              + taskList.get(taskIndex2).description);
                    System.out.println("____________________________________________________________");
                    break;

                default:
                    // Add a new task
                    taskList.add(new Task(input));
                    System.out.println("____________________________________________________________");
                    System.out.println("   added: " + input);
                    System.out.println("____________________________________________________________");

            }
        }
    }
}