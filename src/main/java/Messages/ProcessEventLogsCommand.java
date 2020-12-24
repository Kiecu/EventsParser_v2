package Messages;

public class ProcessEventLogsCommand {
    private final String fileName;

    public ProcessEventLogsCommand(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
