package service;

import db.DBConnection;
import model.brewery.Brewery;
import model.brewery.BreweryDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
public class BreweryServiceTest {
    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection();
            BreweryDao breweryDao = new BreweryDao(connection);
            BreweryService breweryService = new BreweryService(breweryDao);

            // 테스트 createBrewery 메소드
            String breweryName = "Example Brewery";
            String result = breweryService.createBrewery(breweryName);
            System.out.println(result);

            // 테스트 getAllStadiums 메소드
            List<Brewery> breweries = breweryService.getAllBreweries();
            for (Brewery brewery : breweries) {
                System.out.println(brewery);
            }

            // DB 연결 닫기
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
