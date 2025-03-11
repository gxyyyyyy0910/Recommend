package zjut.service;

import org.apache.ibatis.annotations.Param;
import zjut.entity.Movieinfo;

import java.util.List;

public interface MovieinfoService {
    public List<Movieinfo> findAll(int pageNum, int pageimport);
    public List<Movieinfo> SortByRateAll(int pageNum, int pageSize);
    public List<Movieinfo> SortByPopularAll(int pageNum, int pageSize);
    public List<Movieinfo> SortGenreByRate(String genre, int pageNum, int pageSize);
    public List<Movieinfo> SortGenreByPopular(String genre, int pageNum, int pageSize);
    List<Movieinfo> getMovieByDirector(@Param("director") String director);
    List<Movieinfo> getMovieByGenre(String genre);
}
