package slipstream.ui;

import java.util.ArrayList;

import slipstream.task.Task;


public class Ui {

    public void showWelcomeMessage() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Slipstream");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public void showExitMessage() {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    public void showLoadingError() {
        System.out.println("No saved tasks found, starting fresh.");
    }

    public void showMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }

    public void showTaskMarked(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task.toString());
        System.out.println("____________________________________________________________");
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println(" Nice! I've unmarked this task:");
        System.out.println("   " + task.toString());
        System.out.println("____________________________________________________________");
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task.toString());
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("____________________________________________________________");
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task.toString());
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

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

    public void showTasksCleared() {
        System.out.println("____________________________________________________________");
        System.out.println(" Noted. I've cleared all your tasks.");
        System.out.println("____________________________________________________________");
    }
}
