package model.beer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BeerDao {
    private Connection connection;

    public BeerDao(Connection connection) {
        this.connection = connection;
    }

    // 맥주 등록
    public int createBeer(int styleId, String beerName) {
        String query = "INSERT INTO beer (style_id, name, created_at) VALUES (?, ?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, styleId);
            statement.setString(2, beerName);

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;

        // TODO : 발생할 수 있는 예외 사항
        // 1. styleId에 해당하는 스타일이 존재하지 않는 경우

    }

    // 맥주 조회 (id 값으로 조회)
    public Beer getBeerById(int id) {
        String query = "SELECT * FROM beer WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultset = statement.executeQuery()) {
                if (resultset.next()) {
                    return getBeerFromResultSet(resultset);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // 스타일별 맥주 목록 조회
    public List<Beer> getBeersByStyleId(int styleId) {
        List<Beer> beers = new ArrayList<>();
        String query = "SELECT * FROM beer WHERE style_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, styleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    beers.add(getBeerFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return beers;
    }

    private Beer getBeerFromResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            int styleId = resultSet.getInt("style_id");
            String name = resultSet.getString("name");
            Timestamp createdAt = resultSet.getTimestamp("created_at");

            return Beer.builder()
                    .id(id)
                    .styleId(styleId)
                    .name(name)
                    .createdAt(createdAt)
                    .build();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}