package Infrastructure;

import Infrastructure.Services.DatabaseQueryExecutor;

public class DatabaseInitializationService {
    public static void Initialize() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Up();
    }

    private static void Up() {
        String eventSql = "create table PUBLIC.\"EVENT\"(" +
                "\"Alert\" bit(1)," +
                "\"Id\" bigint identity NOT NULL PRIMARY KEY," +
                "\"EventId\" varchar(20) not null UNIQUE," +
                "\"Duration\" int not null," +
                "\"Type\" varchar(20)," +
                "\"Host\" varchar(20));";

        DatabaseQueryExecutor.ExecuteCommand(eventSql);
    }
}
