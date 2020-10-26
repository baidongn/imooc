package com.imooc.controller.center;

import com.imooc.controller.BaselController;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUsersBO;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.resource.FileUpload;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.DateUtil;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CenterController
 * @Descrintion
 * @Author bd
 * @Date 2020/6/7 10:39
 * @Version 1.0
 **/
@RestController
@RequestMapping("userInfo")
@Api(value = "用户信息", tags = {"用户信息的相关接口"})
public class CenterUserController extends BaselController {
    @Autowired
    private CenterUserService centerUserService;
    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("update")
    public IMOOCJSONResult update(
            @ApiParam(name = "userId", value = "用户Id", required = true)
            @RequestParam String userId,
            //我要进行验证
            @RequestBody @Valid CenterUsersBO centerUsersBO,
            //验证的错误信息会放在  BindingResult
            BindingResult bindingResult,
            HttpServletResponse response,
            HttpServletRequest request) {
        //判断bindingResult是是否有错误信息，如果有 直接return
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = getErrors(bindingResult);
            return IMOOCJSONResult.errorMap(errorMap);
        }

        //将更新后的用户信息发给前端,将有些数据设置为空，重新设置cookie（不用传给前端，只需要交给cookies）
        Users usersResult = centerUserService.updateUserInfo(userId, centerUsersBO);
        UsersVO usersVO = convenUserVo(usersResult);
//        usersResult = setNULLProperty(usersResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);
        // TODO: 2020/6/7 后续要改，增加令牌token，整合进redis，分布式会话

        return IMOOCJSONResult.ok();
    }

    private Users setNULLProperty(Users userResult) {
        userResult.setRealname(null);
        userResult.setPassword(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        return userResult;
    }

    private Map<String, String> getErrors(BindingResult bindingResult) {
        //获取所有错误信息
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        Map map = new HashMap();
        for (FieldError fieldError : fieldErrors) {
            //发生验证错误所对应的某一个属性
            String errorField = fieldError.getField();
            //错误信息
            String defaultMessage = fieldError.getDefaultMessage();
            map.put(errorField, defaultMessage);
        }
        return map;
    }


    @ApiOperation(value = "用户头像修改", notes = "用户头像修改", httpMethod = "POST")
    @PostMapping("uploadFace")
    public IMOOCJSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户Id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
                    MultipartFile file,
            HttpServletResponse response,
            HttpServletRequest request) {


        //定义头像保存地址
//         String fileSpace = IMAGE_USER_FACE_LOCATION;
        String fileSpace = fileUpload.getImageUserFaceLocation();
        //在路径上 为每一个用户增加一个userId，区分不同用户上传
        String uploadPathPrefix = File.separator + userId;

        //开始文件上传
        if (file != null) {
            FileOutputStream fileOutputStream = null;
            try {
                //获得文件上传的名称
                String filename = file.getOriginalFilename();
                if (StringUtils.isNotBlank(filename)) {
                    //文件重命名  face-userId.png
                    String[] fileNameArr = filename.split("\\.");
                    //获取文件后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];
                    //判断后缀名，防止黑客上传一些php等文件攻击服务器
                    if(!suffix.equalsIgnoreCase("png")&&
                    !suffix.equalsIgnoreCase("jpg")&&!suffix.equalsIgnoreCase("jpeg")){
                        return IMOOCJSONResult.errorMsg("图片格式不正确");
                    }

                    //文件名称重组 覆盖式 /增量式（拼接时间）
                    String newFile = "face-" + userId + "." + suffix;

                    //上传头像最终保存位置
                    String fianlFacePath = fileSpace + uploadPathPrefix + File.separator + newFile;
                   //用于提供给 web服务访问的地址
                    uploadPathPrefix += ("/" + newFile);

                    File outFile = new File(fianlFacePath);
                    //getParentFile()的作用是获得父目录（因为字符串中已经定义了路径，一定会获取到父级目录）
                    if (outFile.getParentFile() != null) {
                        //创建文件夹  mkdirs() 父级目录都会生成
                        outFile.getParentFile().mkdirs();
                    }
                    //文件输出保存到目录
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            return IMOOCJSONResult.errorMsg("文件不能为空");

        }

        //获得用户头像地址
        String imageServerUrl = fileUpload.getImageServerUrl();

        //由于浏览器可能存在缓存情况，所以我们要加上时间戳，保证更新后的时间及时更新(缓存名相同不会更新，只有名称不同才会更新)
        String fianlUrl = imageServerUrl + uploadPathPrefix
                +"?t="+ DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        //更新用户头像到数据库
        Users usersResult = centerUserService.updateUserFace(userId, fianlUrl);
        UsersVO usersVO = convenUserVo(usersResult);
        //将更新后的用户信息发给前端,将有些数据设置为空，重新设置cookie（不用传给前端，只需要交给cookies）
        usersResult = setNULLProperty(usersResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);
        return IMOOCJSONResult.ok();
    }

}
