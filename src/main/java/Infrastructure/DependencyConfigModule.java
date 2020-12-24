package Infrastructure;

import Handlers.CommandHandler;
import Handlers.EventLogsCommandHandler;
import Infrastructure.AggregateRepositories.EventRepository;
import Infrastructure.Services.EventLogsParser;
import Infrastructure.Services.FileService;
import Infrastructure.Services.StreamParser;
import Messages.ProcessEventLogsCommand;
import Models.EventLog;
import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

public class DependencyConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<CommandHandler<ProcessEventLogsCommand>>() {}).to(EventLogsCommandHandler.class);
        bind(EventRepository.class).toInstance(new EventRepository());
        bind(FileService.class).in(Singleton.class);
        bind(new TypeLiteral<StreamParser<EventLog>>() {}).toInstance(new EventLogsParser(new Gson()));
    }
}
