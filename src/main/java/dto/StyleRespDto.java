package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;



@AllArgsConstructor
@ToString
@Builder
@Getter
public class StyleRespDto {
    Integer id;
    Integer breweryId;
    String styleName;
    String breweryName;
}
