package com.example.dorm.controller;

import com.example.dorm.entity.Orders;
import com.example.dorm.service.OrdersService;
import com.example.dorm.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    public OrdersService ordersService;

    @PostMapping("/create")
    @ResponseBody
    public Result<Map<String,Object>> createOrders(@RequestHeader("Authorization") String token, @RequestBody Map<String, Long> r){
        String[] splited = token.split("\\s+");
        token=splited[1];
        Long group_id=r.get("group_id");
        Long building_id=r.get("building_id");
        return ordersService.createOrder(token,group_id,building_id);
    }
    @GetMapping("/list")
    public Result<Object> getOrderList(@RequestHeader("Authorization") String token){
        String[] splited = token.split("\\s+");
        token=splited[1];
        return ordersService.getOrderList(token);
    }
    @GetMapping("/info")
    @ResponseBody
    public Result<ReOrderInfo> getOrderInfo(@RequestHeader("Authorization") String token, @RequestParam("order_id") long order_id){
        String[] splited = token.split("\\s+");
        token=splited[1];
        return ordersService.getOrderInfo(token,order_id);
    }


}
