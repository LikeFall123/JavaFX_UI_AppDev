package hu.alkfejl.zh1gyak.dao;

import hu.alkfejl.zh1gyak.modell.Order;

import java.util.List;

public interface OrderDAO {
    List<Order> getOrders();
    Order save(Order order);
}
