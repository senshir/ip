package slipstream.parser;

import java.util.ArrayList;

import slipstream.SlipstreamException;
import slipstream.storage.Storage;
import slipstream.task.Task;
import slipstream.task.TaskList;
import slipstream.ui.Ui;

public class Parser {
    public boolean processCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        String[] commandAndTask = input.split(" ", 2);
        String command = commandAndTask[0];

        try {
            switch (command) {
            case "bye":
                ui.showExitMessage();
                return false;
            case "list":
                ui.showMessage(tasks.listTasks(ui));
                break;
            case "mark":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("You must specify a task number to mark!");
                }
                tasks.markTask(commandAndTask[1], storage, ui);
                break;
            case "unmark":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("You need to specify a task number to unmark!");
                }
                tasks.unmarkTask(commandAndTask[1], storage, ui);
                break;
            case "todo":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("The description of your todo task can't be empty!");
                }
                tasks.addToDo(commandAndTask[1], storage, ui);
                break;
            case "deadline":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("Your deadline task needs a description and a deadline! (use /by)");
                }
                tasks.addDeadline(commandAndTask[1], storage, ui);
                break;
            case "event":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException(
                        "Your event task needs a description and a time frame! (use /from and /to)");
                }
                if (!commandAndTask[1].contains(" /from ") || !commandAndTask[1].contains(" /to ")) {
                    throw new SlipstreamException("Your event task needs a time frame! (use /from and /to)");
                }
                tasks.addEvent(commandAndTask[1], storage, ui);
                break;
            case "delete":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("The delete command needs a task number!");
                }
                tasks.deleteTask(commandAndTask[1], storage, ui);
                break;
            case "clear":
                tasks.clearTasks(storage, ui);
                break;
            case "find":
                if (commandAndTask.length < 2) {
                    throw new SlipstreamException("You need to specify a keyword to search!");
                }
                ArrayList<Task> matchingTasks = tasks.findTasks(commandAndTask[1]);
                ui.showMatchingTasks(matchingTasks);
                break;
            default:
                throw new SlipstreamException("Sorry but I don't know what that means :/");
            }
        } catch (Exception e) {
            ui.showMessage("An error occurred: " + e.getMessage());
        }
        return true;
    }
}
