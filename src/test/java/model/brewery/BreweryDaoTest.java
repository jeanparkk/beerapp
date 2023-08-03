package model.brewery;
import db.DBConnection;
import model.brewery.Brewery;
import model.brewery.BreweryDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BreweryDaoTest {

    private Connection connection = DBConnection.getConnection();
    private BreweryDao breweryDao = new BreweryDao(connection);


    @BeforeEach
    void beforeEach() throws SQLException {
        connection.setAutoCommit(false);
    }

    @AfterEach
    public void tearDown() {
        // 각 테스트가 끝난 후 정리 작업을 수행 (예: 데이터베이스 연결 종료 등)
    }

    @Test
    @DisplayName("양조장 등록 테스트")
    public void testCreateBrewery() throws SQLException {
        String breweryName = "Sample Brewery";
        // 양조장 등록 메서드 호출 및 결과 검증
        int result = breweryDao.createBrewery(breweryName);
        assertThat(result).isNotEqualTo(-1); // 등록 결과가 -1이 아니어야 성공적으로 등록된 것으로 간주
    }

    @Test
    @DisplayName("전체 양조장 목록 보기 테스트")
    public void testGetAllBreweries() {
        // 전체 양조장 목록 가져오기 메서드 호출 및 결과 검증
        List<Brewery> breweries = breweryDao.getAllBreweries();
        assertThat(breweries).isNotNull(); // 목록이 null이 아니어야 함
        assertThat(breweries.size()).isGreaterThanOrEqualTo(0); // 양조장의 개수가 0 이상이어야 함
    }
}
