package com.csmaxwell.srent.wx.web;

import com.csmaxwell.srent.core.util.RegexUtil;
import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.db.domain.SrentAddress;
import com.csmaxwell.srent.db.service.SrentAddressService;
import com.csmaxwell.srent.db.service.SrentRegionService;
import com.csmaxwell.srent.wx.annotation.LoginUser;
import com.csmaxwell.srent.wx.service.GetRegionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户收获地址服务
 */
@RestController
@RequestMapping("/wx/address")
@Validated
public class WxAddressController extends GetRegionService {

    private final Log logger = LogFactory.getLog(WxAddressController.class);

    @Autowired
    private SrentAddressService addressService;

    @Autowired
    private SrentRegionService regionService;

    /**
     * 用户收货地址列表
     *
     * @param userId 用户ID
     * @return 收获地址列表
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<SrentAddress> addressList = addressService.queryByUid(userId);

        return ResponseUtil.okList(addressList);
    }

    /**
     * 收货地址详情
     * @param userId 用户ID
     * @param id 收获地址ID
     * @return 收获地址详情
     */
    @GetMapping("detail")
    public Object detail(@LoginUser Integer userId, @NotNull Integer id) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        //System.out.println("233");

        SrentAddress address = addressService.query(userId, id);
        if (address == null) {
            return ResponseUtil.badArgumentValue();
        }
        return ResponseUtil.ok(address);
    }

    private Object validate(SrentAddress address) {
        String name = address.getUsername();

        if (StringUtils.isEmpty(name)) {

            return ResponseUtil.badArgumentValue();
        }


        // 测试收货手机号码是否正确
        String mobile = address.getTel();
        if (StringUtils.isEmpty(mobile)) {

            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileExact(mobile)) {

            return ResponseUtil.badArgument();
        }

        String province = address.getProvince();
        if (StringUtils.isEmpty(province)) {

            return ResponseUtil.badArgument();
        }

        String city = address.getCity();
        if (StringUtils.isEmpty(city)) {

            return ResponseUtil.badArgument();
        }

        String county = address.getCounty();
        if (StringUtils.isEmpty(county)) {

            return ResponseUtil.badArgument();
        }


        String areaCode = address.getAreaCode();
        if (StringUtils.isEmpty(areaCode)) {

            return ResponseUtil.badArgument();
        }

        String detailedAddress = address.getAddressDetail();
        if (StringUtils.isEmpty(detailedAddress)) {

            return ResponseUtil.badArgument();
        }

        Boolean isDefault = address.getIsDefault();
        if (isDefault == null) {

            return ResponseUtil.badArgument();
        }
        return null;
    }

    @PostMapping("save")
    public Object save(@LoginUser Integer userId, @RequestBody SrentAddress address) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

//        System.out.println(address.getUsername());
        Object error = validate(address);

        if (error != null) {

            return error;
        }
//        System.out.println(address.getIsDefault());
        if (address.getIsDefault()) {
            // 重置其他收获地址的默认选项
            addressService.resetDefault(userId);
        }


        if (address.getId() == null || address.getId().equals(0)) {
            address.setId(null);
            address.setUserId(userId);
            addressService.add(address);
        } else {
            address.setUserId(userId);
            if (addressService.update(address) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        return ResponseUtil.ok(address.getId());
    }

    /**
     * 删除收货地址
     *
     * @param userId 用户ID
     * @param address 用户收货地址, {id: xxx}
     * @return 删除操作结果
     */
    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId, @RequestBody SrentAddress address) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer id = address.getId();
        if (id == null) {
            return ResponseUtil.badArgumentValue();
        }

        addressService.delete(id);
        return ResponseUtil.ok();
    }
}
