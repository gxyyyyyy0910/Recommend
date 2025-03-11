package zjut.service;
import zjut.entity.*;
import java.util.List;

public interface MovidService {
    public  Currentuser getCurrentUser();
    public String getMovieID(int movieNo);
    public Movieinfo getMovieinfo(String movieID);
    public List<Ratinfolike> getlikeinfo();
}
