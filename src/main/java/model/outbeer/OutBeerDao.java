package model.outbeer;

import dto.OutBeerRespDto;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

@Getter
public class OutBeerDao {

    private final Connection connection;

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
        String query = "SELECT b.id 'id', b.name 'name', o.reason 'reason', o.created_at 'outDate' " +
                "FROM out_beer o " +
                "RIGHT OUTER JOIN beer b " +
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
            Timestamp outDate  = resultSet.getTimestamp("outDate");

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