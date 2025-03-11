package zjut.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zjut.entity.Movieinfo;
import zjut.service.MovieinfoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/movieinfo")

//@ResponseBody
public class MovieinfoController {
    private int pageSize=24;
    @Autowired
    private MovieinfoService MovieinfoService;
    @RequestMapping(value = "/findAll")
    @ResponseBody
    public String findAll(@RequestParam(value = "pageNum") Integer pageNum) throws IOException
    //List<Movieinfo> String PageInfo<Movieinfo>
    //@RequestBody Map<String,Object> map
    {
        System.out.println(pageNum);
//        try { Thread.sleep(3000) ;
//        } catch (InterruptedException ie){}
        PageHelper.startPage(pageNum, pageSize);
        List<Movieinfo> movieList = MovieinfoService.findAll(pageNum, pageSize);
        PageInfo<Movieinfo> info = new PageInfo<Movieinfo>(movieList);
//        System.out.println(movieList);
//        System.out.println(info);
//        System.out.println(info.getList());
//        System.out.println(info.getList().get(0));
        String json = new ObjectMapper().writeValueAsString(info);
//        System.out.println("json");
//        System.out.println(json);
        return json;
    }
    @RequestMapping(value = "/getMovieByDirector/{director}", method = RequestMethod.GET)
    @ResponseBody
    public List<Movieinfo> getMovieByDirector(@Param("director") @PathVariable("director") String director, Model model)
    //ModelAndView String List<Movieinfo>
    {
        List<Movieinfo> movieList = MovieinfoService.getMovieByDirector(director);
        return movieList;
    }
    @RequestMapping(value = "/getMovieByGenre/{cid}/{choose}")
    @ResponseBody
    public PageInfo<Movieinfo> getMovieByGenre(@PathVariable("cid") String cid, @PathVariable("choose") String choose, @RequestParam(value = "pageNum") Integer pageNum) throws IOException
    //ModelAndView String List<Movieinfo>
    {
        List<Movieinfo> movieList = new ArrayList<Movieinfo>();
        Integer id = Integer.valueOf(cid);
        Integer id2 = Integer.valueOf(choose);
//        String [] categories = {
//                0: '全部', 1: '剧情', 2: '喜剧', 3: '动作',
//            4: '爱情', 5: '科幻', 6: '动画', 7: '悬疑',
//            8: '惊悚', 9: '恐怖', 10: '犯罪', 11: '同性',
//            12: '音乐', 13: '歌舞', 14: '传记', 15: '历史',
//            16: '战争', 17: '西部', 18: '奇幻', 19: '冒险',
//            20: '灾难', 21: '武侠', 22: '情色'
//};
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("全部");categories.add("剧情");categories.add("喜剧");categories.add("动作");
        categories.add("爱情");categories.add("科幻");categories.add("动画");categories.add("悬疑");
        categories.add("惊悚");categories.add("恐怖");categories.add("犯罪");categories.add("同性");
        categories.add("音乐");categories.add("歌舞");categories.add("传记");categories.add("历史");
        categories.add("战争");categories.add("西部");categories.add("奇幻");categories.add("冒险");
        categories.add("灾难");categories.add("武侠");categories.add("情色");categories.add("家庭");
        ArrayList<String> keys = new ArrayList<String>();
        keys.add("rate");keys.add("popular");
        System.out.println("pageNum");
        System.out.println(pageNum);
        System.out.println(id);
        System.out.println(id2);
        //System.out.println(keys.get(id2-1));
//        try { Thread.sleep(10000) ;
//        } catch (InterruptedException ie){}
        PageHelper.startPage(pageNum, pageSize);
        if((id == 0 && id2 == 1) || (id == 0 && id2 == 0)){
            System.out.println("categories situation1");
            movieList = MovieinfoService.SortByRateAll(pageNum, pageSize);
        }
        else if(id == 0 && id2 ==2){
            System.out.println("categories situation2");
            movieList = MovieinfoService.SortByPopularAll(pageNum, pageSize);
        }
        else if(id2 == 1){
            System.out.println("categories situation3");
            System.out.println(keys.get(0));
            //String genrejson = new ObjectMapper().writeValueAsString(categories.get(id));
            movieList = MovieinfoService.SortGenreByRate('%'+categories.get(id)+'%', pageNum, pageSize);
            //movieList = MovieinfoService.SortGenreByRate(genrejson, pageNum, pageSize);
        }
        else if(id2 == 2){
            System.out.println("categories situation4");
            System.out.println(keys.get(1));
            movieList = MovieinfoService.SortGenreByPopular('%'+categories.get(id)+'%', pageNum, pageSize);
        }
        else{
            System.out.println("categories situation5");
            //movieList = MovieinfoService.findAll();
        }
        PageInfo<Movieinfo> info = new PageInfo<Movieinfo>(movieList);
        String json = new ObjectMapper().writeValueAsString(info);
        System.out.println(info);
        return info;
    }
}

