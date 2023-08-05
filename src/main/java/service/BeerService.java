package service;

import model.beer.Beer;
import model.beer.BeerDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BeerService {

    private final BeerDao beerDao;

    public BeerService(BeerDao beerDao) {
        this.beerDao = beerDao;
    }

    // 맥주 등록
    public String createBeer(int styleId, String name ) {
        Connection connection = beerDao.getConnection();

        try {
            connection.setAutoCommit(false);
            int result = beerDao.createBeer(styleId, name);
            if (result == -1) {
                connection.rollback();
            } else {
                connection.commit();
                return "성공";
            }
        } catch (SQLException outerException) {
            System.out.println("ERROR: " + outerException.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        return "실패";
    }


    // 스타일별 맥주 조회 (nullable)
    public List<Beer> getBeersByStyleId(int styleId) {
        List<Beer> beers = beerDao.getBeersByStyleId(styleId);
        if (beers == null) {
            throw new RuntimeException("선수 목록을 가져올 수 없습니다.");
        }

        return beers;
    }


}
