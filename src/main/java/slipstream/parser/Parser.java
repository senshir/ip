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
        String response;
        try {
            response = handleCommand(command, commandAndTask, tasks, ui, storage);
        } catch (Exception e) {
            response = ui.showMessage("An error occurred: " + e.getMessage());
        }

        return response;
    }

    /**
     * Handles the user command and calls the appropriate methods
     * in the {@code TaskList} and {@code UiMessages} classes.
     * @param command The command to be executed.
     * @param commandAndTask The user input split into the command and the task description.
     * @param tasks The list of tasks to be updated based on the command.
     * @param ui The UI messages handler that returns responses as Strings.
     * @param storage The storage object used to save the updated list of tasks.
     * @return A String response based on the command executed.
     * @throws SlipstreamException If an error occurs during command execution
     */
    private String handleCommand(String command, String[] commandAndTask, TaskList tasks,
                                 UiMessages ui, Storage storage)
            throws SlipstreamException {
        switch (command) {
        case "bye":
            return ui.showExitMessage();
        case "list":
            return ui.showMessage(tasks.listTasks(ui));
        case "mark":
            return handleMarkCommand(commandAndTask, tasks, storage, ui);
        case "unmark":
            return handleUnmarkCommand(commandAndTask, tasks, storage, ui);
        case "todo":
            return handleTodoCommand(commandAndTask, tasks, storage, ui);
        case "deadline":
            return handleDeadlineCommand(commandAndTask, tasks, storage, ui);
        case "event":
            return handleEventCommand(commandAndTask, tasks, storage, ui);
        case "reschedule":
            return handleRescheduleCommand(commandAndTask, tasks, storage, ui);
        case "delete":
            return handleDeleteCommand(commandAndTask, tasks, storage, ui);
        case "clear":
            return tasks.clearTasks(storage, ui);
        case "find":
            return handleFindCommand(commandAndTask, tasks, ui);
        default:
            throw new SlipstreamException("Sorry, but I don't know what that means :/");
        }
    }

    private void validateCommandInput(String[] commandAndTask, String errorMessage) throws SlipstreamException {
        if (commandAndTask.length < 2) {
            throw new SlipstreamException(errorMessage);
        }
    }

    private String handleMarkCommand(String[] commandAndTask, TaskList tasks, Storage storage, UiMessages ui)
            throws SlipstreamException {
        validateCommandInput(commandAndTask, "You must specify a task number to mark!");
        return tasks.markTask(commandAndTask[1], storage, ui);
    }

    private String handleUnmarkCommand(String[] commandAndTask, TaskList tasks, Storage storage, UiMessages ui)
            throws SlipstreamException {
        validateCommandInput(commandAndTask, "You must specify a task number to mark!");
        return tasks.unmarkTask(commandAndTask[1], storage, ui);
    }

    private String handleTodoCommand(String[] commandAndTask, TaskList tasks, Storage storage, UiMessages ui)
            throws SlipstreamException {
        validateCommandInput(commandAndTask, "The description of your todo task can't be empty!");
        return tasks.addToDo(commandAndTask[1], storage, ui);
    }

    private String handleDeadlineCommand(String[] commandAndTask, TaskList tasks, Storage storage, UiMessages ui)
            throws SlipstreamException {
        validateCommandInput(commandAndTask, "Your deadline task needs a description and a deadline! (use /by)");
        return tasks.addDeadline(commandAndTask[1], storage, ui);
    }

    private String handleEventCommand(String[] commandAndTask, TaskList tasks, Storage storage, UiMessages ui)
            throws SlipstreamException {
        validateCommandInput(commandAndTask,
            "Your event task needs a description and a time frame! (use /from and /to)");
        if (!commandAndTask[1].contains(" /from ") || !commandAndTask[1].contains(" /to ")) {
            throw new SlipstreamException("Your event task needs a time frame! (use /from and /to)");
        }
        return tasks.addEvent(commandAndTask[1], storage, ui);
    }

    private String handleRescheduleCommand(String[] commandAndTask, TaskList tasks, Storage storage, UiMessages ui)
            throws SlipstreamException {
        validateCommandInput(commandAndTask, "The reschedule command needs a task number!");
        return tasks.rescheduleTask(commandAndTask[1], storage, ui);
    }

    private String handleDeleteCommand(String[] commandAndTask, TaskList tasks, Storage storage, UiMessages ui)
            throws SlipstreamException {
        validateCommandInput(commandAndTask, "The delete command needs a task number!");
        return tasks.deleteTask(commandAndTask[1], storage, ui);
    }

    private String handleFindCommand(String[] commandAndTask, TaskList tasks, UiMessages ui)
            throws SlipstreamException {
        validateCommandInput(commandAndTask, "You need to specify a keyword to search!");
        ArrayList<Task> matchingTasks = tasks.findTasks(commandAndTask[1]);
        return ui.showMatchingTasks(matchingTasks);
    }
}
