package model.beer;

import db.DBConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeerDaoTest {
    private Connection connection = DBConnection.getConnection();
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
        connection.prepareStatement("DELETE FROM beer").execute();
        connection.prepareStatement("alter table beer auto_increment=1").execute();
    }

    @Test
    @DisplayName("맥주 등록 성공")
    void createBeerSuccessTest() {
        // Given
        // TODO: Style 도메인 완성 후 Style 객체로 테스트 완성

        // When
        int result = beerDao.createBeer(1, "일분수괴즈");;

        // Then
        assertThat(result).isEqualTo(1);
    }


    @Test
    @DisplayName("맥주 등록 실패 - 존재하지 않는 styleId 사용")
    void createBeerFailTestWithInvalidStyleId() {
        // Given
        int styleId = 1000;

        // When
        int result = beerDao.createBeer(styleId, "일분수괴즈");

        // Then
        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("맥주 조회 테스트")
    void getBeerByIdTest() {
        // Given
        beerDao.createBeer(1, "일분수괴즈");
        int beerId = 1;

        // When
        Beer beer = beerDao.getBeerById(beerId);

        // Then
        assertThat(beer).isNotNull();
        assertThat(beer.getId()).isEqualTo(beerId);
        assertThat(beer.getName()).isEqualTo("일분수괴즈");
    }

    @Test
    @DisplayName("스타일 별 맥주 조회 테스트")
    void getBeersTest() {
        // Given
        int styleId = 1;
        beerDao.createBeer(styleId, "일분수괴즈");
        beerDao.createBeer(styleId, "이분수괴즈");
        beerDao.createBeer(styleId, "삼분수괴즈");
        beerDao.createBeer(styleId, "사분수괴즈");

        // When
        List<Beer> beers = beerDao.getBeersByStyleId(styleId);

        // Then
        assertThat(beers.size()).isEqualTo(4);
        assertThat(beers.get(0).getName()).isEqualTo("일분수괴즈");
        assertThat(beers.get(1).getName()).isEqualTo("이분수괴즈");
        assertThat(beers.get(2).getName()).isEqualTo("삼분수괴즈");
        assertThat(beers.get(3).getName()).isEqualTo("사분수괴즈");
    }

    @Test
    @DisplayName("맥주 단종 시 styleId를 null로 변경")
    void convertOutBeerTest() {
        // Given
        beerDao.createBeer(1, "일분수괴즈");
        int beerId = 1;

        // When
        beerDao.convertOutBeer(beerId);

        // Then
        Beer beer = beerDao.getBeerById(beerId);
        assertThat(beer).isNotNull();
        assertThat(beer.getStyleId()).isNull();
    }

}
