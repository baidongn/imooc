package com.imooc.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName FileUpload
 * @Descrintion  与资源文件进行映射关联
 * @Author bd
 * @Date 2020/6/7 16:20
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "file")  //前缀
@PropertySource("classpath:file-upload-dev.properties") //打包后都在classPath下面，文件名
public class FileUpload {

    private String imageUserFaceLocation;
    private String imageServerUrl;

    public String getImageUserFaceLocation() {
        return imageUserFaceLocation;
    }

    public void setImageUserFaceLocation(String imageUserFaceLocation) {
        this.imageUserFaceLocation = imageUserFaceLocation;
    }

    public String getImageServerUrl() {
        return imageServerUrl;
    }

    public void setImageServerUrl(String imageServerUrl) {
        this.imageServerUrl = imageServerUrl;
    }
}
