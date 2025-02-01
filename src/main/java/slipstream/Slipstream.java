package slipstream;

import java.io.IOException;
import java.util.Scanner;

import slipstream.parser.Parser;
import slipstream.storage.Storage;
import slipstream.task.TaskList;
import slipstream.ui.Ui;

/*
  * Slipstream is a chatbot that helps users to keep track of their tasks.
  * Users can add, delete, mark tasks as done, and find tasks based on keywords.
  * The tasks are saved in a text file and loaded when the chatbot is started.
  * The chatbot will save the tasks to the text file when the chatbot is closed.
  */
public class Slipstream {

    private final Ui ui;
    private TaskList taskList;
    private final Storage storage;
    private final Parser parser;

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

    public static void main(String[] args) throws IOException, SlipstreamException {
        new Slipstream("./data/slipstream.txt").run();
    }

}
