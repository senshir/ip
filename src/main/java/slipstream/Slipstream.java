package slipstream;

import java.io.IOException;

import slipstream.parser.Parser;
import slipstream.storage.Storage;
import slipstream.task.TaskList;
import slipstream.ui.UiMessages;

/**
 * The {@code Slipstream} class represents a chatbot that helps users manage their tasks.
 * Users can add, delete, mark tasks as done, and search for tasks by keywords.
 * The chatbot automatically saves tasks to a file and loads them upon startup.
 */
public class Slipstream {

    private final UiMessages ui;
    private TaskList taskList;
    private final Storage storage;
    private final Parser parser;

    /**
     * Constructs a {@code Slipstream} instance, initializing UI, storage, and task management.
     * Loads tasks from a text file if it exists, otherwise creates a new task list.
     * If an error occurs while loading tasks, an IOException is caught and an error message is displayed.
     *
     * @param filePath The text file path where tasks are stored.
     * @throws SlipstreamException If an error occurs while processing tasks.
     */
    public Slipstream(String filePath) throws SlipstreamException {
        ui = new UiMessages();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            taskList = new TaskList(storage.loadTasks());
        } catch (IOException e) {
            ui.showLoadingError();
            taskList = new TaskList();
        }
    }

    /**
     * Processes user input and returns a response.
     *
     * @param input User command.
     * @return Bot response.
     */
    public String getResponse(String input) {
        return parser.processCommand(input, taskList, ui, storage);
    }

}
