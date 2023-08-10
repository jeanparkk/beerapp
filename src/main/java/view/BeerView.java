package view;

import model.beer.Beer;
import service.BeerService;

import java.util.List;
import java.util.Map;

public class BeerView {
    private final BeerService beerService;

    public BeerView(BeerService beerService) {
        this.beerService = beerService;
    }

    public void create(Map<String, Object> params) {
        try {
            int styleId = Integer.parseInt(params.get("styleId").toString());
            String name = params.get("name").toString();
            System.out.println(beerService.createBeer(styleId, name));
        } catch (Exception e) {
            throw new RuntimeException("잘못된 요청입니다.");
        }
    }

    public void findBeers(Map<String, Object> params) {
        try {
            int styleId = Integer.parseInt(params.get("styleId").toString());
            List<Beer> beers = beerService.getBeersByStyleId(styleId);
            String[] tableHead = {"맥주Id" ,"이름", "등록일"};
            printRow(tableHead);
            for (Beer beer : beers) {
                String[] tableData = {
                        beer.getId().toString(),
                        beer.getName(),
                        ViewFormatter.getFormatDateTime(beer.getCreatedAt())
                };
                printRow(tableData);
            }
        } catch (Exception e) {
            throw new RuntimeException("잘못된 요청입니다.");
        }

    }
    private void printRow(String[] input) {
        StringBuilder builder = new StringBuilder();
        for (String str : input) {
            builder.append(ViewFormatter.getFormatData(str));
        }
        System.out.println(builder);
    }


}