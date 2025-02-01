package slipstream.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import slipstream.SlipstreamException;

public class DeadlineTest {

    private final Deadline deadline = new Deadline("Return book", "2025-01-31");

    public DeadlineTest() throws SlipstreamException {
    }

    @Test
    public void correctFormatForDeadline() {
        assertEquals("[D][ ] Return book (by: Jan 31 2025)", deadline.toString());
    }

    @Test
    public void wrongFormatForDeadline() {
        Exception exception = assertThrows(SlipstreamException.class, ()
            -> new Deadline("Clean room", "05-02-2025"));

        assertEquals("Please write your deadline in this format: yyyy-MM-dd, e.g., 2021-02-26",
            exception.getMessage());
    }

    @Test
    public void toFileStringTest() {
        assertEquals("D | 0 | Return book | 2025-01-31", deadline.toFileString());
    }

    @Test
    public void markAsDoneTest() {
        deadline.markAsDone();
        assertEquals("[D][X] Return book (by: Jan 31 2025)", deadline.toString());
    }
}
