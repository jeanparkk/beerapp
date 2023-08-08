package service;

import model.brewery.BreweryDao;

public class BreweryService {

    private final BreweryDao breweryDao;

    public BreweryService(BreweryDao breweryDao){
        this.breweryDao = breweryDao;
    }
}
