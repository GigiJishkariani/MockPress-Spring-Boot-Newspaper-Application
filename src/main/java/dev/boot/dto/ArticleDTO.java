package dev.boot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.boot.domain.Area;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ArticleDTO {

    private String Id;

    private String title;

    private String content;

    private String author;

    private LocalDate releaseYear;

    private LocalDate lastUpdateDate;

    private Area area;

    private Set<String> names;


    @JsonIgnore
    public void setId(String id) {
        Id = id;
    }
}
