package slipstream.ui;

import java.util.ArrayList;

import slipstream.task.Task;

/**
 * The {@code Ui} class handles interactions with the user by formatting messages
 * for display.
 */
public class UiMessages {

    /**
     * Returns the welcome message when the Slipstream chatbot starts up.
     */
    public String showWelcomeMessage() {
        return "Hello! I'm Slipstream\nWhat can I do for you?";
    }

    /**
     * Returns the exit message.
     */
    public String showExitMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns an error message when an invalid command is input.
     */
    public String showLoadingError() {
        return "No saved tasks found, starting fresh.";
    }

    /**
     * Returns a custom message.
     *
     * @param message The message to be displayed.
     */
    public String showMessage(String message) {
        return message;
    }

    /**
     * Returns a message when a task is marked as done.
     */
    public String showTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n" + task.toString();
    }

    /**
     * Returns a message when a task is unmarked.
     */
    public String showTaskUnmarked(Task task) {
        return "Nice! I've unmarked this task:\n" + task.toString();
    }

    /**
     * Returns a message when a task is added.
     */
    public String showTaskAdded(Task task, int taskCount) {
        return "Got it. I've added this task:\n" + task.toString()
            + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /**
     * Returns a message when a task is deleted.
     */
    public String showTaskDeleted(Task task, int taskCount) {
        return "Noted. I've removed this task:\n" + task.toString()
            + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /**
     * Returns the list of tasks.
     */
    public String showTaskList(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            return "You have no tasks in your list!";
        }
        StringBuilder output = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            output.append(i + 1).append(". ").append(taskList.get(i).toString()).append("\n");
        }
        return output.toString();
    }

    /**
     * Returns a message when tasks are cleared.
     */
    public String showTasksCleared() {
        return "Noted. I've cleared all your tasks.";
    }

    /**
     * Returns the list of matching tasks.
     */
    public String showMatchingTasks(ArrayList<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            return "No matching tasks found :(";
        }
        StringBuilder output = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            output.append(i + 1).append(". ").append(matchingTasks.get(i)).append("\n");
        }
        return output.toString();
    }
}
