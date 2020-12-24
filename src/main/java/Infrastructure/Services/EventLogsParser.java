package Infrastructure.Services;

import Models.EventLog;
import com.google.gson.Gson;
import com.google.inject.Inject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class EventLogsParser implements StreamParser<EventLog> {

    private final Gson jsonParser;

    @Inject
    public EventLogsParser(Gson jsonParser) {

        this.jsonParser = jsonParser;
    }

    @Override
    public ArrayList<EventLog> Parse(InputStream stream) {
        try (InputStreamReader streamReader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            try (BufferedReader reader = new BufferedReader(streamReader)) {

                ArrayList<EventLog> eventLogs = new ArrayList<>();
                String line;

                while ((line = reader.readLine()) != null) {
                    EventLog eventLog = jsonParser.fromJson(line, EventLog.class);
                    eventLogs.add(eventLog);
                }

                return eventLogs;
            }
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
