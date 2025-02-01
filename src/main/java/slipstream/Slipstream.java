package slipstream;

import java.io.IOException;
import java.util.Scanner;

import slipstream.parser.Parser;
import slipstream.storage.Storage;
import slipstream.task.TaskList;
import slipstream.ui.Ui;

/**
 * The {@code Slipstream} class represents a chatbot that helps users manage their tasks.
 * Users can add, delete, mark tasks as done, and search for tasks by keywords.
 * The chatbot automatically saves tasks to a file and loads them upon startup.
 */
public class Slipstream {

    private final Ui ui;
    private TaskList taskList;
    private final Storage storage;
    private final Parser parser;

    /**
     * Constructs a {@code Slipstream} instance, initializing UI, storage, and task management.
     *
     * @param filePath The text file path where tasks are stored.
     * @throws IOException         If there is an error reading from the storage file.
     * @throws SlipstreamException If an error occurs while processing tasks.
     */
    public Slipstream(String filePath) throws IOException, SlipstreamException {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadTasks());
        parser = new Parser();
        try {
            taskList = new TaskList(storage.loadTasks());
        } catch (IOException e) {
            ui.showLoadingError();
            taskList = new TaskList();
        }
    }
    /**
     * * Runs the chatbot, continuously processing user input until the exit command is given.
     */
    public void run() {
        ui.showWelcomeMessage();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            String userInput = scanner.nextLine();
            isRunning = parser.processCommand(userInput, taskList, ui, storage);
        }

        scanner.close();
    }
    /**
     * The main entry point of the program. Initialises and starts the Slipstream chatbot.
     *
     * @param args Command-line arguments (not used in this case).
     * @throws IOException If there is an issue reading from the storage file.
     * @throws SlipstreamException If an error occurs during initialization/processing tasks.
     */
    public static void main(String[] args) throws IOException, SlipstreamException {
        new Slipstream("./data/slipstream.txt").run();
    }

}
