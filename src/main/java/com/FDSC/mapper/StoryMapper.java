package com.FDSC.mapper;

import com.FDSC.controller.dto.StoryItemDto;
import com.FDSC.entity.Story;
import com.FDSC.mapper.dto.StoryLatestTempDto;
import com.FDSC.mapper.dto.StoryTempDto;
import com.FDSC.utils.SqlProvider;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface StoryMapper extends BaseMapper<Story> {

    @Select("Select * from story")
    public List<Story> getAll();

    @SelectProvider(type = SqlProvider.class, method = "search")
    public List<StoryTempDto> search(@Param("tagId") Long tagId, @Param("sort") String sort, @Param("page") Integer page);

    @SelectProvider(type = SqlProvider.class, method = "storyNum")
    public Integer getStoryNum(@Param("tagId") Long tagId);

    @Select("Select id as story_id, user_id, total_like, total_collection, total_comment, story_name, cover_url from story")
    public List<StoryItemDto> getAllStoryItem();

    @Select("SELECT id as story_id, story_name, update_time FROM story ORDER BY update_time DESC LIMIT 20")
    public List<StoryLatestTempDto> getLatestStory();

    @Select("Select id as story_id, user_id, total_like, total_collection, total_comment, story_name, cover_url " +
            "from story ORDER BY total_like DESC")
    public List<StoryItemDto> getHotStory();

    @Select("Select tag_name from tag,story_tag where tag.id=tag_id and story_id=#{storyid}")
    public List<String> gettag(String storyid);

    @Insert("Insert into story_collection(user_id,story_id) values(#{userid},#{storyid})")
    public boolean collected(String storyid, String userid);

    @Delete("Delete from story_collection where story_id=#{storyid} and user_id=#{userid}")
    boolean uncollected(String storyid, String userid);
    @Select("Select id as storyId,total_like,total_collection,total_comment,story_name,cover_url,story.user_id as userId " +
            " from story " +
            " where user_id=#{userid}")
    List<StoryItemDto> usersStories(String userid);

    @Select("Select story.id as storyId,total_like,total_collection,total_comment,story_name,cover_url,story.user_id as userId " +
            "from story,story_collection " +
            "where story.id=story_id " +
            "and story_collection.user_id=#{userid}")
    List<StoryItemDto>  collectedStories(String userid);

    @Select("Select distinct story.id as storyId,story.total_like,story.total_collection,story.total_comment,story_name,cover_url " +
            " from story,fragment " +
            " where story.user_id=#{userid} " +
            " or fragment.user_id=#{userid} and fragment.story_id=story.id")
    List<StoryItemDto> StoriessWithFragment(String userid);

    //@Insert("Insert story_tag(story_id,tag_id) values (#{storyid},#{tagid})")
    boolean settag(Long storyid, List<Integer> tags);

    @Select("Select count(*) from story_collection where user_id=#{userId} and story_id=#{storyId}")
    boolean checkCollect(String userId, String storyId);
}

