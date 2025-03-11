package zjut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zjut.entity.*;
import zjut.mapper.MovidMapper;

import java.util.List;
@Service
public class MovidServiceImpl implements MovidService{
    @Autowired
    private MovidMapper movidMapper;

    @Override
    public Currentuser getCurrentUser() {
        return movidMapper.findCurrentUser();
    }

    @Override
    public String getMovieID(int movieNo) {
        return movidMapper.getMovieID(movieNo);
    }

    @Override
    public Movieinfo getMovieinfo(String movieID) {
        return movidMapper.getMovieinfo(movieID);
    }

    @Override
    public List<Ratinfolike> getlikeinfo() {
        return movidMapper.getlikeinfo();
    }
}
