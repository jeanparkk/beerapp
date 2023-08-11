package service;

import model.brewery.Brewery;
import model.brewery.BreweryDao;

import java.sql.SQLException;
import java.util.List;

public class BreweryService {

    private final BreweryDao breweryDao;

    public BreweryService(BreweryDao breweryDao){
        this.breweryDao = breweryDao;
    }

    // 양조장 등록 기능
    public String createBrewery(String breweryName) {
        try {
            int result = breweryDao.createBrewery(breweryName);
            if (result > 0) {
                return "성공";
            } else {
                return "실패";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "실패";
        }
    }

    // 전체 양조장 목록 조회 기능
    public List<Brewery> getAllBreweries() {
        List<Brewery> brweries = breweryDao.getAllBreweries();
        if (brweries != null && !brweries.isEmpty()) {
            return brweries;
        } else {
            return List.of();
        }
    }
}
