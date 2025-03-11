package zjut.entity;

import lombok.Data;

@Data
public class Movieinfo {
    public String movieID;
    public String name;
    public String director;
    public String actor;
    public String genre;
    public String tag;
    public String summary;
    public float rate;
    public  Integer popular;
    public String cover;
}
