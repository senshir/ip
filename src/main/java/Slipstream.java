import java.util.Scanner;
import java.io.IOException;

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