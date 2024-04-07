package dev.boot.service;

import dev.boot.domain.Article;
import dev.boot.dto.ArticleDTO;
import dev.boot.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;



    public ArticleDTO save(ArticleDTO articleDTO) {
        Article article = articleRepository.save(modelMapper.map(articleDTO, Article.class));
        return modelMapper.map(article, ArticleDTO.class);

    }

    public Set<ArticleDTO> findAll() {
        return StreamSupport.stream(articleRepository.findAll().spliterator(), false)
                .map(article -> modelMapper.map(article, ArticleDTO.class)).collect(Collectors.toSet());
    }


    //პროგრამის ყოველ გაშვებაზე რომ შენახული მონაცემები რესეტდებოდეს
    @PostConstruct
    public void deleteAllArticles() {
        articleRepository.deleteAll();
    }

    public Set<ArticleDTO> search(String query) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(buildMultiMatchQuery(query))
                .build();

        SearchHits<Article> searchHits = elasticsearchRestTemplate.search(searchQuery, Article.class);
        return searchHits.getSearchHits().stream()
                .map(searchHit -> modelMapper.map(searchHit.getContent(), ArticleDTO.class))
                .collect(Collectors.toSet());
    }

    private MultiMatchQueryBuilder buildMultiMatchQuery(String query) {
        return QueryBuilders.multiMatchQuery(query, "title", "names", "content")
                .fuzziness(Fuzziness.AUTO);
    }

//    public Set<ArticleDTO> findByReleaseYear(int releaseYear) {
//        return filterByReleaseYear(releaseYear).stream()
//                .map(article -> modelMapper.map(article, ArticleDTO.class))
//                .collect(Collectors.toSet());
//    }

    public Set<ArticleDTO> filterByReleaseYear(int releaseYear){
        Set<ArticleDTO> articleDTOS = findAll();
        return articleDTOS.stream()
                .filter(articleDTO -> articleDTO.getReleaseYear().getYear() == releaseYear)
                .collect(Collectors.toSet());
    }

    public Set<ArticleDTO> filterByAuthor(String authorName){
        Set<ArticleDTO> articleDTOS = findAll();
        return articleDTOS.stream()
                .filter(articleDTO ->
                        Objects.equals(articleDTO.getAuthor(), authorName))
                .collect(Collectors.toSet());
    }

    public Set<ArticleDTO> filterByNames(String name){
        Set<ArticleDTO> articleDTOS = findAll();
        return articleDTOS.stream()
                .filter(articleDTO ->
                        articleDTO.getNames().contains(name))
                .collect(Collectors.toSet());
    }

}
