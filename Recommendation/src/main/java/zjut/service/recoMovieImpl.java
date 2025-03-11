package zjut.service;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zjut.entity.Movieinfo;
import zjut.mapper.LogAndRegMapper;
import zjut.mapper.MovidMapper;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

@Service
public class recoMovieImpl implements recoMovie{
    @Override
    public List<RecommendedItem> movieRecommend(long userno) throws Exception{
        File file=new File("D:\\Innovation\\ratInfo1.txt");//路径
        DataModel dataModel=new FileDataModel(file);//构造数据模型
        UserSimilarity similarity= new PearsonCorrelationSimilarity(dataModel);//用PearsonCorrelation 算法计算用户相似度
        UserNeighborhood userNeighborhood=new NearestNUserNeighborhood(10,similarity,dataModel);//计算用户的“邻居”，这里将与该用户最近距离为10的用户设置为该用户的“邻居”
        Recommender recommender=new GenericUserBasedRecommender(dataModel,userNeighborhood,similarity);//基于用户的推荐器
        List<RecommendedItem> recommendedItemList=recommender.recommend(userno,21);
        return recommendedItemList;
    }
    @Autowired
    private MovidMapper movidRepository;
    @Override
    public List<String> friendsRecommend(long userno) throws Exception {
        List<String> friendno=new LinkedList<>();
        File file=new File("D:\\Innovation\\ratInfo1.txt");//路径
        DataModel dataModel=new FileDataModel(file);
        UserSimilarity similarity= new PearsonCorrelationSimilarity(dataModel);
        UserNeighborhood userNeighborhood=new NearestNUserNeighborhood(6,similarity,dataModel);
        long[] userN = userNeighborhood.getUserNeighborhood(userno);
        for(int i=0;i<userN.length;i++)
        {
            friendno.add(movidRepository.getUserID((int)userN[i]));
        }
        return friendno;
    }
    @Autowired
    private LogAndRegMapper logAndRegRepository;
    @Override
    public String[] getpre(String id){
        String pre=logAndRegRepository.findone(id).preference;
        if(pre==null)pre="";
        String substring = pre.substring(0, pre.length());
        //以逗号分割，得出的数据存到 result 里面
        String[] result = substring.split(",");
        return result;
    }
    @Override
    public List<Movieinfo> recmoviebytag(String tag){
        return movidRepository.recmoviebytag(tag);
    }
    @Override
    public List<String> recfriendbytag(String tag){
        return movidRepository.recfriendbytag(tag);
    }
}
