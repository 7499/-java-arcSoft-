package com.papi.java_face.service.impl;

import com.arcsoft.face.FaceInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.papi.java_face.dao.FFaceDao;
import com.papi.java_face.dao.FUserDao;
import com.papi.java_face.dto.User;
import com.papi.java_face.entity.FFace;
import com.papi.java_face.entity.FUser;
import com.papi.java_face.service.FUserService;
import com.papi.java_face.utils.FaceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @Author: 王杰
 * @Date: 2023/2/3 20:50
 */
@Service
public class FUserServiceImpl extends ServiceImpl<FUserDao, FUser> implements FUserService {

    @Autowired
    private FaceUtil faceUtil;
    @Autowired(required = false)
    private FFaceDao fFaceDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String register(User fUser) {

        QueryWrapper<FUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FUser::getUserName,fUser.getUserName());

        FUser dbUser = baseMapper.selectOne(queryWrapper);
        if(dbUser != null) {
            QueryWrapper<FFace> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(FFace::getUserId,dbUser.getId());
            FFace fFace = fFaceDao.selectOne(queryWrapper2);
            if (fFace == null) {
                fFace = new FFace();
                fFace.setFaceInfo(fUser.getFaceInfoBase64());
                fFace.setUserId(dbUser.getId());
                fFaceDao.insert(fFace);
                return "注册成功";
            }

            List<FFace> allFaceData = fFaceDao.selectList(new QueryWrapper<FFace>());
            List<String> allFaceBase64Data = allFaceData.stream().map(FFace::getFaceInfo).collect(toList());

            boolean sameUser = faceUtil.compareFaceFeature(fUser.getFaceInfoBase64(), allFaceBase64Data);

            if(sameUser) {
                return "注册成功";
            } else {

                byte[] bytes = faceUtil.loadUserFace(fUser.getFaceInfoBase64());

                if(bytes == null) {
                    return "请确保只有一人在取景框中";
                }

                fFace.setFaceInfo(fUser.getFaceInfoBase64());
                fFaceDao.updateById(fFace);
                return "已为您更新特征信息";
            }
        } else {
            dbUser = new FUser();
            dbUser.setAccount(fUser.getUserName());
            dbUser.setUserName(fUser.getUserName());
            baseMapper.insert(dbUser);

            FFace fFace = new FFace();
            fFace.setFaceInfo(fUser.getFaceInfoBase64());
            fFace.setUserId(dbUser.getId());
            fFaceDao.insert(fFace);
            return "注册成功";

        }

    }


    @Override
    public String login(User fUser) {

        if(fUser.getUserName() == null || "".equals(fUser.getUserName())) {

            List<FFace> allFaceData = fFaceDao.selectList(new QueryWrapper<FFace>());
            List<String> allFaceBase64Data = allFaceData.stream().map(FFace::getFaceInfo).collect(toList());

            int sameUser = faceUtil.compareFaceFeature2(fUser.getFaceInfoBase64(), allFaceBase64Data);

            if(sameUser >= 0) {

                FFace same = allFaceData.get(sameUser);

                QueryWrapper<FUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(FUser::getId,same.getUserId());

                FUser dbUser = baseMapper.selectOne(queryWrapper);
                return "你好，"+dbUser.getUserName();
            }


            return "未查询到此用户特征数据";

        } else {
            QueryWrapper<FUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FUser::getUserName,fUser.getUserName());

            FUser dbUser = baseMapper.selectOne(queryWrapper);

            if (dbUser == null) {
                return "未查询到此用户";
            }

            QueryWrapper<FFace> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(FFace::getUserId,dbUser.getId());
            FFace fFace = fFaceDao.selectOne(queryWrapper2);
            if (fFace == null) {
                return "未查询到此用户特征数据";
            }

            List<FFace> allFaceData = fFaceDao.selectList(new QueryWrapper<FFace>());
            List<String> allFaceBase64Data = allFaceData.stream().map(FFace::getFaceInfo).collect(toList());

            boolean sameUser = faceUtil.compareFaceFeature(fUser.getFaceInfoBase64(), allFaceBase64Data);

            if (sameUser) {
                return "你好，"+dbUser.getUserName();
            } else {
                return "特征数据匹配失败";
            }
        }
    }
}
