package model.brewery;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
public class Brewery {
    private Integer id; // Primary Key

    private String name;
    private Timestamp createdAt;

    @Builder
    public Brewery(int id, String name, Timestamp createdAt){
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }


}
