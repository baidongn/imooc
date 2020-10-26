package com.imooc.controller;

import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @ClassName HellController
 * @Descrintion
 * @Author bd
 * @Date 2020/5/16 23:06
 * @Version 1.0
 **/

@Api(value = "购物车接口controller", tags = "购物车接口相关api")
@RequestMapping("shopcart")
@RestController
public class ShopcatController {

    final static Logger logger = LoggerFactory.getLogger(ShopcatController.class);

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(
            @RequestParam String userId,
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("");
        }
        // TODO: 2020/5/30  前端用户登录的情况下，添加商品到购物车，会同步到后端redis中
        return IMOOCJSONResult.ok();


    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public IMOOCJSONResult del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (StringUtils.isBlank(userId)||StringUtils.isBlank(itemSpecId)) {
            return IMOOCJSONResult.errorMsg("");
        }

        // TODO: 2020/5/30  前端用户登录的情况下，删除用户商品，需要删除后端redis中数据
        return IMOOCJSONResult.ok();


    }
}
