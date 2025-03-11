package zjut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Select;
import zjut.entity.*;
import java.util.List;
@Mapper
public interface MovidMapper {
    @Select("select * from currentuser")
    public Currentuser findCurrentUser();
    @Select("select movieID from movid where no_movie =#{movieNo}")
    public String getMovieID(int movieNo);
    @Select("select * from movieinfo where movieID=#{movieID}")
    public Movieinfo getMovieinfo(String movieID);
    @Select(" select * from ratinfolike")
    public List<Ratinfolike> getlikeinfo();
    @Select("select userID from userid where no_user =#{userno}")
    public String getUserID(int userno);
    @Select(" select * from movieinfo\n" +
            "    where genre LIKE '%${tag}%'\n" +
            "    order by rate desc\n" +
            "    LIMIT 5")
    public List<Movieinfo> recmoviebytag(@Param("tag") String tag);
    @Select("select userid from tag\n" +
            "    where pre=#{tag}")
    public List<String> recfriendbytag(@Param("tag")String tag);
}
