package zjut.controller;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import zjut.entity.Friend;
import zjut.service.MovidService;
import zjut.service.UserService;
import zjut.service.recoMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/usera")
public class FriendsRecommend {
    @Autowired
    private recoMovie rec;
    @Autowired
    private MovidService movidService;
    @Autowired
    private UserService userService;
    @GetMapping("/friendsRecommend")
    @ResponseBody
    public List<Friend> findAll() throws Exception {
        int flag=movidService.getCurrentUser().flag;
        List<String> id=new LinkedList<>();
        if(flag==0)
        {
            long num=(long)movidService.getCurrentUser().no_user;
            id=rec.friendsRecommend(num);
        }
        else
        {
            String tempid=movidService.getCurrentUser().userID;
            String[] pre=rec.getpre(tempid);
            HashSet<String> temp=new HashSet<>();
            for(int i=0;i<pre.length;i++)
            {
                List<String> person=rec.recfriendbytag(pre[i]);
                for(int j=0;j<person.size();j++)
                {
                    temp.add(person.get(j));
                }
            }
            id=new LinkedList<>(temp);
        }
        List<Friend> result=new LinkedList<>();
        for(int i=0;i<id.size();i++)
        {
            List<String> texts = userService.getGenreList(id.get(i));
            //建立词频分析器，设置词频，以及词语最短长度，此处的参数配置视情况而定即可
            FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
            // 返回字数限制
            frequencyAnalyzer.setWordFrequenciesToReturn(600);
            // 最短字符
            frequencyAnalyzer.setMinWordLength(2);
            // 引入中文解析器
            frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
            // 设置词汇文本
            List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(texts);
            StringBuffer tag=new StringBuffer("");
            int count=0;
            for(int j=0;j< wordFrequencies.size();j++)
            {
                count++;
                tag.append(" "+wordFrequencies.get(j).getWord());
                if(count==3)
                    break;
            }
            String stringtag=new String(tag);
            Friend temp=new Friend();
            temp.userID= id.get(i);
            temp.tag=stringtag;
            result.add(temp);
        }
        return result;
    }
    @GetMapping("/createTags/{username}")
    @ResponseBody
    public List<String> tag(@PathVariable("username") String username){
        String[] pre=rec.getpre(username);
        List<String> result=new LinkedList<>();
        for(int i=0;i<pre.length;i++)
        {
            result.add(pre[i]);
        }
        return result;
    }
}
