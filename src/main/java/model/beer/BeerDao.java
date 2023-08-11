package model.beer;

import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BeerDao {
    private final Connection connection;

    public BeerDao(Connection connection) {
        this.connection = connection;
    }

    // 맥주 등록
    public  int createBeer(int styleId, String beerName) {
        String query = "INSERT INTO beer (style_id, name, created_at) VALUES (?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, styleId);
            statement.setString(2, beerName);

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("맥주 등록 실패");
        }

        return -1;



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
            System.out.println("맥주 조회 실패");
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
            throw new RuntimeException("스타일별 맥주 목록 조회 실패");
        }

        return beers;
    }

    // 맥주 단종
    public int convertOutBeer(int beerId) {
        String query = "UPDATE beer SET style_id = null where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, beerId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("맥주 단종 오류");
        }

        return -1;
    }

    private Beer getBeerFromResultSet(ResultSet resultSet) {
        try {
            Integer id = resultSet.getInt("id");
            Integer styleId = resultSet.getInt("style_id");
            styleId = styleId == 0 ? null : styleId;
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