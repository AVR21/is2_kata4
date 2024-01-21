package is2.ulpgc.kata4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLTrackLoader implements TrackLoader{
    private final Connection connection;
    private static final String SQL = "select tracks.name as track, composer, Milliseconds, title, " +
            "artists.Name as artist from tracks, albums, artists, " +
            "genres where tracks.AlbumId = albums.AlbumId and albums.ArtistId = artists.ArtistId and " +
            "tracks.GenreId = genres.GenreId";

    public SQLTrackLoader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Track> load() {
        try {
            return load(query());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private List<Track> load(ResultSet resultSet) throws SQLException {
        List<Track> result = new ArrayList<>();
        while(resultSet.next())
            result.add(trackFrom(resultSet));
        return result;
    }

    private static Track trackFrom(ResultSet resultSet) throws SQLException {
        return new Track(
                resultSet.getString("track"),
                resultSet.getString("composer"),
                resultSet.getString("title"),
                resultSet.getString("artist"),
                resultSet.getInt("milliseconds")/1000
        );
    }

    private ResultSet query() throws SQLException{
        return connection.createStatement().executeQuery(SQL);
    }

}
