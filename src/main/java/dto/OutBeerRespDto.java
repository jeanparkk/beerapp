package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@Builder
@AllArgsConstructor
@Getter
public class OutBeerRespDto {
    Integer beerId;
    String beerName;
    String outReason;
    Timestamp outDate;
}
