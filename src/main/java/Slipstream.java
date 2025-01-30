import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

public class Slipstream {
    public static void main(String[] args) {
        String logo = """
				____________________________________________________________
				 Hello! I'm Slipstream
				 What can I do for you?
				____________________________________________________________
				""";
        System.out.println(logo);
        
        // Initialise variables for saving file
        String filePath = "data/tasks.txt";
        FileSaving fileSaver = new FileSaving(filePath);

        // Initialise a scanner to read user's input
        Scanner sc = new Scanner(System.in);

        // Create a list of tasks
        ArrayList<Task> taskList = new ArrayList<>(100);
        try {
            taskList = fileSaver.loadTasks();
        } catch (IOException | SlipstreamException e) {
            System.out.println("No saved tasks found, starting fresh.");
        }
        
        while (true) {
            // Read the user's input
            String input = sc.nextLine();
            String[] commandAndTask = input.split(" ", 2);
            String command = commandAndTask[0];

            try {
                // Switch for different commands
                switch (command) {
                case "bye":
                    System.out.println("""
                            ____________________________________________________________
                             Bye. Hope to see you again soon!
                            ____________________________________________________________""");
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
                    if (commandAndTask.length < 2) {
                        throw new SlipstreamException("You need to choose which task to mark! (with a number)");
                    }
                    // Find the integer in the string and minus 1 to get the index
                    int taskIndex1 = Integer.parseInt(commandAndTask[1]) - 1;
                    taskList.get(taskIndex1).markAsDone();
                    fileSaver.saveTasks(taskList);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + taskList.get(taskIndex1).toString());
                    System.out.println("____________________________________________________________");
                    break;
                case "unmark":
                    if (commandAndTask.length < 2) {
                        throw new SlipstreamException("You need to choose which task to unmark! (with a number)");
                    }
                    // Find the integer in the string and minus 1 to get the index
                    int taskIndex2 = Integer.parseInt(commandAndTask[1]) - 1;
                    taskList.get(taskIndex2).markAsNotDone();
                    fileSaver.saveTasks(taskList);
                    System.out.println("____________________________________________________________");
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + taskList.get(taskIndex2).toString());
                    System.out.println("____________________________________________________________");
                    break;
                
                case "todo":
                    if (commandAndTask.length < 2 || commandAndTask[1].trim().isEmpty()) {
                        throw new SlipstreamException("The description of your todo task can't be empty!");
                    }
                    taskList.add(new ToDo(commandAndTask[1]));
                    fileSaver.saveTasks(taskList);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + taskList.get(taskList.size() - 1).toString());
                    System.out.println(" Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    break;
                
                case "deadline":
                    if (commandAndTask.length < 2) {
                        throw new SlipstreamException("Your deadline task needs a description and a deadline!");
                    } else if (!commandAndTask[1].contains(" /by ")) {
                        throw new SlipstreamException("Your deadline task needs a deadline! (use /by)");
                    }
                    // Split the input into description and by
                    String[] deadlineCommand = commandAndTask[1].split(" /by ");
                    taskList.add(new Deadline(deadlineCommand[0], deadlineCommand[1]));
                    fileSaver.saveTasks(taskList);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + taskList.get(taskList.size() - 1).toString());
                    System.out.println(" Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    break;
                
                case "event":
                    if (commandAndTask.length < 2) {
                        throw new SlipstreamException("Your event task needs a description and a time frame!" +
                                " (use /from and /to) after your description");
                    } else if (!commandAndTask[1].contains(" /from ") || !commandAndTask[1].contains(" /to ")) {
                        throw new SlipstreamException("Your event task needs a time frame! (use /from and /to)");
                    }
                    // Split the input into description and at
                    String[] eventDetails = commandAndTask[1].split(" /from | /to ");
                    String eventDescription = eventDetails[0];
                    String eventFrom = eventDetails[1];
                    String eventTo = eventDetails[2];
                    taskList.add(new Event(eventDescription, eventFrom, eventTo));
                    fileSaver.saveTasks(taskList);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + taskList.get(taskList.size() - 1).toString());
                    System.out.println(" Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    break;
                
                case "delete":
                    if (commandAndTask.length < 2 || commandAndTask[1].isEmpty()) {
                        throw new SlipstreamException("The delete command needs a task number after itself!");
                    }
                    
                    // Find the integer in the string and minus 1 to get the index
                    int taskIndexToDelete = Integer.parseInt(commandAndTask[1]) - 1;
                    
                    // If the task number is not valid, throw an exception
                    if (taskIndexToDelete < 0) {
                        throw new SlipstreamException("The task number can't be negative!");
                    } else if (taskIndexToDelete >= taskList.size()) {
                        throw new SlipstreamException("The task number is too large! " +
                                "Please choose a number between 1 and " + taskList.size());
                    }
                    
                    // Remove the indexed task from the list
                    Task removedTask = taskList.remove(taskIndexToDelete);
                    fileSaver.saveTasks(taskList);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Noted. I've removed this task:");
                    System.out.println("   " + removedTask.toString());
                    System.out.println(" Now you have " + taskList.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    break;
                    
                case "clear":
                    taskList.clear();
                    fileSaver.saveTasks(taskList);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Noted. I've cleared all your tasks.");
                    System.out.println("____________________________________________________________");
                    break;
                
                default:
                    throw new SlipstreamException("Sorry but I don't know what that means :/");
                }
            } catch (Exception e) {
                System.out.println("____________________________________________________________");
                System.out.println("An unknown issue occurred :( (Likely an issue with arguments of your commands) --> "
                                    + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }
    }
}