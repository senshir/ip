package slipstream;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SlipstreamTest {

    private static final String TEST_FILE_PATH = "./data/slipstream_test.txt";
    private Slipstream slipstream;

    @BeforeEach
    public void createFile() throws SlipstreamException {
        // Create a new test file before the tests
        slipstream = new Slipstream(TEST_FILE_PATH);
    }

    @AfterEach
    public void deleteFile() throws IOException {
        // Delete test file after every test
        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
    }

    @Test
    public void testValidCommands() {
        // Test list command on empty task list
        String response = slipstream.getResponse("list");
        System.out.println(response);
        assertTrue(response.contains("You have no tasks in your list!"), "'list' command failed!");

        // Test adding a todo
        response = slipstream.getResponse("todo read book");
        assertTrue(response.contains("Got it. I've added this task:"), "'todo' command failed!");
        assertTrue(response.contains("[T][ ] read book"), "Task formatting is incorrect!");

        // Test adding a deadline
        response = slipstream.getResponse("deadline homework /by 2024-03-01");
        assertTrue(response.contains("[D][ ] homework (by: Mar 01 2024)"), "'deadline' command failed!");

        // Test adding an event
        response = slipstream.getResponse("event meeting /from Monday /to Wednesday");
        assertTrue(response.contains("[E][ ] meeting (from: Monday to: Wednesday)"), "'event' command failed!");
    }

    @Test
    public void testInvalidCommands() {
        // Test an invalid command
        String response = slipstream.getResponse("lol");
        assertTrue(response.contains("Sorry, but I don't know what that means :/"), "Unknown command handling failed!");

        // Test empty todo description
        response = slipstream.getResponse("todo");
        assertTrue(response.contains("The description of your todo task can't be empty!"),
            "'todo' error handling failed!");

        // Test missing deadline date
        response = slipstream.getResponse("deadline homework");
        assertTrue(response.contains("Your deadline task needs a deadline! (use /by)"),
            "'deadline' error handling failed!");

        // Test missing event timeframe
        response = slipstream.getResponse("event meeting /from Monday");
        assertTrue(response.contains("Your event task needs a time frame! (use /from and /to)"),
            "'event' error handling failed!");
    }

    @Test
    public void testRescheduleCommand() {
        // Add a deadline task first
        slipstream.getResponse("deadline project /by 2024-02-20");

        // Reschedule the task
        String response = slipstream.getResponse("reschedule 1 2024-03-01");
        assertTrue(response.contains("Got it! I've rescheduled this task:"), "'reschedule' command failed!");
    }
}
