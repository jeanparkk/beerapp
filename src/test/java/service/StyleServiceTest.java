package service;

import db.DBConnection;
import dto.StyleRespDto;
import model.style.StyleDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class StyleServiceTest {
    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection();
            StyleDao styleDao = new StyleDao(connection);
            StyleService styleService = new StyleService(styleDao);

            // 테스트 팀생성 메소드
            int breweryId = 1; // Provide the brewery ID
            String styleName = "Example Style";
            String result = styleService.createStyle(breweryId, styleName);
            System.out.println(result);

            // 테스트 getAllTeams 메소드
            List<StyleRespDto> styles = styleService.getAllStyles();
            for (StyleRespDto style : styles) {
                System.out.println("Style ID: " + style.getId());
                System.out.println("Style Name: " + style.getStyleName());
                System.out.println("Brewery ID: " + style.getBreweryId());
                System.out.println("Brewery Name: " + style.getBreweryName());
                System.out.println("-------------------------");
            }

            // DB 연결 닫기
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
