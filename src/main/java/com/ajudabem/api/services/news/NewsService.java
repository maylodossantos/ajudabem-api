package com.ajudabem.api.services.news;


import com.ajudabem.api.domains.news.News;
import com.ajudabem.api.domains.user.User;
import com.ajudabem.api.dto.news.NewsRequestDTO;
import com.ajudabem.api.dto.news.NewsResponseDTO;
import com.ajudabem.api.repositories.NewsRepository;
import com.ajudabem.api.services.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final CurrentUserService currentUserService;
    private final NewsRepository repository;

    public NewsResponseDTO createNews(NewsRequestDTO dto) {
        User user = currentUserService.get();
        News newNews = new News();

        newNews.setAuthor(user);
        newNews.setContent(dto.content());
        newNews.setTitle(dto.title());
        newNews.setSubtitle(dto.subtitle());
        newNews.setCover_image(dto.cover_image());

        repository.save(newNews);

        return NewsResponseDTO.fromEntity(newNews);
    }

    public NewsResponseDTO updateNews(NewsRequestDTO dto, Long id) {

        News news = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("News is not exists"));

        if(dto.content() != null) news.setContent(dto.content());
        if(dto.title() != null) news.setTitle(dto.title());
        if(dto.subtitle() != null) news.setSubtitle(dto.subtitle());
        if(dto.cover_image() != null) news.setCover_image(dto.cover_image());

        repository.save(news);

        return NewsResponseDTO.fromEntity(news);
    }

    public List<NewsResponseDTO> getAll() {
        List<News> newsList = repository.findAll();

        return newsList.stream()
                .map(NewsResponseDTO::fromEntity)
                .toList();
    }

    public NewsResponseDTO getNews(Long id) {
        News news = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("News is not exists"));

        return NewsResponseDTO.fromEntity(news);
    }
}
