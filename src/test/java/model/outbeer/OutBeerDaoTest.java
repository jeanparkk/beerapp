package model.outbeer;


import db.DBConnection;
import dto.OutBeerRespDto;
import model.beer.BeerDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OutBeerDaoTest {
    private Connection connection = DBConnection.getConnection();
    private OutBeerDao outBeerDao = new OutBeerDao(connection);
    private BeerDao beerDao = new BeerDao(connection);

    @BeforeEach
    void beforeEach() throws SQLException {
        dbInit();
    }

    @AfterEach
    void afterEach() throws SQLException {
        dbInit();
    }
    private void dbInit() throws SQLException {
        connection.prepareStatement("DELETE FROM out_beer").execute();
        connection.prepareStatement("DELETE FROM beer").execute();
        connection.prepareStatement("alter table out_beer auto_increment=1").execute();
        connection.prepareStatement("alter table beer auto_increment=1").execute();
    }

    @Test
    @DisplayName("단종 맥주 등록 테스트")
    void createOutBeerTest() {
        // Given
        beerDao.createBeer(1, "일분수괴즈");
        int beerId = 1;

        // When
        int result = outBeerDao.createOutBeer(beerId, "인기없음");

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("단종 맥주 목록 조회")
    void getOutBeers() {
        // Given
        beerDao.createBeer(1, "일분수괴즈");
        beerDao.createBeer(1, "이분수괴즈");
        beerDao.createBeer(1, "삼분수괴즈");

        outBeerDao.createOutBeer(2, "효모단종"); // 이분수괴즈 단종
        outBeerDao.createOutBeer(3, "시즌종료"); // 삼분수괴즈 단종

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        String currentDate = getFormatDate(now);

        // When
        List<OutBeerRespDto> outBeers = outBeerDao.getOutBeers();

        // Then
        assertThat(outBeers.size()).isEqualTo(3);
        assertThat(outBeers.get(1).getOutReason()).isEqualTo("효모단종");
        assertThat(outBeers.get(2).getOutReason()).isEqualTo("시즌종료");
        assertThat(getFormatDate(outBeers.get(2).getOutDate())).isEqualTo(currentDate);
    }

    private String getFormatDate(Timestamp timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY.MM.dd");
        return timestamp.toLocalDateTime().format(formatter);
    }

}