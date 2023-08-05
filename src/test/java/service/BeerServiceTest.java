package service;

import db.DBConnection;
import model.beer.Beer;
import model.beer.BeerDao;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeerServiceTest {
    private Connection connection = DBConnection.getConnection();
    private BeerDao beerDao = new BeerDao(connection);
    private BeerService beerService = new BeerService(beerDao);

    @BeforeEach
    void beforeEach() throws SQLException {
        dbInit();
    }

    @AfterEach
    void afterEach() throws SQLException {
        dbInit();
    }

    private void dbInit() throws SQLException {
        connection.prepareStatement("DELETE FROM beer").execute();
        connection.prepareStatement("alter table beer auto_increment=1").execute();
    }

    @Test
    @DisplayName("Service - 맥주 등록 성공")
    void createBeerSuccessTest() {
        // Given
        int styleId = 1;
        String name = "일분수괴즈";

        // When
        String result = beerService.createBeer(styleId, name);

        // Then
        assertThat(result).isEqualTo("성공");
    }


    @Test
    @DisplayName("Service - 맥주 등록 실패 (존재하지 않는 스타일Id)")
    void createBeerFailTestWithInvalidStyleId() {
        // Given
        int styleId = -1;
        String name = "일분수괴즈";

        // When
        String result = beerService.createBeer(styleId, name);

        // Then
        assertThat(result).isEqualTo("실패");
    }

    @Test
    @DisplayName("Service - 스타일 조회 성공")
    void getBeersByStyleIdSuccessTest() {
        // Given
        int styleId = 1;
        beerDao.createBeer(styleId, "일분수괴즈");
        beerDao.createBeer(styleId, "이분수괴즈");
        beerDao.createBeer(styleId, "삼분수괴즈");

        // When
        List<Beer> beers = beerService.getBeersByStyleId(styleId);

        // Then
        assertThat(beers.size()).isEqualTo(3);
        assertThat(beers.get(0).getName()).isEqualTo("일분수괴즈");
        assertThat(beers.get(1).getName()).isEqualTo("이분수괴즈");
        assertThat(beers.get(2).getName()).isEqualTo("삼분수괴즈");
    }

    @Test
    @DisplayName("Service - 없는 스타일 조회")
    void getBeersByStyleIdFailTestWithInvalidStyleId() {
        // Given
        int styleId = -1;

        // When
        List<Beer> beers = beerService.getBeersByStyleId(styleId);

        // Then
        assertThat(beers.isEmpty()).isTrue();
    }


}
