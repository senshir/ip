package slipstream.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import slipstream.SlipstreamException;
import slipstream.task.Deadline;
import slipstream.task.Event;
import slipstream.task.Task;
import slipstream.task.ToDo;

/**
 * The {@code Storage} class represents a storage object that saves and loads tasks to and from a file.
 * It contains operations to save and load tasks.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a {@code Storage} instance with the specified file path.
     *
     * @param filePath The file path where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        checkFileExists();
    }

    /**
     * Checks if the file exists and creates it if it doesn't.
     * If the file doesn't exist, a directory is created as well.
     * Catches and prints any IOException that occurs.
     */
    private void checkFileExists() {
        File file = new File(filePath);
        File directory = file.getParentFile();

        try {
            // Create a directory if it doesn't exist yet
            if (directory != null && !directory.exists()) {
                directory.mkdirs();
            }
            // Create a file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("There was an issue creating the file: " + e.getMessage());
        }
    }

    /**
     * Saves the list of tasks to the file.
     *
     * @param taskList The list of tasks to be saved.
     */
    public void saveTasks(ArrayList<Task> taskList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : taskList) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("There was an issue saving the tasks: " + e.getMessage());
        }
    }

    /**
     * Reads a task from a line in the file.
     *
     * @param line The line in the file to read the task from.
     * @return The task read from the line.
     * @throws SlipstreamException If the task type is not recognized.
     */
    public static Task readTask(String line) throws SlipstreamException {
        String[] taskDetails = line.split(" \\| ", -1);
        if (taskDetails.length < 3) {
            return null;
        }
        String taskType = taskDetails[0];
        boolean isDone = taskDetails[1].equals("1");
        String description = taskDetails[2];

        switch (taskType) {
        case "T":
            return new ToDo(description, isDone);
        case "D":
            String by = taskDetails[3];
            return new Deadline(description, isDone, by);
        case "E":
            String from = taskDetails[3];
            String to = taskDetails[4];
            return new Event(description, isDone, from, to);
        default:
            throw new IllegalArgumentException("Can't identify the task type: " + taskType);
        }
    }

    /**
     * Loads tasks from the file.
     *
     * @return The list of tasks loaded from the file.
     * @throws IOException If there is an issue reading the file.
     * @throws SlipstreamException If there is an issue reading the tasks.
     */
    public ArrayList<Task> loadTasks() throws IOException, SlipstreamException {
        ArrayList<Task> taskList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                Task task = readTask(line);
                if (task != null) {
                    taskList.add(task);
                }
            }
        } catch (IOException | SlipstreamException e) {
            System.out.println("Issue with loading tasks: " + e.getMessage());
            throw e;
        }
        return taskList;
    }
}
