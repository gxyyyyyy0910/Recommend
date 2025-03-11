package zjut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zjut.mapper.LogAndRegMapper;
import zjut.entity.*;
@Service
public class LogAndRegServiceImpl implements LogAndRegService{
    @Autowired
    private LogAndRegMapper logAndRegMapper;
    @Override
    public boolean register(String id, String password) {
        Userid tempuser=logAndRegMapper.findone(id);
        if(tempuser!=null){
            return false;
        }
        int num=logAndRegMapper.countnumber()+1;
        logAndRegMapper.register(num,id,password);
        return true;
    }

    @Override
    public boolean login(String id, String password) {
        Userid tempuser=logAndRegMapper.findone(id);
        if(tempuser==null)
        {
            return false;
        }
        else
        {
            if(!tempuser.password.equals(password))
                return false;
            else
            {
                System.out.println(tempuser);
                loginupdate(tempuser.no_user,id);
                return true;
            }
        }
    }

    @Override
    public void loginupdate(int userno, String id) {
        logAndRegMapper.delete();
        System.out.println(userno);
        logAndRegMapper.login(userno,id);
    }

    @Override
    public void logout() {
        logAndRegMapper.delete();
    }

    @Override
    public boolean judgeifnew(int no, String id) {
        int count=logAndRegMapper.countlike(no);
        System.out.println(count);
        if(count>15)
            return true;
        else
        {
            setpreflag();
            Userid tempuser=logAndRegMapper.findone(id);

            if(tempuser.preference!=null && !tempuser.preference.equals(""))
                return true;
            else
                return false;
        }
    }

    @Override
    public void setpreference(int no, String pre) {
        logAndRegMapper.setpreference(no,pre);
    }

    @Override
    public void setpreflag() {
        logAndRegMapper.setpreflag();
    }

    @Override
    public void setfp(int no, int f) {
        logAndRegMapper.setfp(no,f);
    }

    @Override
    public void setfh(int no, int f) {
        logAndRegMapper.setfh(no,f);
    }

    @Override
    public int getfp(String id) {
        return logAndRegMapper.findone(id).fp;
    }

    @Override
    public int getfh(String id) {
        return logAndRegMapper.findone(id).fh;
    }
}
