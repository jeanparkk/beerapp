package view;

import service.BeerService;

public class BeerView {
    private final BeerService beerService;

    public BeerView(BeerService beerService) {
        this.beerService = beerService;
    }
}