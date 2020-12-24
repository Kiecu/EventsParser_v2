package Aggregates;

import Models.EventLog;
import org.junit.Assert;

import java.util.ArrayList;

public class EventAggregateTests {

    private EventAggregate eventAggregate = new EventAggregate();

    @org.junit.Test
    public void CreateEvents_Given_Events_Are_In_Logfile_Then_Maps_Events_Correctly()
    {
        // Arrange
        EventLog firstEvent = this.Given_RawEvent_Is_Added("scsmbstgra", "12345", "STARTED",  "APPLICATION_LOG", 1491377495213L);
        EventLog secondEvent = this.Given_RawEvent_Is_Added("scsmbstgra", "12345", "FINISHED",  "APPLICATION_LOG", 1491377495218L);

        ArrayList<EventLog> eventRawLogs = new ArrayList<>();
        eventRawLogs.add(firstEvent);
        eventRawLogs.add(secondEvent);

        // Act
        ArrayList<EventState> events = eventAggregate.CreateEvents(eventRawLogs);

        // Assert
        Assert.assertTrue(events.size() == 1);

        EventState eventState = events.get(0);
        Assert.assertTrue(eventState.getAlert());
        Assert.assertEquals(eventState.getId(), "scsmbstgra");
        Assert.assertEquals(eventState.getHost(), "12345");
        Assert.assertEquals(eventState.getType(), "APPLICATION_LOG");
        Assert.assertEquals((long)eventState.getDuration(), 5L);
    }

    @org.junit.Test
    public void CreateEvents_Given_Events_Are_In_Logfile_Alert_Not_Set_When_Duration_Less_Than_4_Milliseconds()
    {
        // Arrange
        EventLog firstEvent = this.Given_RawEvent_Is_Added("scsmbstgra", "12345", "STARTED",  "APPLICATION_LOG", 1491377495213L);
        EventLog secondEvent = this.Given_RawEvent_Is_Added("scsmbstgra", "12345", "FINISHED",  "APPLICATION_LOG", 1491377495214L);

        ArrayList<EventLog> eventRawLogs = new ArrayList<>();
        eventRawLogs.add(firstEvent);
        eventRawLogs.add(secondEvent);

        // Act
        ArrayList<EventState> events = eventAggregate.CreateEvents(eventRawLogs);

        // Assert
        Assert.assertTrue(events.size() == 1);

        EventState eventState = events.get(0);
        Assert.assertFalse(eventState.getAlert());
        Assert.assertEquals(eventState.getId(), "scsmbstgra");
        Assert.assertEquals(eventState.getHost(), "12345");
        Assert.assertEquals(eventState.getType(), "APPLICATION_LOG");
        Assert.assertEquals((long)eventState.getDuration(), 1L);
    }

    @org.junit.Test
    public void CreateEvents_Given_No_Events_Are_In_Logfile_Then_No_Events_Are_Returned()
    {
        // Arrange
        ArrayList<EventLog> eventRawLogs = new ArrayList<>();

        // Act
        ArrayList<EventState> events = eventAggregate.CreateEvents(eventRawLogs);

        // Assert
        Assert.assertTrue(events.size() == 0);
    }

    private EventLog Given_RawEvent_Is_Added(
            String id,
            String host,
            String state,
            String type,
            long duration
    )
    {
        EventLog eventRawLog = new EventLog();

        eventRawLog.setId(id);
        eventRawLog.setHost(host);
        eventRawLog.setState(state);
        eventRawLog.setType(type);
        eventRawLog.setTimestamp(duration);

        return eventRawLog;
    }
}
