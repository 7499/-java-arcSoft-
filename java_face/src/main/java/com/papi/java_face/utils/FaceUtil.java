package com.papi.java_face.utils;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

/**
 * @Author: 王杰
 * @Date: 2023/2/3 21:10
 */
@Component
public class FaceUtil {

    @Value("${face.appId}")
    public String appId;
    @Value("${face.sdkKey}")
    public String sdkKey;
    @Value("${face.libDir}")
    public String libDir;
    @Value("${face.same}")
    public Double same;

    private FaceEngine faceEngine;

    private int errorCode;

    @PostConstruct
    public void init() {

        faceEngine = new FaceEngine(libDir);
        //激活引擎
        errorCode = faceEngine.activeOnline(appId, sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }


        ActiveFileInfo activeFileInfo=new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("获取激活文件信息失败");
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);


        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            throw new RuntimeException("初始化引擎失败");
        }


    }


    /**
     * 获取特征
     * @param userFaceBase64
     * @return
     */
    public byte[] loadUserFace(String userFaceBase64) {

        if (userFaceBase64.contains("data:")) {
            int start = userFaceBase64.indexOf(",");
            userFaceBase64 = userFaceBase64.substring(start + 1);
        }

        byte[] bytes = Base64.getDecoder().decode(userFaceBase64);

        //人脸检测
        ImageInfo imageInfo = getRGBData(bytes);
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>(); //可能会有多人 每个人作为list的一个item
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);

        if(faceInfoList.size() != 1) {
            return null;
        }

        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        System.out.println("特征值大小：" + faceFeature.getFeatureData().length);

        return faceFeature.getFeatureData();

    }

    /**
     * 比对特征
     * @param sourceFace
     * @param targetFace
     * @return
     */
    public boolean compareFaceFeature(String sourceFace,List<String> targetFace) {

        if (targetFace == null || targetFace.size() == 0) {
            return false;
        }

        byte[] source = loadUserFace(sourceFace);

        for (String item : targetFace) {

            byte[] target = loadUserFace(item);

            //特征比对
            FaceFeature targetFaceFeature = new FaceFeature();
            targetFaceFeature.setFeatureData(source);
            FaceFeature sourceFaceFeature = new FaceFeature();
            sourceFaceFeature.setFeatureData(target);
            FaceSimilar faceSimilar = new FaceSimilar();

            errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

            System.err.println("相似度：" + faceSimilar.getScore());

            if(faceSimilar.getScore() > same) {
                return true;
            }
        }

        return false;
    }
    public int compareFaceFeature2(String sourceFace,List<String> targetFace) {

        if (targetFace == null || targetFace.size() == 0) {
            return -1;
        }

        byte[] source = loadUserFace(sourceFace);

        for (int i = 0; i < targetFace.size(); i++) {

            byte[] target = loadUserFace(targetFace.get(i));

            //特征比对
            FaceFeature targetFaceFeature = new FaceFeature();
            targetFaceFeature.setFeatureData(source);
            FaceFeature sourceFaceFeature = new FaceFeature();
            sourceFaceFeature.setFeatureData(target);
            FaceSimilar faceSimilar = new FaceSimilar();

            errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

            System.err.println("相似度：" + faceSimilar.getScore());

            if(faceSimilar.getScore() > same) {
                return i;
            }
        }

        return -1;
    }

}
