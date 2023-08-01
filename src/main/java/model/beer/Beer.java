package model.beer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
@ToString
@Builder
@AllArgsConstructor
@Getter
public class Beer {
    private Integer id; // Primary Key
    private Integer style_id;
    private String name;
    private Timestamp createdAt;

}
