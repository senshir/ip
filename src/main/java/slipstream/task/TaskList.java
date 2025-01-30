package slipstream.task;

import slipstream.SlipstreamException;
import slipstream.storage.Storage;
import slipstream.ui.Ui;
import slipstream.task.*;
import java.util.ArrayList;

public class TaskList {
	private final ArrayList<Task> tasks;
	
	public TaskList() {
		this.tasks = new ArrayList<>();
	}
	
	public TaskList(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}
	
	public String listTasks(Ui ui) {
		return ui.showTaskList(tasks);
	}
	
	public void markTask(String indexStr, Storage storage, Ui ui) throws SlipstreamException {
		try {
			int index = Integer.parseInt(indexStr) - 1;
			if (index < 0 || index >= tasks.size()) {
				throw new SlipstreamException("You need to choose which task to mark! (with a number)");
			}
			tasks.get(index).markAsDone();
			storage.saveTasks(tasks);
			ui.showTaskMarked(tasks.get(index));
		} catch (NumberFormatException e) {
			throw new SlipstreamException("Please enter a valid task number!");
		}
	}
	
	public void unmarkTask(String indexStr, Storage storage, Ui ui) throws SlipstreamException {
		try {
			int index = Integer.parseInt(indexStr) - 1;
			if (index < 0 || index >= tasks.size()) {
				throw new SlipstreamException("You need to choose which task to unmark! (with a number)");
			}
			tasks.get(index).markAsNotDone();
			storage.saveTasks(tasks);
			ui.showTaskUnmarked(tasks.get(index));
		} catch (NumberFormatException e) {
			throw new SlipstreamException("Please enter a valid task number!");
		}
	}
	
	public void addToDo(String description, Storage storage, Ui ui) throws SlipstreamException {
		if (description.trim().isEmpty()) {
			throw new SlipstreamException("The description of your todo task can't be empty!");
		}
		ToDo newTask = new ToDo(description);
		tasks.add(newTask);
		storage.saveTasks(tasks);
		ui.showTaskAdded(newTask, tasks.size());
	}
	
	public void addDeadline(String input, Storage storage, Ui ui) throws SlipstreamException {
		if (!input.contains(" /by ")) {
			throw new SlipstreamException("Your deadline task needs a deadline! (use /by)");
		}
		String[] parts = input.split(" /by ", 2);
		Deadline newTask = new Deadline(parts[0], parts[1]);
		tasks.add(newTask);
		storage.saveTasks(tasks);
		ui.showTaskAdded(newTask, tasks.size());
	}
	
	public void addEvent(String input, Storage storage, Ui ui) throws SlipstreamException {
		if (!input.contains(" /from ") || !input.contains(" /to ")) {
			throw new SlipstreamException("Your event task needs a time frame! (use /from and /to) after your description");
		}
		String[] eventDetails = input.split(" /from | /to ", 3);
		Event newTask = new Event(eventDetails[0], eventDetails[1], eventDetails[2]);
		tasks.add(newTask);
		storage.saveTasks(tasks);
		ui.showTaskAdded(newTask, tasks.size());
	}
	
	public void deleteTask(String indexStr, Storage storage, Ui ui) throws SlipstreamException {
		try {
			int index = Integer.parseInt(indexStr) - 1;
			if (index < 0) {
				throw new SlipstreamException("The task number must be positive!");
			} else if (index >= tasks.size()) {
				throw new SlipstreamException("The task number is too large!" +
						" You only have " + tasks.size() + " tasks in your list.");
			}
			Task removedTask = tasks.remove(index);
			storage.saveTasks(tasks);
			ui.showTaskDeleted(removedTask, tasks.size());
		} catch (NumberFormatException e) {
			throw new SlipstreamException("Please enter a valid task number!");
		}
	}
	
	public void clearTasks(Storage storage, Ui ui) {
		tasks.clear();
		storage.saveTasks(tasks);
		ui.showTasksCleared();
	}
	
}
