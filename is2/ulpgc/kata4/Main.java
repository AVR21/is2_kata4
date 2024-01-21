package is2.ulpgc.kata4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(url("chinook.db"))) {
            TrackLoader trackLoader = new SQLTrackLoader(connection);
            List<Track> trackList = trackLoader.load();
            for(Track track : trackList)
                System.out.println(track);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String url(String file) {
        return "jdbc:sqlite:"+ file;
    }

}
