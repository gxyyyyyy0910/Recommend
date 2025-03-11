package zjut.mapper;

import org.apache.ibatis.annotations.*;
import zjut.entity.*;
@Mapper
public interface LogAndRegMapper {
    @Insert("insert into userid(no_user,userID,password,fh,fp)" + " values(#{userno},#{id},#{password},1,1)")
    public void register(@Param("userno") int userno, @Param("id") String id, @Param("password") String password);
    @Select("select count(*) from userid")
    public int countnumber();
    @Select("SELECT * FROM userid WHERE userID=#{id} LIMIT 1")
    public Userid findone(@Param("id") String id);
    @Delete("delete from currentuser")
    public void delete();
    @Insert("insert into currentuser(no_user,userID,flag)" + "values(#{userno},#{id},0)")
    public void login(@Param("userno") int userno,@Param("id") String id);
    @Select("select count(*) from ratinfolike\n" +
            "where no_user=#{no}")
    public int countlike(@Param("no") int no);
    @Update(" update userid\n" +
            "    set preference=#{pre}\n" +
            "    where no_user=#{no}")
    public void setpreference(@Param("no") int no,@Param("pre") String pre);
    @Update("update currentuser\n" +
            "   set flag=1")
    public void setpreflag();
    @Update("update userid\n" +
            "    set fp=#{f}\n" +
            "    where no_user=#{no}")
    public void setfp(@Param("no") int no,@Param("f") int f);
    @Update(" update userid\n" +
            "    set fh=#{f}\n" +
            "    where no_user=#{no}")
    public void setfh(@Param("no") int no,@Param("f") int f);
}
