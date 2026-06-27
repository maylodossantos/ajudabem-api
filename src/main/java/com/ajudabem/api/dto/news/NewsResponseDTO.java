package com.ajudabem.api.dto.news;

import com.ajudabem.api.domains.news.News;
import com.ajudabem.api.dto.user.UserResponseSummaryDTO;

public record NewsResponseDTO (Long id, String title, String subtitle, UserResponseSummaryDTO author, String content, String cover_image) {
    public static NewsResponseDTO fromEntity(News news) {
        return new NewsResponseDTO(
                news.getId(),
                news.getTitle(),
                news.getSubtitle(),
                UserResponseSummaryDTO.fromEntity(news.getAuthor()),
                news.getContent(),
                news.getCover_image()
        );
    }
}
