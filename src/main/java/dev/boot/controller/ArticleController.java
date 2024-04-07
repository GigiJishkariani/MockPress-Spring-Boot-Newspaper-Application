package dev.boot.controller;

import dev.boot.dto.ArticleDTO;
import dev.boot.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService service;

    @Autowired
    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ArticleDTO> save(@RequestBody ArticleDTO articleDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(articleDTO));
    }


    @GetMapping
    public ResponseEntity<Set<ArticleDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Set<ArticleDTO>> search(@RequestParam String query) {
        Set<ArticleDTO> searchResults = service.search(query);
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/filter/releaseYear")
    public ResponseEntity<Set<ArticleDTO>> filterByReleaseYear(@RequestParam int releaseYear) {
        Set<ArticleDTO> filteredArticles = service.filterByReleaseYear(releaseYear);
        return ResponseEntity.ok(filteredArticles);
    }

    @GetMapping("/filter/author")
    public ResponseEntity<Set<ArticleDTO>> filterByAuthor(@RequestParam String authorName){
        Set<ArticleDTO> filteredArticles = service.filterByAuthor(authorName);
        return ResponseEntity.ok(filteredArticles);
    }

    @GetMapping("/filter/name")
    public ResponseEntity<Set<ArticleDTO>> filterByNames(@RequestParam String name){
        Set<ArticleDTO> filteredArticles = service.filterByNames(name);
        return ResponseEntity.ok(filteredArticles);
    }
}
