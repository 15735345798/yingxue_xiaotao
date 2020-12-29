package com.baizhi.service;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.VideoMapper;
import com.baizhi.entity.UserExample;
import com.baizhi.entity.Video;
import com.baizhi.entity.VideoExample;
import com.baizhi.po.VideoPO;
import com.baizhi.po.VideoVO;
import com.baizhi.util.AliyunOSSUtil;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author:xiaotao
 * @time 2020/12/23-17:48
 */
@Transactional
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    VideoMapper videoMapper;

    @Override
    public HashMap<String, Object> queryVideoPage(Integer page, Integer rows) {
        // Integer page, Integer rows(每页展示条数)
        //返回  page=当前页   rows=[User,User]数据    tolal=总页数   records=总条数
        HashMap<String, Object> map = new HashMap<>();

        //设置当前页
        map.put("page",page);
        //创建条件对象
        UserExample example = new UserExample();

        example.setOrderByClause("upload_time desc"); //排序查询
        //创建分页对象   参数：从第几条开始，展示几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //查询数据
        List<Video> video = videoMapper.selectByExampleAndRowBounds(example, rowBounds);

        map.put("rows",video);

        //查询总条数
        int records = videoMapper.selectCountByExample(example);
        map.put("records",records);

        //计算总页数
        Integer tolal=records%rows==0?records/rows:records/rows+1;
        map.put("total",tolal);

        return map;
    }

    @Override
    public String add(Video video) {
        String uuid=UUIDUtil.getUUID();
        video.setId(uuid);
        video.setUploadTime(new Date());
        video.setLikeCount(0);
        video.setPalyCount(0);
        videoMapper.insertSelective(video);
        return video.getId();
    }

    @Override
    public void uploadVideoCover(MultipartFile videoPath, String id, HttpServletRequest request) {
        String filename = videoPath.getOriginalFilename();
        //拼接时间戳  2341423424-动画.mp4
        String newName=new Date().getTime()+"-"+filename;
        //拼接视频名   video/2341423424-动画.mp4
        String objectName="video/"+newName;

        /*1.上传至阿里云
         * 将文件上传至阿里云
         * 参数：
         *   headImg：MultipartFile类型的文件
         *   bucketName:存储空间名
         *   objectName:文件名
         * */
        AliyunOSSUtil.uploadBytesFile(videoPath,"xiaotao-yingxue",objectName);

        //根据视频名拆分    0:2341423424-动画    1:mp4
        String[] split = newName.split("\\.");
        //获取视频名字  0:2341423424-动画   cover/2341423424-动画.jpg
        String coverName="cover/"+split[0]+".jpg";

        AliyunOSSUtil.videocaptureFile("xiaotao-yingxue",objectName,coverName);

        //5.修改数据
        //修改的条件
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);

        Video video = new Video();
        video.setId(id);
        video.setCoverPath("http://xiaotao-yingxue.oss-cn-beijing.aliyuncs.com/"+coverName); //设置封面
        video.setVideoPath("http://xiaotao-yingxue.oss-cn-beijing.aliyuncs.com/"+objectName); //设置视频地址

        //修改
        videoMapper.updateByExampleSelective(video, example);
    }
    @AddLog(value="修改视频")
    @Override
    public String edit(Video video) {
        videoMapper.updateByPrimaryKeySelective(video);
        return video.getId();
    }

    @AddLog(value="删除视频")
    @Override
    public void del(Video video) {
        //设置条件
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(video.getId());
        //根据id查询视频数据
        Video videos = videoMapper.selectOneByExample(example);

        //1.删除数据
        videoMapper.deleteByExample(example);


        //获取视频名字并拆分
        String videoName=videos.getVideoPath().replace("http://xiaotao-yingxue.oss-cn-beijing.aliyuncs.com/","");
        //获取封面名字并拆分
        String coverName=videos.getCoverPath().replace("http://xiaotao-yingxue.oss-cn-beijing.aliyuncs.com/","");

        //2.删除视频
        AliyunOSSUtil.deleteFile("xiaotao-yingxue",videoName);
        //3.删除封面
        AliyunOSSUtil.deleteFile("xiaotao-yingxue",coverName);

    }

    @Override
    public List<VideoVO> queryByReleaseTime() {
        List<VideoPO> videoPOList = videoMapper.queryByReleaseTime();

        ArrayList<VideoVO> videoVOS = new ArrayList<>();

        for (VideoPO videoPO : videoPOList) {

            //封装VO对象
            VideoVO videoVO = new VideoVO(
                    videoPO.getId(),
                    videoPO.getTitle(),
                    videoPO.getCoverPath(),
                    videoPO.getVideoPath(),
                    videoPO.getUploadTime(),
                    videoPO.getDescription(),
                    18,  //根据videoPO中的视频id  redis  查询视频点赞数
                    videoPO.getCateName(),
                    videoPO.getHeadImg()
            );
            //将vo对象放入集合
            videoVOS.add(videoVO);
        }

        return videoVOS;
    }


}
