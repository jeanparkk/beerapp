package model.style;

import db.DBConnection;
import dto.StyleRespDto;
import model.style.StyleDao;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class StyleDaoTest {

    private Connection connection = DBConnection.getConnection();
    private StyleDao styleDao = new StyleDao(connection);

    @BeforeEach
    void beforeEach() throws SQLException {
        connection.setAutoCommit(false);
    }

    @AfterEach
    void afterEach() throws SQLException {
        connection.rollback();
        connection.commit();
        connection.setAutoCommit(true);
        // Auto_Increment 초기화
        PreparedStatement statement = connection.prepareStatement("alter table style auto_increment=?");
        statement.setInt(1, 1);
        statement.execute();
    }

    @Test
    @DisplayName("스타일 등록 성공")
    void createStyleSuccessTest() throws SQLException {
        // Given


        // When
        int result = styleDao.createStyle(1, "괴즈");;

        // Then
        assertThat(result).isEqualTo(1);
    }


    @Test
    @DisplayName("스타일 조회 테스트")
    public void testGetAllStyles() throws SQLException {
        // Given
        int id = 1;
        styleDao.createStyle(1,"람빅");
        styleDao.createStyle(2,"에일");
        styleDao.createStyle(3,"라거");

        // When
        List<StyleRespDto> styles = styleDao.getAllStyles(id);

        // Then
        Assertions.assertNotNull(styles);
        Assertions.assertFalse(styles.isEmpty());
        for (StyleRespDto style : styles) {
            Assertions.assertNotNull(style.getId());
            Assertions.assertNotNull(style.getBreweryId());
            Assertions.assertNotNull(style.getStyleName());
            Assertions.assertNotNull(style.getBreweryName());
        }
    }
}
