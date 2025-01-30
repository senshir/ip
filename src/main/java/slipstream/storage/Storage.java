package slipstream.storage;

import slipstream.SlipstreamException;
import slipstream.task.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
        checkFileExists();
    }

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
