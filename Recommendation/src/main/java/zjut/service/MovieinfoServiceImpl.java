package zjut.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zjut.entity.Movieinfo;
import zjut.mapper.MovieinfoMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;

@Service
public class MovieinfoServiceImpl implements MovieinfoService{

    @Autowired
    private MovieinfoMapper movieinfoMapper;
    @Override
    public List<Movieinfo> findAll(int pageNum, int pageSize)
    { return movieinfoMapper.findAll(); }
    @Override
    public List<Movieinfo> SortByRateAll(int pageNum, int pageSize)
    { return movieinfoMapper.SortByRateAll(); }
    @Override
    public List<Movieinfo> SortByPopularAll(int pageNum, int pageSize)
    { return movieinfoMapper.SortByPopularAll(); }
    @Override
    public List<Movieinfo> SortGenreByRate(String genre, int pageNum, int pageSize)
    { return movieinfoMapper.SortGenreByRate(genre); }
    @Override
    public List<Movieinfo> SortGenreByPopular(String genre,int pageNum, int pageSize)
    { return movieinfoMapper.SortGenreByPopular(genre); }
    @Override
    public List<Movieinfo> getMovieByDirector(@Param("director") String director)
    { return movieinfoMapper.getMovieByDirector(director);}
    @Override
    public List<Movieinfo> getMovieByGenre(String genre)
    { return movieinfoMapper.getMovieByGenre(genre); }
}
