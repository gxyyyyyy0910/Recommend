package zjut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import zjut.entity.Movieinfo;

import java.util.List;

@Mapper
public interface MovieinfoMapper {
    @Select("select * from movieinfo order by movieid DESC")
    public List<Movieinfo> findAll();
    @Select("select * from movieinfo order by rate DESC")
    public List<Movieinfo> SortByRateAll();
    @Select("select * from movieinfo order by popular DESC")
    public List<Movieinfo> SortByPopularAll();
    @Select("select * from movieinfo where genre LIKE #{genre} order by rate DESC")
    public List<Movieinfo> SortGenreByRate(String genre);
    @Select("select * from movieinfo where genre LIKE #{genre} order by popular DESC")
    public List<Movieinfo> SortGenreByPopular(String genre);
    @Select("select * from movieinfo where director LIKE '%${director}%' or genre LIKE '%${director}%' or name LIKE '%${director}%' or actor LIKE '%${director}%' or tag LIKE '%${director}%'")
    public List<Movieinfo> getMovieByDirector(@Param("director") String director);
    @Select("select * from movieinfo where genre LIKE #{genre}")
    public List<Movieinfo> getMovieByGenre(String genre);

}
