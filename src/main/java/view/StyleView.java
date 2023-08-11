package view;

import dto.StyleRespDto;
import service.StyleService;

import java.util.List;
import java.util.Map;

public class StyleView {
    private final StyleService styleService;
    public StyleView(StyleService styleService){
        this.styleService = styleService;
    }

    public void create(Map<String, Object> params) {
        try {
            int breweryId = Integer.parseInt(params.get("breweryId").toString());
            String name = params.get("name").toString();
            System.out.println(styleService.createStyle(breweryId, name));
        } catch (Exception e) {
            throw new RuntimeException("잘못된 요청입니다.");
        }

    }

    public void findStyles() {
        try {
            List<StyleRespDto> styles = styleService.getAllStyles();
            String[] tableHead = {"스타일 ID", "스타일명", "양조장 ID", "양조장명"};
            printRow(tableHead);
            for (StyleRespDto style : styles) {
                String[] tableData = {
                        style.getId().toString(),
                        style.getStyleName(),
                        style.getBreweryId().toString(),
                        style.getBreweryName()
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
