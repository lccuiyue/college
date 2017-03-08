package com.qijun.goods.goodstype.controller;

import com.qijun.goods.goodstype.entity.Goods;
import com.qijun.goods.goodstype.entity.GoodsCommon;
import com.qijun.goods.goodstype.service.GoodsCommonService;
import com.qijun.goods.goodstype.service.GoodsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
@Controller
@RequestMapping("/goodsController")
public class GoodsController {

    private static final Logger logger = Logger.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsCommonService goodsCommonService;

    @RequestMapping("/showAllGoodses.do")
    public String showAllGoodses(Model model){
        List<Goods> goodses = goodsService.selectAll();
        model.addAttribute("goodses", goodses);
        return "view/goods/goodstype/goodstype";
    }

    @RequestMapping("/loadGoods.do")
    public String loadGoods(Model model,Long gsId){
        Goods goods = goodsService.selectByPrimaryKey(gsId);
        model.addAttribute("goods", goods);
        return "view/goods/goodstype/goodstypeDetail";
    }

    @RequestMapping("/insertUI.do")
    public String insertUI(){
        return "view/goods/goodstype/goodstypeInsertUI";
    }

    @RequestMapping("/insert.do")
    public String insert(Goods goods){
        logger.info("接受到的数据:" + goods);
        GoodsCommon goodsCommon = goods.getGoodsCommon();
        logger.info("goodsCommon哈哈:" + goodsCommon);
        goodsCommonService.insert(goodsCommon);
        goodsService.insert(goods);

        return "forward:showAllGoodses.do";
    }

    @RequestMapping("/updateUI.do")
    public String updateUI(Model model, Long gsId){
        Goods goods = goodsService.selectByPrimaryKey(gsId);
        model.addAttribute("goods", goods);
        return "view/goods/goodstype/goodstypeUpdateUI";
    }

    @RequestMapping("/update.do")
    public String update(Goods goods){
        logger.info("接收到的参数对象：" + goods);
        GoodsCommon goodsCommon = goods.getGoodsCommon();
        logger.info("goodsCommon" + goodsCommon);
        goodsCommonService.update(goodsCommon);
        goodsService.update(goods);
        return "forward:showAllGoodses.do";
    }

    @RequestMapping("/delete.do")
    public String delete(Long gsId){
        Goods goods = goodsService.selectByPrimaryKey(gsId);
        goodsService.deleteByPrimaryKey(gsId);
        goodsCommonService.deleteByPrimaryKey(goods.getGoodsCommon().getGcTypeId());

        return "forward:showAllGoodses.do";
    }




}
