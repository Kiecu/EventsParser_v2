package Infrastructure.AggregateRepositories;

import Aggregates.EventState;
import Infrastructure.Services.DatabaseQueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventRepository {

    public EventState Get(String eventId) {
        if (eventId == null || eventId == "") {
            return null;
        }

        String query = "SELECT * FROM PUBLIC.\"EVENT\" WHERE EventId = \"" + eventId + "\"";
        try (ResultSet resultSet = DatabaseQueryExecutor.ExecuteQuery(query)) {

            if (resultSet == null) {
                return null;
            }
            try {
                if (resultSet.next()) {
                    return MapToEventState(resultSet);
                }

                return null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<EventState> GetAll() {
        String query = "SELECT * FROM PUBLIC.\"EVENT\"";
        try (ResultSet resultSet = DatabaseQueryExecutor.ExecuteQuery(query)) {

            if (resultSet == null) {
                return null;
            }
            try {
                ArrayList<EventState> events = new ArrayList<>();

                while (resultSet.next()) {
                    events.add(MapToEventState(resultSet));
                }

                return events;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void Add(ArrayList<EventState> eventStates) {
        ArrayList<String> commands = new ArrayList<>();

        for (EventState state : eventStates) {
            String commandBuilder = new StringBuilder("INSERT INTO PUBLIC.\"EVENT\"(\"EventId\", \"Duration\", \"Type\", \"Host\", \"Alert\")Values(")
                    .append("'").append(state.getId()).append("',")
                    .append("'").append(state.getDuration()).append("',")
                    .append("'").append(state.getType()).append("',")
                    .append("'").append(state.getHost()).append("',")
                    .append(state.getAlert() == true ? 1 : 0)
                    .append(")")
                    .toString();

            commands.add(commandBuilder);
        }

        DatabaseQueryExecutor.ExecuteBatchInsert(commands);
    }

    private EventState MapToEventState(ResultSet resultSet) throws SQLException {
        EventState state = new EventState();

        state.setId(resultSet.getString("EventId"));
        state.setDuration(resultSet.getLong("Duration"));
        state.setType(resultSet.getString("Type"));
        state.setHost(resultSet.getString("Host"));
        state.setAlert(resultSet.getBoolean("Alert"));

        return state;
    }
}
