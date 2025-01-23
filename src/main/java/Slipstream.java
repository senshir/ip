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
        ArrayList<Task> taskList = new ArrayList<>(100);

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
                        System.out.println("   " + (i + 1) + "." + taskList.get(i).toString());
                    }
                    System.out.println("____________________________________________________________");
                    break;

                case "mark":
                    // Find the integer in the string and minus 1 to get the index
                    int taskIndex1 = Integer.parseInt(commandAndTask[1]) - 1;
                    taskList.get(taskIndex1).markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + taskList.get(taskIndex1).toString());
                    System.out.println("____________________________________________________________");
                    break;
                case "unmark":
                    // Find the integer in the string and minus 1 to get the index
                    int taskIndex2 = Integer.parseInt(commandAndTask[1]) - 1;
                    taskList.get(taskIndex2).markAsNotDone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + taskList.get(taskIndex2).toString());
                    System.out.println("____________________________________________________________");
                    break;

                case "todo":
                    // Add a new ToDo task
                    taskList.add(new ToDo(commandAndTask[1]));
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + taskList.get(taskList.size() - 1).toString());
                    System.out.println(" Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    break;

                case "deadline":
                    // Split the input into description and by
                    String[] deadlineCommand = commandAndTask[1].split(" /by ");
                    taskList.add(new Deadline(deadlineCommand[0], deadlineCommand[1]));
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + taskList.get(taskList.size() - 1).toString());
                    System.out.println(" Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    break;

                case "event":
                    // Split the input into description and at
                    String[] eventDetails = commandAndTask[1].split(" /from | /to ");
                    String eventDescription = eventDetails[0];
                    String eventFrom = eventDetails[1];
                    String eventTo = eventDetails[2];
                    taskList.add(new Event(eventDescription, eventFrom, eventTo));
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + taskList.get(taskList.size() - 1).toString());
                    System.out.println(" Now you have " + taskList.size() + " tasks in the list.");
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