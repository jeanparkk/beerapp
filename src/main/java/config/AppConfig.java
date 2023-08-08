package config;
import db.DBConnection;
import lombok.Getter;
import model.beer.BeerDao;
import model.brewery.BreweryDao;
import model.outbeer.OutBeerDao;
import model.style.StyleDao;
import service.BeerService;
import service.BreweryService;
import service.OutBeerService;
import service.StyleService;

import java.sql.Connection;

@Getter
public class AppConfig {
    private final static AppConfig instance = new AppConfig();
    private final Connection connection;
    private final BeerDao beerDao;
    private final BeerService beerService;
    private final OutBeerDao outBeerDao;
    private final OutBeerService outBeerService;
    private final BreweryService breweryService;
    private final BreweryDao breweryDao;
    private final StyleDao styleDao;
    private final StyleService styleService;

    public static AppConfig getInstance() {
        return instance;
    }

    private AppConfig() {
        this.connection = DBConnection.getConnection();
        this.beerDao = new BeerDao(connection);
        this.beerService = new BeerService(beerDao);
        this.outBeerDao = new OutBeerDao(connection);
        this.outBeerService = new OutBeerService(beerDao, outBeerDao);
        this.breweryDao = new BreweryDao(connection);
        this.breweryService = new BreweryService(breweryDao);
        this.styleDao = new StyleDao(connection);
        this.styleService = new StyleService(styleDao);
    }
}