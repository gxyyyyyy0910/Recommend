package zjut.service;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import java.util.List;

import zjut.entity.Movieinfo;
public interface recoMovie {
    public List<RecommendedItem> movieRecommend(long userno) throws Exception ;
    public List<String> friendsRecommend(long userno) throws Exception ;
    public String[] getpre(String id);
    public List<Movieinfo> recmoviebytag(String tag);
    public List<String> recfriendbytag(String tag);
}
