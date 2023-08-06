package config;
import db.DBConnection;
import lombok.Getter;
import model.beer.BeerDao;
import model.outbeer.OutBeerDao;
import service.BeerService;
import service.OutBeerService;

import java.sql.Connection;

@Getter
public class AppConfig {
    private final static AppConfig instance = new AppConfig();
    private final Connection connection;
    private final BeerDao beerDao;
    private final BeerService beerService;
    private final OutBeerDao outBeerDao;
    private final OutBeerService outBeerService;

    public static AppConfig getInstance() {
        return instance;
    }

    private AppConfig() {
        this.connection = DBConnection.getConnection();
        this.beerDao = new BeerDao(connection);
        this.beerService = new BeerService(beerDao);
        this.outBeerDao = new OutBeerDao(connection);
        this.outBeerService = new OutBeerService(beerDao, outBeerDao);
    }
}