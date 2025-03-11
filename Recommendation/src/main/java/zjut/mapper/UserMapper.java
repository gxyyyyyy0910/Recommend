package zjut.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zjut.entity.Userid;
import zjut.entity.Movieinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    //根据用户id获取 该用户看过的电影
    @Select("select * from movieinfo WHERE movieID in (SELECT movieID from userrate where userID = #{id})")
    List<Movieinfo> getWatchedMoviesByUserId(@Param("id") String id);

    //根据用户id获取 该用户看过的电影数量
    @Select("select count(*) from movieinfo WHERE movieID in (SELECT movieID from userrate where userID = #{id})")
    int getWatchedMnumByUserId(@Param("id") String id);

    @Select("SELECT tag FROM movieinfo,userrate WHERE movieinfo.movieID = userrate.movieID AND userId = #{id}")
    List<String> getWordCloud(String var1);

    @Select("SELECT genre FROM movieinfo,userrate WHERE movieinfo.movieID = userrate.movieId AND userId = #{id}")
    List<String> getGenreList(String var1);

    @Select("SELECT userrate.rate FROM movieinfo,userrate WHERE movieinfo.movieID = userrate.movieId AND userId = #{id} AND genre LIKE #{genre}")
    List<String> getAvgRate(@Param("id") String id, @Param("genre") String genre);

    @Select("<script>"+
            "select movieinfo.movieID,name,director,actor,genre,tag,summary,movieinfo.rate,popular,cover,userrate.rate as urate from movieinfo, userrate\n" +
            "        where movieinfo.movieID=userrate.movieID\n" +
            "            <if test=\"id != null\">\n" +
            "                and userID = #{id}\n" +
            "            </if>\n" +
            "            <if test=\"cid != '%全部%'\">\n" +
            "                and genre LIKE #{cid}\n" +
            "            </if>"+"</script>"
    )
    List<Map<String,Object>> getMovieinfoByUserId(@Param("cid") String cid, @Param("id") String id);
}
