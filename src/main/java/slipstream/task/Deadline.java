package slipstream.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import slipstream.SlipstreamException;

public class Deadline extends Task {
    protected LocalDate by;

    // Note that date and time are formatted as "yyyy-MM-dd"
    // Example: "2021-08-26"
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

    @Override
    public String toFileString() {
        return "D | " + (isDone ? 1 : 0) + " | " + description + " | "
            + by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
            + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
