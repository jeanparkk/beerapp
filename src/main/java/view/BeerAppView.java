package view;


import config.AppConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static view.CommandConst.*;

public class BeerAppView {
    private final AppConfig appConfig;
    private final BeerView beerView;
    private final OutBeerView outBeerView;
    private final BreweryView breweryView;
    private final StyleView styleView;

    public BeerAppView() {
        this.appConfig = AppConfig.getInstance();
        this.beerView = new BeerView(appConfig.getBeerService());
        this.outBeerView = new OutBeerView(appConfig.getOutBeerService());
        this.breweryView = new BreweryView(appConfig.getBreweryService());
        this.styleView = new StyleView(appConfig.getStyleService());
    }

    public void renderWithParams(String command, Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            throw new RuntimeException("잘못된 요청입니다.");
        }
        switch (command) {
            case CREATE_BREWERY:
                System.out.println("양조장등록 요청");
                breweryView.create(params);
                return;
            case CREATE_STYLE:
                System.out.println("스타일등록 요청");
                styleView.create(params);
                return;
            case CREATE_BEER:
                System.out.println("맥주등록 요청");
                beerView.create(params);
                return;
            case BEER_LIST:
                System.out.println("맥주목록 요청");
                beerView.findBeers(params);
                return;
            case CREATE_OUT_BEER:
                System.out.println("단종등록 요청");
                outBeerView.create(params);
                return;
            default:
                System.out.println("잘못된 요청입니다.");
        }
        System.out.println();
    }

    public void renderWithoutParams(String command) {
        switch (command) {
            case BREWERY_LIST:
                System.out.println("양조장목록 요청");
                breweryView.findBreweries();
                return;
            case STYLE_LIST:
                System.out.println("스타일목록 요청");
                styleView.findStyles();
                return;
            case OUT_BEER_LIST:
                System.out.println("퇴출목록 요청");
                outBeerView.findOutBeers();
                return;
            default:
                System.out.println("잘못된 요청입니다.");
        }
    }

    public String[] separateCommandAndParams(String input) {
        return input.split("\\?");
    }

    public Map<String, Object> getParams(String[] commandAndParams) {
        Map<String, Object> params = new HashMap<>();
        StringTokenizer paramsToken = new StringTokenizer(commandAndParams[1], "&");

        while (paramsToken.hasMoreTokens()) {
            StringTokenizer keyAndValueToken = new StringTokenizer(paramsToken.nextToken(), "=");
            if (keyAndValueToken.countTokens() == 2) {
                String key = keyAndValueToken.nextToken();
                Object value = keyAndValueToken.nextToken();
                params.put(key, value);
            } else {
                throw new RuntimeException("잘못된 요청입니다.");
            }
        }

        return params;
    }
}