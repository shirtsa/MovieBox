package bg.moviebox.service;

import bg.moviebox.model.dtos.AddNewsDTO;

public interface NewsService {

    void createNews(AddNewsDTO addNewsDTO);

    void deleteNews(Long newsId);

    void cleanupOldNews();

}
