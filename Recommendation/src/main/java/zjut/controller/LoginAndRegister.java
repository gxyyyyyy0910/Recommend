package zjut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zjut.entity.*;
import zjut.service.*;
import zjut.result.Result;
import zjut.service.LogAndRegService;
@Controller
@CrossOrigin
@RequestMapping("/user")
public class LoginAndRegister {
    @Autowired
    private LogAndRegService logAndRegService;
    @Autowired
    private MovidService movidService;
    @PostMapping(value = "/login")
    @ResponseBody
    public Result login(@RequestBody Userid tempuser) {
        String id= tempuser.userID;
        String password= tempuser.password;
        try{
            if(logAndRegService.login(id,password)){
                int no=movidService.getCurrentUser().no_user;
                if(logAndRegService.judgeifnew(no,id))
                {
                    System.out.println(no);
                    return new Result(201,"login successfully");
                }
                else
                {
                    return new Result(200,"login successfully");
                }
            }
            else
                return new Result(300, "账号或密码错误");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400,e.getMessage());
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestBody Userid tempuser)
    {
        String id= tempuser.userID;
        String password= tempuser.password;
        try {
            if(logAndRegService.register(id, password)) {
                return new Result(200, "register successfully.");
            }
            else
                return new Result(300, "the id exists.");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(400,e.getMessage());
        }
    }

    @GetMapping(value = "/logout")
    @ResponseBody
    public Result logout() {
        try{
            logAndRegService.logout();
            return new Result(200,"logout successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400,e.getMessage());
        }
    }

    @GetMapping(value = "/preference/{pre}")
    @ResponseBody
    public Result setpreference(@PathVariable("pre") String pre) {
        try {
            int no = movidService.getCurrentUser().no_user;
            System.out.println(no);
            logAndRegService.setpreference(no, pre);
            return new Result(200,"set successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, e.getMessage());
        }
    }

    @GetMapping(value="/privacyp/{ff}")
    @ResponseBody
    public Result privacyp(@PathVariable("ff") String ff){
        try{
            int f=Integer.valueOf(ff);
            int no = movidService.getCurrentUser().no_user;
            logAndRegService.setfp(no,f);
            return new Result(200,"set successfully");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(400, e.getMessage());
        }
    }

    @GetMapping(value="/privacyh/{ff}")
    @ResponseBody
    public Result privacyh(@PathVariable("ff") String ff){
        try{
            int f=Integer.valueOf(ff);
            int no = movidService.getCurrentUser().no_user;
            logAndRegService.setfh(no,f);
            return new Result(200,"set successfully");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(400, e.getMessage());
        }
    }

    @GetMapping(value = "/getprivacyp")
    @ResponseBody
    public int getprivacyp(){
        String id=movidService.getCurrentUser().userID;
        return logAndRegService.getfp(id);
    }

    @GetMapping(value = "/getprivacypp/{id}")
    @ResponseBody
    public int getprivacypp(@PathVariable("id") String id){
        return logAndRegService.getfp(id);
    }

    @GetMapping(value = "/getprivacyh")
    @ResponseBody
    public int getprivacyh(){
        String id=movidService.getCurrentUser().userID;
        return logAndRegService.getfh(id);
    }

    @GetMapping(value = "/getprivacyhh/{id}")
    @ResponseBody
    public int getprivacyhh(@PathVariable("id") String id){
        return logAndRegService.getfh(id);
    }
}
