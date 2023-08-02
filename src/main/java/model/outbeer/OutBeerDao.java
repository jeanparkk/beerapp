package model.outbeer;

import dto.OutBeerRespDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OutBeerDao {

    private Connection connection;

    public OutBeerDao(Connection connection) {
        this.connection = connection;
    }

    // 단종 맥주 등록
    public int createOutBeer(int beerId, String reason) {
        String query = "INSERT INTO out_beer (beer_id, reason, created_at) VALUES (?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, beerId);
            statement.setString(2, reason);

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    // 단종 맥주 목록 조회
    public List<OutBeerRespDto> getOutBeers() {
        List<OutBeerRespDto> outBeers = new ArrayList<>();
        String query = "SELECT b.id 'id', b.name 'name', o.reason 'reason', date_format(o.created_at, '%Y.%m.%d') 'outDate' " +
                "FROM out_beer o " +
                "RIGHT OUTER JOIN beer p " +
                "ON o.beer_id = b.id";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    outBeers.add(getOutBeerRespDtoFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return outBeers;
    }

    private OutBeerRespDto getOutBeerRespDtoFromResultSet(ResultSet resultSet) {
        try {
            Integer beerId = resultSet.getInt("id");
            String beerName = resultSet.getString("name");
            String outReason = resultSet.getString("reason");
            String outDate = resultSet.getString("outDate");

            return OutBeerRespDto.builder()
                    .beerId(beerId)
                    .beerName(beerName)
                    .outReason(outReason)
                    .outDate(outDate)
                    .build();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}