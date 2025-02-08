package slipstream.parser;

import java.util.ArrayList;

import slipstream.SlipstreamException;
import slipstream.storage.Storage;
import slipstream.task.Task;
import slipstream.task.TaskList;
import slipstream.ui.UiMessages;

/**
 * The {@code Parser} class is responsible for parsing user input and executing the corresponding commands.
 * It processes commands such as adding, deleting, marking tasks as done, and listing tasks.
 * The parser also handles exceptions and displays error messages to the user.
 * Based on the command, the parser calls the appropriate methods in the {@code TaskList} and {@code Ui} classes.
 */
public class Parser {

    /**
     * Processes the user input and executes the corresponding command.
     * The method calls the appropriate methods in the {@code TaskList} and {@code UiMessages} classes
     * based on the command.
     *
     * @param input   The user input to be processed.
     * @param tasks   The list of tasks to be updated based on the command.
     * @param ui      The UI messages handler that returns responses as Strings.
     * @param storage The storage object used to save the updated list of tasks.
     * @return A String response based on the command executed.
     */
    public String processCommand(String input, TaskList tasks, UiMessages ui, Storage storage) {
        String[] commandAndTask = input.split(" ", 2);
        String command = commandAndTask[0];
        String response = "";

        try {
            switch (command) {
            case "bye":
                response = ui.showExitMessage();
                break;
            case "list":
                response = ui.showMessage(tasks.listTasks(ui));
                break;
            case "mark":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("You must specify a task number to mark!");
                }
                response = tasks.markTask(commandAndTask[1], storage, ui);
                break;
            case "unmark":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("You need to specify a task number to unmark!");
                }
                response = tasks.unmarkTask(commandAndTask[1], storage, ui);
                break;
            case "todo":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("The description of your todo task can't be empty!");
                }
                response = tasks.addToDo(commandAndTask[1], storage, ui);
                break;
            case "deadline":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("Your deadline task needs a description and a deadline! (use /by)");
                }
                response = tasks.addDeadline(commandAndTask[1], storage, ui);
                break;
            case "event":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("Your event task needs a description and a time frame! "
                        + "(use /from and /to)");
                }
                if (!commandAndTask[1].contains(" /from ") || !commandAndTask[1].contains(" /to ")) {
                    throw new SlipstreamException("Your event task needs a time frame! (use /from and /to)");
                }
                response = tasks.addEvent(commandAndTask[1], storage, ui);
                break;
            case "delete":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("The delete command needs a task number!");
                }
                response = tasks.deleteTask(commandAndTask[1], storage, ui);
                break;
            case "clear":
                response = tasks.clearTasks(storage, ui);
                break;
            case "find":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("You need to specify a keyword to search!");
                }
                ArrayList<Task> matchingTasks = tasks.findTasks(commandAndTask[1]);
                response = ui.showMatchingTasks(matchingTasks);
                break;
            default:
                throw new SlipstreamException("Sorry, but I don't know what that means :/");
            }
        } catch (Exception e) {
            response = ui.showMessage("An error occurred: " + e.getMessage());
        }

        return response;
    }
}
