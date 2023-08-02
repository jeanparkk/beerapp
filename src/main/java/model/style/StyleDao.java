package model.style;

import dto.StyleRespDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StyleDao {
    private Connection connection;

    public StyleDao(Connection connection) {
        this.connection = connection;
    }
    //스타일등록
    public int createStyle(int id, int breweryId, String name) throws SQLException {
        String query = "INSERT INTO team (id, brewery_id, name, created_at) VALUES(?,?,?,now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2, breweryId);
            statement.setString(3, name);

            return statement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    //전체 팀 목록 보기
    public List<StyleRespDto> getAllTeams(int id){
        List<StyleRespDto> teams = new ArrayList<>();
        String query ="SELECT s.id AS style_id, s.name AS style_name, b.id AS brewery_id, t.created_at AS created_at  " +
                "FROM style s " +
                "RIGHT OUTER JOIN brewery b ON s.brewery_id = b.id";


        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    teams.add(getStyleFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return teams;
    }
    private StyleRespDto getStyleFromResultSet(ResultSet resultSet) {
        try {
            Integer id = resultSet.getInt("id");
            Integer breweryId = resultSet.getInt("brewery_id");
            String name = resultSet.getString("name");
            Timestamp createdAt = resultSet.getTimestamp("created_at");

            return StyleRespDto.builder()
                    .id(id)
                    .breweryId(breweryId)
                    .name(name)
                    .createdAt(createdAt)
                    .build();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
