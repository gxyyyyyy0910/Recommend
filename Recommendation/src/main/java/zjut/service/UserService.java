package zjut.service;

import zjut.entity.Movieinfo;

import java.util.List;
import java.util.Map;

public interface UserService {

    //根据用户id获取 该用户看过的电影
    List<Movieinfo> getWatchedMoviesByUserId(String id);

    //根据用户id获取 该用户看过的电影数量
    int getWatchedMnumByUserId(String id);

    List<String> getWordCloudText(String var1);

    List<String> getGenreList(String var1);

    Double getAvgRate(String id, String genre);

    List<Map<String,Object>> getMovieinfoByUserId(String cid, String id, int pageNum, int pageSize);
}
