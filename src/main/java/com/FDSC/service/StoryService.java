package com.FDSC.service;


import com.FDSC.common.Result;
import com.FDSC.controller.dto.SlideShowDto;
import com.FDSC.controller.dto.StoryItemDto;
import com.FDSC.entity.Story;
import com.FDSC.mapper.AnnounceMapper;
import com.FDSC.mapper.StoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class StoryService extends ServiceImpl<StoryMapper, Story> {

    @Autowired
    private StoryMapper storyMapper;

    @Autowired
    private AnnounceMapper announceMapper;

    public List<String> getstorytag(String storyid){   return storyMapper.gettag(storyid);}


    public Result collectestory(String storyid, String userid) {
        try{
            return Result.success(storyMapper.collected(storyid,userid));
        }catch (Exception e){
            return Result.error("500","已收藏");
        }
    }

    public Result recommend() {
        try{
            return Result.success(storyMapper.getAllStoryItem());
        }catch (Exception e){
            return Result.error("403","获取失败");
        }
    }

    public Result slideShow() {
        try{
            return Result.success(announceMapper.getSlideShow());
        }catch (Exception e){
            return Result.error("403","获取失败");
        }
    }

    public Result usersStories(String userid) {
        try{
            List<StoryItemDto> list =storyMapper.usersStories(userid);
            for (StoryItemDto stoty :list){
                /*stoty.setLink("/APP/StoryInfo/?storyid="+stoty.getStoryId());*/
            }
            return Result.success(list);
        }
        catch (Exception e){
            return Result.error("403","获取失败");
        }
    }

    public Result usersCollectStories(String userid) {
        try{
            return Result.success(storyMapper.collectedStories(userid));
        }catch (Exception e){
            return Result.error("500","已收藏");
        }
    }
}
