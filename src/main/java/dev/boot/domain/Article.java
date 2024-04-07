package dev.boot.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Document(indexName = "articles")
public class Article {
    @Id
    private String Id;

    private String title;

    private String content;

    private String author;

    @Field(name = "@timestamp1", type = FieldType.Date)
    private LocalDate releaseYear;

    @Field(name = "@timestamp", type = FieldType.Date)
    private LocalDate lastUpdateDate;

    @Field(type = FieldType.Text, fielddata = true)
    private Area area;

    private Set<String> names;


}