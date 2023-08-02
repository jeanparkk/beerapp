package model.outbeer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@Builder
@AllArgsConstructor
@Getter
public class OutBeer {
    private Integer id;
    private Integer beerId;
    private String reason;
    private Timestamp createdAt;
}
