package com.example.hotel.controller.home;

import com.example.hotel.controller.common.BaseController;
import com.example.hotel.dto.JsonResult;
import com.example.hotel.entity.*;
import com.example.hotel.enums.OrderStatusEnum;
import com.example.hotel.service.*;
import com.example.hotel.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 焦新伟
 * @date 2020/12/21 4:59 下午
 */
@Controller
public class FrontPostController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @Autowired
    private SocketDailyService DailyService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;

    @Autowired
    private SocketInfoService infoService;
    Long PostId;

    /**
     * 帖子详情
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/post/{id}")
    public String postDetails(@PathVariable("id") Long id,
                              @RequestParam(value = "startDate", required = false) String start,
                              @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                              Model model) {
        DateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT);

        if (quantity == null || quantity < 1) {
            quantity = 1;
        }

        Date today = new Date();

        // 判断入住日期是否合法
        if (StringUtils.isEmpty(start)) {
            start = dateFormat.format(today);
        } else {
            try {
                Date startDate = dateFormat.parse(start);
                if (startDate.before(today)) {
                    start = dateFormat.format(today);
                }
            } catch (ParseException e) {
                start = dateFormat.format(today);
                e.printStackTrace();
            }
        }

        PostId=id;
        // 客房
        Post post = postService.get(id);
        if (post == null) {
            return renderNotFound();
        }
        // 分类
        Category category = categoryService.get(post.getCateId());
        post.setCategory(category);
        model.addAttribute("post", post);

        String[] imgUrlList = post.getImgUrl().split(",");
        model.addAttribute("imgUrlList", imgUrlList);


        // 该房间的预定记录
        List<Record> recordList = recordService.findByPostId(id);
        model.addAttribute("recordList", recordList);

        // 分类列表
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("startDate", start);
        model.addAttribute("quantity", quantity);
        return "home/post";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getList() {
        Map<String,Object> map = new HashMap<>();
        map.put("msg", "ok");
        map.put("data", infoService.getById(PostId));
        return map;
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHistoryList() {
        System.out.println("进入方法士大夫士大夫神经科大夫很少看见对方国家");
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "ok");
        map.put("data", DailyService.findAllById(PostId));
        return map;
    }
    /**
     * 结算页面
     *
     * @param postId
     * @param start
     * @param quantity
     * @param model
     * @return
     */
    @GetMapping("/checkout")
    public String checkout(@RequestParam("postId") Long postId,
                           @RequestParam(value = "startDate", required = false) String start,
                           @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                           Model model) {
        DateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT);

        if (quantity == null || quantity < 1) {
            quantity = 1;
        }

        Date today = new Date();

        // 判断入住日期是否合法
        if (StringUtils.isEmpty(start)) {
            start = dateFormat.format(today);
        } else {
            try {
                Date startDate = dateFormat.parse(start);
                if (startDate.before(today)) {
                    start = dateFormat.format(today);
                }
            } catch (ParseException e) {
                start = dateFormat.format(today);
                e.printStackTrace();
            }
        }

        Post post = postService.get(postId);
        if (post == null) {
            return this.renderNotFound();
        }

        User user = getLoginUser();
        if (user == null) {
            return "redirect:/";
        }

        // 分类列表
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("post", post);
        model.addAttribute("startDate", start);
        model.addAttribute("quantity", quantity);
        model.addAttribute("user", user);
        return "home/checkout";
    }

    /**
     * 创建订单
     *
     * @param postId
     * @param start
     * @param quantity
     * @return
     */
    @GetMapping("/checkOrder")
    @ResponseBody
    public JsonResult checkOrder(@RequestParam(value = "postId") Long postId,
                                 @RequestParam(value = "startDate") String start,
                                 @RequestParam(value = "quantity") Integer quantity) {
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("请先登录");
        }

        Post post = postService.get(postId);
        if (post == null) {
            return JsonResult.error("客房不存在");
        }

        DateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT);

        if (quantity == null || quantity < 1 || quantity > 7) {
            return JsonResult.error("天数不合法");
        }

        Date today = new Date();

        // 判断入住日期是否合法
        if (StringUtils.isEmpty(start)) {
            start = dateFormat.format(today);
        } else {
            try {
                Date startDate = dateFormat.parse(start);
                if (startDate.before(today) && !Objects.equals(start, dateFormat.format(today))) {
                    return JsonResult.error("不能预定过去的日期");
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return JsonResult.error("预定日期格式不合法");
            }
        }

        // 查询日期列表
        List<String> dateList = DateUtil.getBetweenDates(start, quantity);
        // 判断客房是否可以预定
        List<Record> recordList = recordService.findByPostIdAndRecordDate(postId, dateList);
        if (recordList.size() > 0) {
            return JsonResult.error("房间已被人预定，请重新选择房间和日期");
        }
        return JsonResult.success("可以预定");
    }

    /**
     * 创建订单
     *
     * @param postId
     * @param start
     * @param quantity
     * @param userName
     * @param userDisplayName
     * @param idCard
     * @return
     */
    @PostMapping("/order")
    @Transactional
    @ResponseBody
    public JsonResult addOrder(@RequestParam(value = "postId") Long postId,
                               @RequestParam(value = "startDate") String start,
                               @RequestParam(value = "quantity") Integer quantity,
                               @RequestParam(value = "userName") String userName,
                               @RequestParam(value = "userDisplayName") String userDisplayName,
                               @RequestParam(value = "idCard") String idCard) {
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("请先登录");
        }

        Post post = postService.get(postId);
        if (post == null) {
            return JsonResult.error("客房不存在");
        }

        DateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT);

        if (quantity == null || quantity < 1 || quantity > 7) {
            return JsonResult.error("天数不合法");
        }

        Date today = new Date();

        // 判断入住日期是否合法
        if (StringUtils.isEmpty(start)) {
            start = dateFormat.format(today);
        } else {
            try {
                Date startDate = dateFormat.parse(start);
                if (startDate.before(today) && !Objects.equals(start, dateFormat.format(today))) {
                    return JsonResult.error("不能预定过去的日期");
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return JsonResult.error("预定日期格式不合法");
            }
        }

        // 查询日期列表
        List<String> dateList = DateUtil.getBetweenDates(start, quantity);
        // 判断客房是否可以预定
        List<Record> recordList = recordService.findByPostIdAndRecordDate(postId, dateList);
        if (recordList.size() > 0) {
            return JsonResult.error("房间已被人预定，请重新选择房间和日期");
        }

        // 支付省略
        // 添加订单
        Order order = new Order();
        order.setPostId(postId);
        order.setQuantity(quantity);
        order.setStartDate(start);
        order.setName(userDisplayName);
        order.setPhone(userName);
        order.setIdCard(idCard);
        order.setUserId(user.getId());
        order.setStatus(OrderStatusEnum.HAS_PAY.getCode());
        order.setPostTitle(post.getPostTitle());
        order.setPostNumber(post.getNumber());
        order.setPrice(post.getPrice());
        order.setTotalPrice(post.getPrice() * quantity);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        orderService.insert(order);

        // 添加预定记录
        for (String recordDate : dateList) {
            Record record = new Record();
            record.setPostId(postId);
            record.setUserId(user.getId());
            record.setRecordDate(recordDate);
            recordService.insert(record);
        }
        return JsonResult.success("预定成功", order.getId());
    }

    @GetMapping("/order/{id}")
    public String order(@PathVariable("id") Long id, Model model) {
        Order order = orderService.get(id);
        if (order == null) {
            return this.renderNotFound();
        }

        User user = getLoginUser();
        if (user == null) {
            return "redirect:/";
        }

        Boolean isCustomer = loginUserIsCustomer();
        if (!Objects.equals(order.getUserId(), user.getId()) && isCustomer) {
            return this.renderNotAllowAccess();
        }
        Date createTime = order.getCreateTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(createTime);
        model.addAttribute("dateString", dateString);
        model.addAttribute("order", order);



        // 分类列表
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("user", userService.get(order.getUserId()));
        return "home/order";
    }



}
