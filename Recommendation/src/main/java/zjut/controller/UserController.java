package zjut.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import zjut.entity.Movieinfo;
import zjut.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/personalPage")
    public String getPersonalPage(HttpSession session, Model model) throws Exception {
        //User TemUser=(User)session.getAttribute(Constants.USER_SESSION);
        //String userId = TemUser.getUserId();
        String userId = "vinika";
        List<Movieinfo> userWatchedMovies = userService.getWatchedMoviesByUserId(userId);
        int userWatchedMnum = userService.getWatchedMnumByUserId(userId);
        //获取观影口味
        List<String> genreList = userService.getGenreList(userId);

        //model.addAttribute("TemUser",TemUser);
        model.addAttribute("userId",userId);
        model.addAttribute("userWatchedMovies",userWatchedMovies);
        model.addAttribute("userWatchedMnum",userWatchedMnum);
        model.addAttribute("genreList",genreList);

        return "/personalPage";
    }

    @RequestMapping({"/createWordCloud/{username}"})
    @ResponseBody
    public List<WordFrequency> createWordCloud(@PathVariable("username") String username, HttpSession session, HttpServletResponse response) throws IOException {
        //username="vinika";
        List<String> texts = userService.getWordCloudText(username);
        System.out.println(texts);
        //建立词频分析器，设置词频，以及词语最短长度，此处的参数配置视情况而定即可
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        // 返回字数限制
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        // 最短字符
        frequencyAnalyzer.setMinWordLength(2);
        // 引入中文解析器
        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
        // 设置词汇文本
        //String addr="D:\\code2\\innovation-practice\\src\\main\\resources\\1.txt";
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(texts);
        // 生成图片尺寸 width 1920  height 1080
        // 从 2000 2000 改了
        Dimension dimension = new Dimension(500, 500);
        // 生产词云形状
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        // 词与词的间距
        // 从5改为2
        wordCloud.setPadding(2);
        // 设置中文字体样式
        java.awt.Font font = new java.awt.Font("STSong-Light", 2, 80);
        // 生成字体
        wordCloud.setKumoFont(new KumoFont(font));
        // 设置背景颜色
        wordCloud.setBackgroundColor(new Color(250, 255, 241));
        //wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setBackground(new CircleBackground(200));
        //wordCloud.setBackground(new PixelBoundryBackground("1.png"));
        wordCloud.setFontScalar(new SqrtFontScalar(12, 45));
        // 生成字体颜色
        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.pink, Color.BLUE, Color.cyan, 30, 30));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        // 生成词云
        wordCloud.build(wordFrequencies);
        // 生成图片地址
        // wordCloud.writeToFile("D:\\job.png");
        System.out.print(wordFrequencies);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("wordcloud");
        //modelAndView.addObject("list",wordFrequencies);
        modelAndView.addObject("list",texts);
        //return modelAndView;
        return wordFrequencies;
    }

    @RequestMapping({"/createRadar/{username}"})
    @ResponseBody
    public List<WordFrequency> createRadar(@PathVariable("username") String username) throws IOException {
        //username="vinika";
        List<String> texts = userService.getGenreList(username);
        //建立词频分析器，设置词频，以及词语最短长度，此处的参数配置视情况而定即可
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        // 返回字数限制
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        // 最短字符
        frequencyAnalyzer.setMinWordLength(2);
        // 引入中文解析器
        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
        // 设置词汇文本
        //String addr="D:\\code2\\innovation-practice\\src\\main\\resources\\1.txt";
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(texts);
        // 生成图片尺寸 width 1920  height 1080
        // 从 2000 2000 改了
        Dimension dimension = new Dimension(500, 500);
        // 生产词云形状
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        // 词与词的间距
        // 从5改为2
        wordCloud.setPadding(2);
        // 设置中文字体样式
        java.awt.Font font = new java.awt.Font("STSong-Light", 2, 80);
        // 生成字体
        wordCloud.setKumoFont(new KumoFont(font));
        // 设置背景颜色
        wordCloud.setBackgroundColor(new Color(250, 255, 241));
        //wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setBackground(new CircleBackground(200));
        //wordCloud.setBackground(new PixelBoundryBackground("1.png"));
        wordCloud.setFontScalar(new SqrtFontScalar(12, 45));
        // 生成字体颜色
        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.pink, Color.BLUE, Color.cyan, 30, 30));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        // 生成词云
        wordCloud.build(wordFrequencies);
        System.out.println("————————————以下是雷达图————————————");
        System.out.println(wordFrequencies);
        return wordFrequencies;
    }

    @RequestMapping({"/createAvgList/{username}"})
    @ResponseBody
    public List<Double> createAvgList(@PathVariable("username") String username) throws IOException {
        List<WordFrequency> wordFrequencies = createRadar(username);
        List<Double> avgList = new ArrayList<Double>();
        for(WordFrequency s :wordFrequencies){
            String genre = s.getWord();
            Double avg = userService.getAvgRate(username,'%'+genre+'%');
            avgList.add(avg);
        }
        return avgList;
    }

    @RequestMapping({"/createHistory/{cid}/{username}"})
    @ResponseBody
    public PageInfo<Map<String,Object>> createHistory(@PathVariable("cid") String cid, @PathVariable("username") String username, @RequestParam(value = "pageNum") Integer pageNum)throws IOException
    //, @RequestParam(value = "pageNum") Integer pageNum) throws IOException
    {
        System.out.println("cid="+cid);
        Integer id = Integer.valueOf(cid);
        PageHelper.startPage(pageNum, 10);
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("全部");categories.add("剧情");categories.add("喜剧");categories.add("动作");
        categories.add("爱情");categories.add("科幻");categories.add("动画");categories.add("悬疑");
        categories.add("惊悚");categories.add("恐怖");categories.add("犯罪");categories.add("同性");
        categories.add("音乐");categories.add("歌舞");categories.add("传记");categories.add("历史");
        categories.add("战争");categories.add("西部");categories.add("奇幻");categories.add("冒险");
        categories.add("灾难");categories.add("武侠");categories.add("情色");categories.add("家庭");
        String ncid = categories.get(id);
        System.out.println('%'+ncid+'%');
        List<Map<String,Object>> res=userService.getMovieinfoByUserId('%'+ncid+'%', username,pageNum,10);
        PageInfo<Map<String,Object>> info = new PageInfo<Map<String,Object>>(res);
        String json = new ObjectMapper().writeValueAsString(info);
        System.out.println(json);
        return info;
    }
}
