package Handlers;

import Aggregates.EventAggregate;
import Aggregates.EventState;
import Infrastructure.AggregateRepositories.EventRepository;
import Infrastructure.Services.FileService;
import Infrastructure.Services.StreamParser;
import Messages.ProcessEventLogsCommand;
import Models.EventLog;
import com.google.inject.Inject;

import java.io.InputStream;
import java.util.ArrayList;

public class EventLogsCommandHandler implements CommandHandler<ProcessEventLogsCommand> {

    private final StreamParser<EventLog> eventLogStreamParser;
    private final FileService fileService;
    private final EventRepository repository;

    @Inject
    public EventLogsCommandHandler(
            StreamParser<EventLog> eventLogStreamParser,
            FileService fileService,
            EventRepository repository) {
        this.eventLogStreamParser = eventLogStreamParser;
        this.fileService = fileService;
        this.repository = repository;
    }

    public void Handle(ProcessEventLogsCommand message) {
        if (message == null || message.getFileName() == null) {
            throw new IllegalArgumentException("FileName can not be null");
        }

        InputStream stream = this.fileService.ReadFile(message.getFileName());
        ArrayList<EventLog> eventRawLogs = this.eventLogStreamParser.Parse(stream);

        EventAggregate aggregate = new EventAggregate();
        ArrayList<EventState> eventStates = aggregate.CreateEvents(eventRawLogs);

        this.repository.Add(eventStates);

        ArrayList<EventState> result = this.repository.GetAll();
    }
}
