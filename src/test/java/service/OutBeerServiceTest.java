package service;



import db.DBConnection;

import dto.OutBeerRespDto;
import model.beer.Beer;
import model.beer.BeerDao;
import model.outbeer.OutBeerDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;




class OutBeerServiceTest {
    private Connection connection = DBConnection.getConnection();
    private BeerDao beerDao = new BeerDao(connection);
    private OutBeerDao outBeerDao = new OutBeerDao(connection);
    private OutBeerService outBeerService = new OutBeerService(beerDao, outBeerDao);

    @BeforeEach
    void beforeEach() throws SQLException {
        dbInit();
    }

    @AfterEach
    void afterEach() throws SQLException {
        dbInit();
    }

    private void dbInit() throws SQLException {
        connection.prepareStatement("DELETE FROM out_player").execute();
        connection.prepareStatement("DELETE FROM player").execute();
        connection.prepareStatement("alter table out_player auto_increment=1").execute();
        connection.prepareStatement("alter table player auto_increment=1").execute();
    }

    @Test
    @DisplayName("Service - 단종 맥주 등록 성공")
    void createOutBeerSuccessTest() {

        // Given
        beerDao.createBeer(1, "일분수괴즈");
        int beerId = 1;
        String reason = "시즌종료";

        // When
        String result = outBeerService.createOutBeer(beerId, reason);

        // Then
        assertThat(result).isEqualTo("성공");
        Beer beer = beerDao.getBeerById(beerId);
        assertThat(beer.getStyleId()).isNull();

        List<OutBeerRespDto> outBeers = outBeerDao.getOutBeers();
        assertThat(outBeers.get(0).getBeerId()).isEqualTo(beerId);
        assertThat(outBeers.get(0).getOutReason()).isEqualTo(reason);

    }

    @Test
    @DisplayName("Service - 단종 맥주 등록 실패(존재하지 않는 맥주)")
    void createOutPlayerFailTest() {
        // Given
        int beerId = 1;
        String reason = "시즌종료";

        // When
        String result = outBeerService.createOutBeer(beerId, reason);

        // Then
        assertThat(result).isEqualTo("실패");

    }

    @Test
    @DisplayName("Service - 단종 맥주 목록 조회 성공")
    void getOutBeersSuccessTest() {

        // Given
        beerDao.createBeer(1, "일본수괴즈");
        beerDao.createBeer(1, "이본수괴즈");
        beerDao.createBeer(1, "삼본수괴즈");

        int beerId = 1; // 일본수괴즈
        String reason = "수요하락";

        outBeerService.createOutBeer(beerId, reason);

        // When
        List<OutBeerRespDto> outBeers = outBeerService.getOutBeers();

        // Then
        assertThat(outBeers.size()).isEqualTo(3); // 일단 모든 맥주가 나오기 떄문에 사이즈는 3

        String outReasonOfOutBeer = outBeers.get(0).getOutReason(); // 일분수괴즈 단종 사유

        assertThat(outReasonOfOutBeer).isEqualTo(reason);


        String outReasonOfNoneOutBeer = outBeers.get(1).getOutReason(); // 이분수괴즈 단종되지 않음

        assertThat(outReasonOfNoneOutBeer).isNull();

    }




}