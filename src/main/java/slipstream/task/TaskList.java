package slipstream.task;

import java.util.ArrayList;

import slipstream.SlipstreamException;
import slipstream.storage.Storage;
import slipstream.ui.UiMessages;

/**
 * The {@code TaskList} class represents a list of tasks.
 * It contains operations to add, delete, mark, and list tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a {@code TaskList} instance with an empty list of tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a {@code TaskList} instance with the specified list of tasks.
     *
     * @param tasks The list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the list of tasks used by the UI.
     *
     * @return The list of tasks.
     */
    public String listTasks(UiMessages ui) {
        return ui.showTaskList(tasks);
    }

    /**
     * Marks a task as done in the list of tasks.
     * The task is identified by its index in the list.
     * The updated list of tasks is saved to the storage file.
     *
     * @param indexStr The index of the task to mark as done.
     * @param storage  The storage object used to save the updated list of tasks.
     * @param ui       The UI object used to display messages.
     * @throws SlipstreamException If the task number is invalid or the input is not a number.
     */

    public String markTask(String indexStr, Storage storage, UiMessages ui) throws SlipstreamException {
        try {
            int index = Integer.parseInt(indexStr) - 1;
            assert index >= 0 && index < tasks.size() : new SlipstreamException(
                index < 0 ? "The task number must be positive!" : "The task number is too large! "
                    + "You only have " + tasks.size() + " tasks in your list."
            );
            tasks.get(index).markAsDone();
            storage.saveTasks(tasks);
            return ui.showTaskMarked(tasks.get(index));
        } catch (NumberFormatException e) {
            throw new SlipstreamException("Please enter a valid task number!");
        }
    }

    /**
     * Unmarks a task as done in the list of tasks.
     * The task is identified by its index in the list.
     * The updated list of tasks is saved to the storage file.
     *
     * @param indexStr The index of the task to unmark.
     * @param storage  The storage object used to save the updated list of tasks.
     * @param ui       The UI object used to display messages.
     * @throws SlipstreamException If the task number is invalid or the input is not a number.
     */
    public String unmarkTask(String indexStr, Storage storage, UiMessages ui) throws SlipstreamException {
        try {
            int index = Integer.parseInt(indexStr) - 1;
            assert index >= 0 && index < tasks.size() : new SlipstreamException(
                index < 0 ? "The task number must be positive!" : "The task number is too large! "
                    + "You only have " + tasks.size() + " tasks in your list."
            );
            tasks.get(index).markAsNotDone();
            storage.saveTasks(tasks);
            return ui.showTaskUnmarked(tasks.get(index));
        } catch (NumberFormatException e) {
            throw new SlipstreamException("Please enter a valid task number!");
        }
    }

    /**
     * Adds a todo task to the list of tasks.
     * The task description is specified by the user.
     * The updated list of tasks is saved to the storage file.
     *
     * @param description The description of the todo task.
     * @param storage     The storage object used to save the updated list of tasks.
     * @param ui          The UI object used to display messages.
     * @throws SlipstreamException If the task description is empty.
     */
    public String addToDo(String description, Storage storage, UiMessages ui) throws SlipstreamException {
        if (description.trim().isEmpty()) {
            throw new SlipstreamException("The description of your todo task can't be empty!");
        }
        ToDo newTask = new ToDo(description);
        tasks.add(newTask);
        storage.saveTasks(tasks);
        return ui.showTaskAdded(newTask, tasks.size());
    }

    /**
     * Adds a deadline task to the list of tasks.
     * The task description and deadline are specified by the user.
     * The updated list of tasks is saved to the storage file.
     *
     * @param input   The description and deadline of the deadline task.
     * @param storage The storage object used to save the updated list of tasks.
     * @param ui      The UI object used to display messages.
     * @throws SlipstreamException If the task description or deadline is empty.
     */
    public String addDeadline(String input, Storage storage, UiMessages ui) throws SlipstreamException {
        if (!input.contains(" /by ")) {
            throw new SlipstreamException("Your deadline task needs a deadline! (use /by)");
        }
        String[] parts = input.split(" /by ", 2);
        Deadline newTask = new Deadline(parts[0], parts[1]);
        tasks.add(newTask);
        storage.saveTasks(tasks);
        return ui.showTaskAdded(newTask, tasks.size());
    }

    /**
     * Adds an event task to the list of tasks.
     * The task description and time frame are specified by the user.
     * The updated list of tasks is saved to the storage file.
     *
     * @param input   The description and time frame of the event task.
     * @param storage The storage object used to save the updated list of tasks.
     * @param ui      The UI object used to display messages.
     * @throws SlipstreamException If the task description or time frame is empty.
     */
    public String addEvent(String input, Storage storage, UiMessages ui) throws SlipstreamException {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            throw new SlipstreamException(
                "Your event task needs a time frame! (use /from and /to) after your description");
        }
        String[] eventDetails = input.split(" /from | /to ", 3);
        Event newTask = new Event(eventDetails[0], eventDetails[1], eventDetails[2]);
        tasks.add(newTask);
        storage.saveTasks(tasks);
        return ui.showTaskAdded(newTask, tasks.size());
    }

    /**
     * Deletes a task from the list of tasks.
     * The task is identified by its index in the list.
     * The updated list of tasks is saved to the storage file.
     *
     * @param indexStr The index of the task to delete.
     * @param storage  The storage object used to save the updated list of tasks.
     * @param ui       The UI object used to display messages.
     * @throws SlipstreamException If the task number is invalid or the input is not a number.
     */
    public String deleteTask(String indexStr, Storage storage, UiMessages ui) throws SlipstreamException {
        try {
            int index = Integer.parseInt(indexStr) - 1;
            assert index >= 0 && index < tasks.size() : new SlipstreamException(
                index < 0 ? "The task number must be positive!" : "The task number is too large! "
                    + "You only have " + tasks.size() + " tasks in your list."
            );
            Task removedTask = tasks.remove(index);
            storage.saveTasks(tasks);
            return ui.showTaskDeleted(removedTask, tasks.size());
        } catch (NumberFormatException e) {
            throw new SlipstreamException("Please enter a valid task number!");
        }
    }

    /**
     * Clears all tasks from the list of tasks.
     * The updated empty list of tasks is saved to the storage file.
     *
     * @param storage The storage object used to save the updated list of tasks.
     * @param ui      The UI object used to display messages.
     */
    public String clearTasks(Storage storage, UiMessages ui) {
        tasks.clear();
        storage.saveTasks(tasks);
        return ui.showTasksCleared();
    }

    /**
     * Finds tasks that contain the keyword in their description.
     *
     * @param keyword The keyword to search for.
     * @return An ArrayList of tasks that contain the keyword.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

}
