package com.importadorabacco.web.model;

import com.importadorabacco.web.model.domain.CommitOrderReq;

import java.sql.Timestamp;

/**
 * Created by tanhengyi on 15-5-13.
 */
public class Order extends BaseData {
    private Long id;
    private Long userId;
    private String name;
    private String mobile;
    private String email;
    private String address;
    private String city;
    private String zipCode;
    private Timestamp createTime;

    public Order() {
    }

    public static Order from(CommitOrderReq commitOrderReq) {
        if (commitOrderReq == null) {
            return null;
        }

        Order order = new Order();
        order.setUserId(commitOrderReq.getUserId());
        order.setName(commitOrderReq.getName());
        order.setMobile(commitOrderReq.getMobile());
        order.setEmail(commitOrderReq.getEmail());
        order.setAddress(commitOrderReq.getAddress());
        order.setCity(commitOrderReq.getCity());
        order.setZipCode(commitOrderReq.getZipCode());
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));

        return order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
