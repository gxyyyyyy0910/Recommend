package zjut.controller;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zjut.entity.Movieinfo;
import zjut.service.MovidService;
import zjut.service.recoMovie;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
@Controller
@CrossOrigin
@RequestMapping("/movie")
public class MovieRecommend {
    @Autowired
    private MovidService movidService;
    @Autowired
    private recoMovie rec;
    private long no;

    //    @RequestMapping("/findAll")
//    public ModelAndView findAll()
//    {
//        ModelAndView modelAndView=new ModelAndView();
//        modelAndView.setViewName("index");
//        modelAndView.addObject("list",movidService.findAll());
//        return modelAndView;
//    }
    @GetMapping("/movieRecommend")
    @ResponseBody
    public List<Movieinfo> movieRecommend() throws Exception {
        List<Movieinfo> movieResult = new LinkedList<>();
        int flag = movidService.getCurrentUser().flag;
        if (flag == 0) {
            no = movidService.getCurrentUser().no_user;
            List<RecommendedItem> resultList;
            resultList = rec.movieRecommend(no);
            int tempno;
            String tempid;
            for (int i = 0; i < resultList.size(); i++) {
                tempno = (int) (resultList.get(i).getItemID());
                tempid = movidService.getMovieID(tempno);
                movieResult.add(movidService.getMovieinfo(tempid));
            }
            return movieResult;
        } else {
            String id = movidService.getCurrentUser().userID;
            String[] pre = rec.getpre(id);
            HashSet<Movieinfo> temp = new HashSet<>();
            for (int i = 0; i < pre.length; i++) {
                List<Movieinfo> movie = rec.recmoviebytag(pre[i]);
                for (int j = 0; j < movie.size(); j++) {
                    temp.add(movie.get(j));
                }
            }
            movieResult = new LinkedList<>(temp);
            return movieResult;
        }
    }
}
