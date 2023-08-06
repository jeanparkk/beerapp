package view;


import config.AppConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static view.CommandConst.*;

public class BeerView {
    private final AppConfig appConfig;

    public BeerView() {
        this.appConfig = AppConfig.getInstance();
    }

    public void renderWithParams(String command, Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            throw new RuntimeException("잘못된 요청입니다.");
        }
        switch (command) {
            case CREATE_BREWERY:
                System.out.println("양조장등록 요청");
                return;
            case CREATE_STYLE:
                System.out.println("스타일등록 요청");
                return;
            case CREATE_BEER:
                System.out.println("맥주등록 요청");
                return;
            case BEER_LIST:
                System.out.println("맥주목록 요청");
                return;
            case CREATE_OUT_BEER:
                System.out.println("단종등록 요청");
                return;
            default:
                System.out.println("잘못된 요청입니다.");
        }
        System.out.println();
    }

    public void renderNoParams(String command) {
        switch (command) {
            case BREWERY_LIST:
                System.out.println("양조장목록 요청");
                return;
            case STYLE_LIST:
                System.out.println("스타일목록 요청");
                return;
            case OUT_BEER_LIST:
                System.out.println("퇴출목록 요청");
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