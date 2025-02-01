package slipstream.ui;

import java.util.ArrayList;

import slipstream.task.Task;

/**
 * The {@code Ui} class handles interactions with the user by displaying messages
 * and task-related updates.
 */
public class Ui {

    /**
     * Displays a welcome message when the Slipstream chatbot starts up.
     */
    public void showWelcomeMessage() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Slipstream");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays an exit message when the Slipstream chatbot is closed (using 'bye' command).
     */
    public void showExitMessage() {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays an error message when an invalid command is input.
     */
    public void showLoadingError() {
        System.out.println("No saved tasks found, starting fresh.");
    }

    /**
     * Displays a custom message to the user (used mainly for errors).
     *
     * @param message The message to be displayed.
     */
    public void showMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message to the user when a task is marked as done in list.
     *
     * @param task The task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task.toString());
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message to the user when a task is unmarked in the list.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println(" Nice! I've unmarked this task:");
        System.out.println("   " + task.toString());
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message to the user when a task is added to the list.
     *
     * @param task      The task that was added.
     * @param taskCount The total number of tasks in the list.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task.toString());
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message to the user when a task is deleted.
     *
     * @param task      The task that was deleted.
     * @param taskCount The total number of tasks in the list.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("____________________________________________________________");
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task.toString());
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a list of tasks to the user when the user inputs 'list' command.
     *
     * @param taskList The list of tasks.
     */
    public String showTaskList(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            return "You have no tasks in your list!";
        }
        StringBuilder output = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            output.append("   ").append(i + 1).append(".").append(taskList.get(i).toString()).append("\n");
        }
        return output.toString();
    }

    /**
     * Clears all tasks in the list and displays a message to the user indicating that.
     */
    public void showTasksCleared() {
        System.out.println("____________________________________________________________");
        System.out.println(" Noted. I've cleared all your tasks.");
        System.out.println("____________________________________________________________");
    }

    /**
     * Shows the tasks that match the search query.
     *
     * @param matchingTasks The list of tasks that match the search query.
     */
    public void showMatchingTasks(ArrayList<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            System.out.println("____________________________________________________________");
            System.out.println(" No matching tasks found :(");
            System.out.println("____________________________________________________________");
            return;
        }

        System.out.println("____________________________________________________________");
        System.out.println(" Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + matchingTasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }

}
