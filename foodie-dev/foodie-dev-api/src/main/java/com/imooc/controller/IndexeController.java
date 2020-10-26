package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("index")
@Api(value = "首页", tags = {"首页展示的相关接口"})
public class IndexeController {

    final static Logger logger = LoggerFactory.getLogger(IndexeController.class);
    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/carousel")
    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    public IMOOCJSONResult carousel() {
        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);
        //传给前端是一个carousel 数组，由前端进行解析
        return IMOOCJSONResult.ok(carousels);
    }

    /*
     * 首页分类展示需求：
     * 1.第一次刷新主页查询分类，渲染展示到首页
     * 2.如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     **/
    @GetMapping("/cats")
    @ApiOperation(value = "获取商品分类（一级分类）", notes = "获取商品分类（一级分类）", httpMethod = "GET")
    public IMOOCJSONResult cats() {
        List<Category> categories = categoryService.queryAllRootLevelCat();
        //传给前端是一个carousel 数组，由前端进行解析
        return IMOOCJSONResult.ok(categories);
    }


    /*
     *路径参数
     **/
    @GetMapping("/subCat/{rootCatId}")
    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    public IMOOCJSONResult subCat(

            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {

            return IMOOCJSONResult.errorMsg("分类id不存在");
        }

        List<CategoryVO> categorieVOs = categoryService.getSubCatList(rootCatId);
        //传给前端是一个carousel 数组，由前端进行解析
        return IMOOCJSONResult.ok(categorieVOs);
    }


    @GetMapping("/SixNewItems/{rootCatId}")
    @ApiOperation(value = "查询每个一级分类下的最新6条商品信息", notes = "查询每个一级分类下的最新6条商品信息", httpMethod = "GET")
    public IMOOCJSONResult SixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("分类id不存在");
        }

        List<NewItemsVO> newItemsVOS = categoryService.getSixNewItemsLazy(rootCatId);
        //传给前端是一个carousel 数组，由前端进行解析
        return IMOOCJSONResult.ok(newItemsVOS);
    }
}
