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
    public int createStyle(int breweryId, String name) throws SQLException {
        String query = "INSERT INTO style (brewery_id, name, created_at) VALUES(?,?,now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, breweryId);
            statement.setString(2, name);

            return statement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    //전체 스타일 목록 보기
    public List<StyleRespDto> getAllStyles(){
        List<StyleRespDto> styles = new ArrayList<>();
        String query ="SELECT s.id AS style_id, s.name AS style_name, b.id AS brewery_id, b.name AS brewery_name  " +
                "FROM style s " +
                "INNER JOIN brewery b ON s.brewery_id = b.id";


        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                styles.add(getStyleFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return styles;
    }
    private StyleRespDto getStyleFromResultSet(ResultSet resultSet) {
        try {
            Integer styleId = resultSet.getInt("style_id");
            Integer breweryId = resultSet.getInt("brewery_id");
            String styleName = resultSet.getString("style_name");
            String breweryName = resultSet.getString("brewery_name");

            return StyleRespDto.builder()
                    .id(styleId)
                    .breweryId(breweryId)
                    .styleName(styleName)
                    .breweryName(breweryName)
                    .build();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
