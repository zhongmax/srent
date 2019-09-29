package com.csmaxwell.srent.wx.web;

import com.csmaxwell.srent.core.util.CharUtil;
import com.csmaxwell.srent.core.util.JacksonUtil;
import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.core.validator.Order;
import com.csmaxwell.srent.core.validator.Sort;
import com.csmaxwell.srent.db.domain.*;
import com.csmaxwell.srent.db.service.*;
import com.csmaxwell.srent.wx.annotation.LoginUser;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 商品服务
 */
@RestController
@RequestMapping("/wx/goods")
@Validated
public class WxGoodsController {

    private final Log logger = LogFactory.getLog(WxGoodsController.class);

    @Autowired
    private SrentGoodsService goodsService;

    @Autowired
    private SrentUserService userService;

    @Autowired
    private SrentCollectService collectService;

    @Autowired
    private SrentFootprintService footprintService;

    @Autowired
    private SrentCategoryService categoryService;

    @Autowired
    private SrentSearchHistoryService searchHistoryService;

    @Autowired
    private SrentOrderService orderService;

    @Autowired
    private SrentOrderGoodsService orderGoodsService;

//    private Object validate(SrentGoods goods, int userId) {
//        String name = goods.getName();
//        String desc = goods.getDesc();
//        String goods_sn = "1";
//        BigDecimal rentPrice = goods.getRentPrice();
//        Integer categoryId = goods.getCategoryId();
//    }

    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(16, 16, 1000, TimeUnit.MILLISECONDS, WORK_QUEUE, HANDLER);

    /**
     * 商品基本信息
     * @param userId
     * @param goodsId
     * @return
     */
    @GetMapping("/info")
    public Object info(@LoginUser Integer userId, Integer goodsId) {
        SrentGoods info = goodsService.findById(goodsId);
        //System.out.println(info);
        return ResponseUtil.ok(info);
    }



    /**
     * 商品详情
     * <p>
     *     用户可以不登录。
     *     如果用户登录，则记录用户足迹以及返回用户收藏信息
     * </p>
     *
     * @param userId 用户ID
     * @param id 商品ID
     * @return 商品详情
     */
    @GetMapping("detail")
    public Object detail(@LoginUser Integer userId, @NotNull Integer id) {


        // 商品信息
        SrentGoods info = goodsService.findById(id);
        int postUser = info.getUserId();
        // 发布人信息
        UserVo userInfo = userService.findUserVoById(postUser);

        // 通过ID查询是否有订单ID被租出以及时间
//        if (orderId != null) {
//            List<SrentOrderGoods> orderGoods = orderGoodsService.queryByOid(orderId);
//            System.out.println(orderGoods);
//        }

        LocalDate then = LocalDate.now();
        boolean isExpiry = false;
        if (info.getOrderId() != null && info.getOrderId() != 0) {
            SrentOrderGoods orderGoods = orderGoodsService.queryByOidDays(info.getOrderId());
//            System.out.println(orderGoods.getDays());
//            System.out.println(orderGoods.getAddTime());

            LocalDate time = orderGoods.getAddTime().toLocalDate();
            Period p = Period.ofDays(orderGoods.getDays());
            then = time.plus(p);
//            System.out.println(then);
            isExpiry = !(then.isAfter(LocalDate.now()));
//            isExpiry = !(then.isAfter(LocalDate.of(2019, 9, 16)));
//            System.out.println(isExpiry);
        }



        // 用户收藏
        int userHasCollect = 0;

        if (userId != null) {
            //System.out.println("执行");
            userHasCollect = collectService.count(userId, id);
        }
        //System.out.println("用户ID" + userId);
        //System.out.println("是否收藏userHasCollect" + userHasCollect);

        // 记录用户足迹
        if (userId != null) {
            executorService.execute(()->{
                SrentFootprint footprint = new SrentFootprint();
                footprint.setUserId(userId);
                footprint.setGoodsId(id);
                footprintService.add(footprint);
            });
        }

        Map<String, Object> data = new HashMap<>();

        try {
            data.put("isExpiry", isExpiry);
            data.put("time", then);
            data.put("userInfo", userInfo);
            data.put("info", info);
            data.put("userHasCollect", userHasCollect);
            data.put("postUserId", postUser);
            data.put("userId", userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseUtil.ok(data);
    }

    /**l
     * 商品分类
     *
     * @param id 分类类型ID
     * @return 商品类型
     */
    @GetMapping("category")
    public Object category(@NotNull Integer id) {
        SrentCategory cur = categoryService.findById(id);
        SrentCategory parent = null;
        List<SrentCategory> children = null;

        if (cur.getPid() == 0) {
            parent = cur;
            children = categoryService.queryByPid(cur.getId());
            cur = children.size() > 0 ? children.get(0) : cur;
        } else {
            parent = categoryService.findById(cur.getPid());
            children = categoryService.queryByPid(cur.getPid());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("currentCategory", cur);
        data.put("parentCategory", parent);
        data.put("brotherCategory", children);
        return ResponseUtil.ok(data);
    }

    /**
     * 根据条件搜索商品
     * <p>
     *
     * </p>
     */
    @GetMapping("list")
    public Object list(
            Integer categoryId,
            String keyword,
            @LoginUser Integer userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort(accepts = {"add_time", "rent_price", "name"}) @RequestParam(defaultValue = "add_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
//        System.out.println(userId);
//        System.out.println(keyword);
        // 添加到搜索历史
        if (userId != null && !StringUtils.isNullOrEmpty(keyword)) {
            SrentSearchHistory searchHistoryVo = new SrentSearchHistory();
            searchHistoryVo.setKeyword(keyword);
            searchHistoryVo.setUserId(userId);
            searchHistoryVo.setFrom("wx");
            searchHistoryService.save(searchHistoryVo);
//            System.out.println(searchHistoryVo);
        }

        // 查询列表数据
        List<SrentGoods> goodsList = goodsService.querySelective(categoryId, keyword, page, limit, sort, order);

        // 查询商品所属类别列表
        List<Integer> goodsCatIds = goodsService.getCatIds(keyword);
        List<SrentCategory> categoryList = null;

        if (goodsCatIds.size() != 0) {
            categoryList = categoryService.queryL2ByIds(goodsCatIds);
        } else {
            categoryList = new ArrayList<>(0);
        }

        PageInfo<SrentGoods> pagedList = PageInfo.of(goodsList);

        Map<String, Object> entity = new HashMap<>();
        entity.put("list", goodsList);
        entity.put("total", pagedList.getTotal());
        entity.put("page", pagedList.getPageNum());
        entity.put("limit", pagedList.getPageSize());
        entity.put("pages", pagedList.getPages());
        entity.put("filterCategoryList", categoryList);

        // 因filterCategoryList参数，需返回ok(param)
        return ResponseUtil.ok(entity);
    }

    /**
     * 添加商品
     */
    @PostMapping("post")
    public Object post(@LoginUser Integer userId, @RequestBody SrentGoods goods) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        String goodsSn = CharUtil.getRandomStringNum(2) + userId;
        goods.setGoodsSn(goodsSn);
        goods.setUserId(userId);

//        System.out.println(goods.getUserId());
//        System.out.println(goods.getGoodsSn());
//        System.out.println(goods.getRentPrice());
//        System.out.println(goods.getName());
//        System.out.println(goods.getDesc());
//        System.out.println(goods.getCategoryId());
//        System.out.println(goods.getKeywords());
//        //System.out.println(goods);
//        System.out.println(Arrays.toString(goods.getGallery()));
        if (goods.getId() == null || goods.getId().equals(0)) {
            goodsService.add(goods);
        } else {
            goodsService.updateById(goods);
        }

        return ResponseUtil.ok(goods);
    }

    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer id = JacksonUtil.parseInteger(body, "id");
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        SrentGoods goods = goodsService.findById(id);
        if (goods == null) {
            return ResponseUtil.badArgument();
        }
        if(!goods.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }
        goodsService.deleteById(id);
        return ResponseUtil.ok();
    }

    @PostMapping("add")
    public Object add(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer id = JacksonUtil.parseInteger(body, "id");
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        SrentGoods goods = goodsService.findById(id);
        goods.setRented(false);
        goods.setOrderId(null);
        if (goods == null) {
            return ResponseUtil.badArgument();
        }
        if(!goods.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }
        goodsService.updateById(goods);
        return ResponseUtil.ok(goods);
    }
}
