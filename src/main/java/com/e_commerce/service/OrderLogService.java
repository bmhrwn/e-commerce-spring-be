package com.e_commerce.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.entity.Order;
import com.e_commerce.entity.OrderLog;
import com.e_commerce.entity.User;
import com.e_commerce.model.StatusPemesanan;
import com.e_commerce.repository.OrderLogRepository;

@Service
public class OrderLogService {

    @Autowired
    private OrderLogRepository orderLogRepository;



    public final static int DRAFT = 0;
    public final static int PAYMENT = 10;
    public final static int GASKET = 20;
    public final static int DELIVERY = 30;
    public final static int FINISH = 40; 
    public final static int CANCEL = 90; 


    public void createLog(String username, Order order, int type, String message){
        OrderLog orderLog = new OrderLog();
        orderLog.setId(UUID.randomUUID().toString());
        orderLog.setLogMessage(message);
        orderLog.setLogType(type);
        orderLog.setOrder(order);
        orderLog.setUser(new User(username));
        orderLog.setTime(new Date());
        orderLogRepository.save(orderLog);

    }


 
}
