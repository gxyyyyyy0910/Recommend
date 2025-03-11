package zjut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zjut.entity.Movieinfo;
import zjut.mapper.UserMapper;

import java.util.*;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Movieinfo> getWatchedMoviesByUserId(String id) {
        return userMapper.getWatchedMoviesByUserId(id);
    }

    @Override
    public int getWatchedMnumByUserId(String id) {
        return userMapper.getWatchedMnumByUserId(id);
    }

    @Override
    public List<String> getWordCloudText(String id) {
        List<String> texts = userMapper.getWordCloud(id);
        List<String> textList = new ArrayList();
        Set<String> textSet = new HashSet();

        for(String s : texts){
            //s = s.substring(1,s.length()-2);
            String[] ss = s.split(",");
            for(int i = 0;i < ss.length; i++){
                //ss[i] = ss[i].substring(1,ss[i].length()-1);
                ss[i] = ss[i].substring(0,ss[i].length());
                //textSet.add(ss[i]);
                textList.add(ss[i]);
            }

        }

        //for(String s : textSet) textList.add(s);
        return textList;
    }

    @Override
    public List<String> getGenreList(String id) {
        List<String> genres = userMapper.getGenreList(id);
        List<String> genreList = new ArrayList();
        Set<String> genreSet = new HashSet();

        for(String s : genres){
            if(s!=null){
                String[] ss = s.split(",");
                for(int i = 0;i < ss.length; i++){
                    //genreSet.add(ss[i]);
                    if(!ss[i].equals("剧情"))
                        genreList.add(ss[i]);
                }
            }
        }
        //for(String s : genreSet) genreList.add(s);
        return genreList;
    }

    @Override
    public Double getAvgRate(String id, String genre) {
        List<String> rates = userMapper.getAvgRate(id, genre);
        //List<Float> rateList = new ArrayList<Float>();
        int sum = 0;
        int n = 0;
        for(String s :rates){
            if(s!=null && !s.equals("none")){
                int i = Integer.parseInt(s);
                sum += i;
                n++;
            }
        }
        if(sum == 0){
            return (double)0;
        }
        else{
            return Math.round(sum/(double)n*100)/100.0;
        }

    }

    @Override
    @Transactional
    public List<Map<String,Object>> getMovieinfoByUserId(String cid, String id, int pageNum, int pageSize)
    {return userMapper.getMovieinfoByUserId(cid,id);}
}
