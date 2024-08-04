package bg.moviebox.service.impl;

import bg.moviebox.model.dtos.AddNewsDTO;
import bg.moviebox.model.entities.News;
import bg.moviebox.model.enums.NewsType;
import bg.moviebox.repository.NewsRepository;
import bg.moviebox.service.NewsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;

    public NewsServiceImpl(NewsRepository newsRepository, ModelMapper modelMapper) {
        this.newsRepository = newsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createNews(AddNewsDTO addNewsDTO) {
        newsRepository.save(map(addNewsDTO));
    }

    @Override
    public void deleteNews(Long newsId) {
        newsRepository.deleteById(newsId);
    }



    private News map(AddNewsDTO addNewsDTO) {
        return modelMapper.map(addNewsDTO, News.class);
    }
}
