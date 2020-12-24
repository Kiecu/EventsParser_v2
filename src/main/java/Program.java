import Handlers.CommandHandler;
import Handlers.EventLogsCommandHandler;
import Infrastructure.DatabaseInitializationService;
import Infrastructure.DependencyConfigModule;
import Messages.ProcessEventLogsCommand;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Program {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DependencyConfigModule());
        DatabaseInitializationService.Initialize();

        ProcessEventLogsCommand command = new ProcessEventLogsCommand(args[0]);
        CommandHandler<ProcessEventLogsCommand> handler = injector.getInstance(EventLogsCommandHandler.class);
        handler.Handle(command);
    }
}