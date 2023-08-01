package model.style;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
public class Style {
    private Integer id; // Primary Key
    private Integer breweryId ; // Style(1) : Brewery(1)
    private String name;
    private Timestamp createdAt;

    @Builder
    public Style(int id, int breweryId, String name, Timestamp createdAt){
        this.id = id;
        this.breweryId = breweryId;
        this.name = name;
        this.createdAt = createdAt;
    }
}
