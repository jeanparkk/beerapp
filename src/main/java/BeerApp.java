import db.DBConnection;
import view.BeerView;

import java.sql.Connection;
import java.util.Map;
import java.util.Scanner;

public class BeerApp {

    public static void main(String[] args) {
        beerAppStart();
    }

    private static void beerAppStart() {
        BeerView view = new BeerView();
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("\n어떤 기능을 요청하시겠습니까? (EXIT)");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("EXIT")) {
                break;
            }
            String[] commandAndParams = view.separateCommandAndParams(input);
            String command = commandAndParams[0];
            if (commandAndParams.length == 1) {
                view.renderNoParams(command);
            } else {
                try {
                    Map<String, Object> params = view.getParams(commandAndParams);
                    view.renderWithParams(command, params);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("BeerApp을 종료합니다.");
    }
}
