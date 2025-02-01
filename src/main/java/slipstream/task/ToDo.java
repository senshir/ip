package slipstream.task;

/**
 * The {@code ToDo} class represents a task without any date/time attached to it.
 * It only contains a description and completion status set by the user.
 */
public class ToDo extends Task {
    /**
     * Constructs a {@code ToDo} instance with the specified description.
     *
     * @param description The description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Constructs a {@code ToDo} instance with the specified description and completion status.
     *
     * @param description The description of the task.
     * @param isDone      The completion status of the task.
     */
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns the string representation of the task to be saved in a file.
     *
     * @return The string representation of the task in a file.
     */
    @Override
    public String toFileString() {
        return "T | " + (isDone ? 1 : 0) + " | " + description;
    }

    /**
     * Returns the string representation of the task.
     *
     * @return The string representation of the task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
