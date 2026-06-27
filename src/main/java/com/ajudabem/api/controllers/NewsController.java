package com.ajudabem.api.controllers;

import com.ajudabem.api.dto.news.NewsRequestDTO;
import com.ajudabem.api.dto.news.NewsResponseDTO;
import com.ajudabem.api.services.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<NewsResponseDTO> createNews(@RequestBody NewsRequestDTO body) {
        return ResponseEntity.ok(newsService.createNews(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> updateNews(@RequestBody NewsRequestDTO body, @PathVariable Long id) {
        return ResponseEntity.ok(newsService.updateNews(body, id));
    }

    @GetMapping
    public ResponseEntity<List<NewsResponseDTO>> getAllNews() {
        return ResponseEntity.ok(newsService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> getNewsById(@PathVariable Long id) {
        return  ResponseEntity.ok(newsService.getNews(id));
    }


}
