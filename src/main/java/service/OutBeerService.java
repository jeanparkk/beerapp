package service;


import dto.OutBeerRespDto;
import model.beer.BeerDao;
import model.outbeer.OutBeerDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OutBeerService {

    private final BeerDao beerDao;
    private final OutBeerDao outBeerDao;

    public OutBeerService(BeerDao beerDao, OutBeerDao outBeerDao) {
        this.beerDao = beerDao;
        this.outBeerDao = outBeerDao;
    }

    // 맥주 단종 등록 (트랜잭션)
    public String createOutBeer(int beerId, String reason) {
        Connection connection = beerDao.getConnection();

        try {
            connection.setAutoCommit(false);

            int outBeerResult = outBeerDao.createOutBeer(beerId, reason);
            int convertResult = beerDao.convertOutBeer(beerId);

            if (outBeerResult == -1 || convertResult == -1) {
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


    // 단종 맥주 목록 조회
    public List<OutBeerRespDto> getOutBeers() {
        List<OutBeerRespDto> outBeers = outBeerDao.getOutBeers();
        if (outBeers == null) {
            throw new RuntimeException("단종 맥주 목록을 가져올 수 없습니다.");
        }
        return outBeers;
    }

}
