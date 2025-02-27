package hu.alkfejl.zh1gyak.dao;

import hu.alkfejl.zh1gyak.config.ConfigurationHelper;
import hu.alkfejl.zh1gyak.modell.Order;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO{

    private static final String INSERT_ORDER = "INSERT INTO 'ORDER' (customer, title, delivery, portion, payment) VALUES (?,?,?,?,?)";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM 'ORDER'";
    private String connectionURL;

    public OrderDAOImpl() {
        connectionURL = ConfigurationHelper.getValue("db.url"); // obtaining DB URL
    }

    @Override
    public List<Order> getOrders() {
        List<Order> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionURL);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_ORDERS)
        ){


            while(rs.next()){
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getString("customer"),
                        rs.getString("title"),
                        rs.getInt("portion"),
                        LocalDate.parse(rs.getString("delivery")),
                        rs.getString("payment")
                );

                result.add(order);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    @Override
    public Order save(Order order) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement stmt = c.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)
        ){

            stmt.setString(1, order.getCustomer());
            stmt.setString(2, order.getTitle());
            stmt.setString(3, order.getDelivery().toString());
            stmt.setInt(4, order.getPortion());
            stmt.setString(5, order.getPayment());

            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0){
                return null;
            }

            ResultSet genKeys = stmt.getGeneratedKeys();
            if(genKeys.next()){
                order.setID(genKeys.getInt(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return order;
    }
}
