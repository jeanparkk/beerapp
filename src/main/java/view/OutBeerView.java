package view;

import dto.OutBeerRespDto;
import service.OutBeerService;

import java.util.List;
import java.util.Map;

public class OutBeerView {

    private final OutBeerService outBeerService;

    public OutBeerView(OutBeerService outBeerService){

        this.outBeerService = outBeerService;
    }

    public void create(Map<String, Object> params) {
        try {
            int beerId = Integer.parseInt(params.get("beerId").toString());
            String reason = params.get("reason").toString();
            System.out.println(outBeerService.createOutBeer(beerId, reason));
        } catch (Exception e) {
            throw new RuntimeException("잘못된 요청입니다.");
        }
    }

    public void findOutBeers() {
        List<OutBeerRespDto> outBeers = outBeerService.getOutBeers();
        String[] tableHead = {"맥주Id" ,"이름", "단종사유", "단종일"};
        printRow(tableHead);
        for (OutBeerRespDto player : outBeers) {
            String[] tableData = {
                    player.getBeerId().toString(),
                    player.getBeerName(),
                    player.getOutReason(),
                    ViewFormatter.getFormatDateTime(player.getOutDate())
            };
            printRow(tableData);
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
