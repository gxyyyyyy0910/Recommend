package zjut.service;

public interface LogAndRegService {
    public boolean register(String id,String password);
    public boolean login(String id,String password);
    public void loginupdate(int userno,String id);
    public void logout();
    public boolean judgeifnew(int no,String id);
    public void setpreference(int no,String pre);
    public void setpreflag();
    public void setfp(int no,int f);
    public void setfh(int no,int f);
    public int getfp(String id);
    public int getfh(String id);
}
