package slipstream;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SlipstreamTest {

    private final String filePath = "./data/slipstream_test.txt";
    // outContent is used to test the output of the program
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    // originalOut is used to revert the output of the program to the original output stream
    private final PrintStream originalOut = System.out;

    // Needed AI's help to understand how to capture output from System.out.println() and inputs
    // Before each test, set System.out to outContent
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    // Reset System.out to the original output stream
    @AfterEach
    public void restoreStreams() throws IOException {
        System.setOut(originalOut);
        // IMPORTANT: Delete the file after each test to reset task list
        Files.deleteIfExists(Paths.get(filePath));
    }

    @Test
    public void testValidCommand() throws IOException, SlipstreamException {
        // User inputs "list", then "todo read book", then "bye"
        String input = "list\n"
            + "todo read book\n"
            + "bye\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Run Slipstream to get greeting
        new Slipstream("./data/slipstream_test.txt").run();

        // Write in inputs and capture the outputs
        String output = outContent.toString();
        System.out.println(output);

        // Verify expected outputs appear
        assertTrue(output.contains("Hello! I'm Slipstream"), "Welcome message is missing!");
        assertTrue(output.contains("You have no tasks in your list!"), "'list' command did not work");
        assertTrue(output.contains("Got it. I've added this task:"), "'todo' command did not work");
        assertTrue(output.contains("[T][ ] read book"), "Task is not formatted right/did not work");
        assertTrue(output.contains("Bye. Hope to see you again soon!"), "'bye' command did not work");
    }

    @Test
    public void testInvalidCommands() throws IOException, SlipstreamException {
        // User enters various invalid commands
        String input = "randomCommand\n"
            + "todo\n" // Empty todo description
            + "deadline homework\n" // Missing /by
            + "event meeting /from Monday\n" // Missing /to
            + "delete\n" // No task number given to delete
            + "mark\n" // No task number to mark
            + "bye\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Run Slipstream with test file
        new Slipstream("./data/slipstream_test.txt").run();

        // Get captured output
        String output = outContent.toString();
        System.out.println(output); // Debugging: See what is printed

        // Verify that expected error messages appear
        assertTrue(output.contains("Sorry but I don't know what that means :/"), "Unknown command error is missing!");
        assertTrue(output.contains("The description of your todo task can't be empty!"),
            "Empty todo error unsuccessful!");
        assertTrue(output.contains("Your deadline task needs a deadline! (use /by)"), "Deadline error is missing!");
        assertTrue(output.contains("Your event task needs a time frame! (use /from and /to)"),
            "Event error is missing!");
        assertTrue(output.contains("The delete command needs a task number!"), "Delete command error is missing!");
        assertTrue(output.contains("You must specify a task number to mark!"), "Mark command error is missing!");
    }

}
