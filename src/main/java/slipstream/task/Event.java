package slipstream.task;

/**
 * The {@code Event} class represents a task that starts and ends at specific times.
 * It contains a description, start time, and end time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs an {@code Event} instance with the specified description, start time, and end time.
     *
     * @param description The description of the event.
     * @param from        The start time of the event.
     * @param to          The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs an {@code Event} instance with the specified description, completion status, start time, and end time.
     *
     * @param description The description of the event.
     * @param isDone      The completion status of the event.
     * @param from        The start time of the event.
     * @param to          The end time of the event.
     */
    public Event(String description, boolean isDone, String from, String to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the string representation of the event to be saved in a file.
     *
     * @return The string representation of the event in a file.
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? 1 : 0) + " | " + description + " | " + from + " | " + to;
    }

    /**
     * Returns the string representation of the event.
     *
     * @return The string representation of the event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
