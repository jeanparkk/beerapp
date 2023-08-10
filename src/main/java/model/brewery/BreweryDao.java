package model.brewery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BreweryDao {
    private Connection connection;

    public BreweryDao(Connection connection) {
        this.connection = connection;
    }
    //양조장 등록
    public int createBrewery(String name) throws SQLException{
        String query = "INSERT INTO brewery (name, created_at) VALUES(?,now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);

            return statement.executeUpdate();
        }catch (SQLException e) {
            System.out.println("양조장 등록 실패");
        }

        return -1;
    }

    //전체 양조장 목록 보기
    public List<Brewery> getAllBreweries() {
        List<Brewery> breweries = new ArrayList<>();
        String query = "SELECT * FROM brewery";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    breweries.add(getBreweryFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("전체 양조장 목록 보기 실패");
            return null;
        }
        return breweries;
    }
    private Brewery getBreweryFromResultSet(ResultSet resultSet) {
        try {
            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Timestamp createdAt = resultSet.getTimestamp("created_at");

            return Brewery.builder()
                    .id(id)
                    .name(name)
                    .createdAt(createdAt)
                    .build();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
