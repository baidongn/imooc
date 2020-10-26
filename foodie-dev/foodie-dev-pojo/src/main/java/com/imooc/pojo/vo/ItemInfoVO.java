package com.imooc.pojo.vo;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;

import java.util.List;

/**
 * @ClassName ItemInfoVO
 * @Descrintion 商品详情VO
 * @Author bd
 * @Date 2020/5/24 18:03
 * @Version 1.0
 **/
public class ItemInfoVO {
    private Items items;
    private List<ItemsImg> itemsImgs;
    private ItemsParam itemParams;
    private List<ItemsSpec> itemSpecList;

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public List<ItemsImg> getItemsImgs() {
        return itemsImgs;
    }

    public void setItemsImgs(List<ItemsImg> itemsImgs) {
        this.itemsImgs = itemsImgs;
    }

    public ItemsParam getItemParams() {
        return itemParams;
    }

    public void setItemParams(ItemsParam itemParams) {
        this.itemParams = itemParams;
    }

    public List<ItemsSpec> getItemSpecList() {
        return itemSpecList;
    }

    public void setItemSpecList(List<ItemsSpec> itemSpecList) {
        this.itemSpecList = itemSpecList;
    }
}
