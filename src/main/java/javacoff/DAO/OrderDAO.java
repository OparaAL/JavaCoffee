package javacoff.DAO;

import javacoff.entity.Order;
import javacoff.entity.OrderPosition;

import java.util.List;

public interface OrderDAO {

    public Order findById(Long id);
    public void create(Order order);
    public List<Order> findAll();


    //OrderPosition

    public void createOP(OrderPosition orderPosition);
    public List<OrderPosition> findAllOP();
    public OrderPosition findByIdOP(Long id);

}
