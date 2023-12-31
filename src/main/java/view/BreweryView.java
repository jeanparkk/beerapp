package view;

import model.brewery.Brewery;
import service.BreweryService;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class BreweryView {
    private final BreweryService breweryService;
    public BreweryView(BreweryService breweryService) {
        this.breweryService = breweryService;
    }

    public void create(Map<String, Object> params) {
        try {
            String name = params.get("name").toString();
            System.out.println(breweryService.createBrewery(name));
        } catch (Exception e) {
            throw new RuntimeException("잘못된 요청입니다.");
        }
    }

    public void findBreweries() {
        try {
            List<Brewery> breweries = breweryService.getAllBreweries();
            String[] tableHead = {"양조장 ID", "양조장 이름", "등록일"};
            printRow(tableHead);
            for (Brewery brewery : breweries) {
                String[] tableData = {
                        brewery.getId().toString(),
                        brewery.getName(),
                        ViewFormatter.getFormatDateTime(brewery.getCreatedAt())
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