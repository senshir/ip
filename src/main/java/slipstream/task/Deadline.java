package slipstream.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import slipstream.SlipstreamException;

/**
 * The {@code Deadline} class represents a task with a deadline.
 * It contains a description and a deadline date.
 */
public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Constructs a {@code Deadline} instance with the specified description and deadline date.
     * Note that the deadline date must be in the format "yyyy-MM-dd".
     *
     * @param description The description of the deadline.
     * @param by          The deadline date of the deadline.
     * @throws SlipstreamException If the deadline date is not in the correct format.
     */
    public Deadline(String description, String by) throws SlipstreamException {
        super(description);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.by = LocalDate.parse(by, formatter);
        } catch (DateTimeParseException e) {
            throw new SlipstreamException("Please write your deadline in this format: yyyy-MM-dd,"
                + " e.g., 2021-02-26");
        }

    }

    /**
     * Constructs a {@code Deadline} instance with the specified description, completion status, and deadline date.
     * Note that the deadline date must be in the format "yyyy-MM-dd".
     *
     * @param description The description of the deadline.
     * @param isDone      The completion status of the deadline.
     * @param by          The deadline date of the deadline.
     * @throws SlipstreamException If the deadline date is not in the correct format.
     */
    public Deadline(String description, boolean isDone, String by) throws SlipstreamException {
        super(description, isDone);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.by = LocalDate.parse(by, formatter);
        } catch (DateTimeParseException e) {
            throw new SlipstreamException("Please write your deadline in this format: yyyy-MM-dd,"
                + " e.g., 2021-02-26");
        }
    }

    /**
     * Returns the deadline date of the deadline.
     *
     */
    public void setDeadline(String newDeadline) throws SlipstreamException {
        if (newDeadline == null || newDeadline.isBlank()) {
            throw new SlipstreamException("New deadline cannot be empty!");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.by = LocalDate.parse(newDeadline, formatter);
        } catch (DateTimeParseException e) {
            throw new SlipstreamException("Invalid date format! Please use yyyy-MM-dd, e.g., 2024-03-01.");
        }
    }

    /**
     * Returns the string representation of the deadline to be saved in a file.
     *
     * @return The string representation of the deadline in a file.
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? 1 : 0) + " | " + description + " | "
            + by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Returns the string representation of the deadline.
     *
     * @return The string representation of the deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
            + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
