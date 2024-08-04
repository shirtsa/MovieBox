package bg.moviebox.service.impl;

import bg.moviebox.model.dtos.AddNewsDTO;
import bg.moviebox.model.entities.News;
import bg.moviebox.model.enums.NewsType;
import bg.moviebox.repository.NewsRepository;
import bg.moviebox.service.NewsService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
public class NewsServiceImpl implements NewsService {

    private final Logger LOGGER = LoggerFactory.getLogger(NewsService.class);
    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;
    private final Period retentionPeriod;

    public NewsServiceImpl(NewsRepository newsRepository,
                           ModelMapper modelMapper,
                           @Value("${news.retention.period}") Period retentionPeriod) {
        this.newsRepository = newsRepository;
        this.modelMapper = modelMapper;
        this.retentionPeriod = retentionPeriod;
    }

    @Override
    public void createNews(AddNewsDTO addNewsDTO) {
        newsRepository.save(map(addNewsDTO));
    }

    @Override
    public void deleteNews(Long newsId) {
        newsRepository.deleteById(newsId);
    }

    @Override
    public void cleanupOldNews() {
        Instant now = Instant.now();
        Instant deleteBefore = now.minus(retentionPeriod);
        LOGGER.info("Removing all news older than " + deleteBefore);
        newsRepository.deleteOldNews(deleteBefore);
        LOGGER.info("Old news were removed");
    }


    private News map(AddNewsDTO addNewsDTO) {
        return modelMapper.map(addNewsDTO, News.class);
    }
}
