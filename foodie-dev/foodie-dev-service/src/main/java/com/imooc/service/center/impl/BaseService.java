package com.imooc.service.center.impl;

import com.github.pagehelper.PageInfo;
import com.imooc.utils.PagedGridResult;

import java.util.List;

/**
 * @ClassName BaseService
 * @Descrintion
 * @Author bd
 * @Date 2020/6/10 23:02
 * @Version 1.0
 **/
public class BaseService {

    public PagedGridResult setterPagedGrid(List<?> list, Integer page) {

        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());
        grid.setRows(list);
        grid.setTotal(pageList.getPages());

        return grid;
    }

}
