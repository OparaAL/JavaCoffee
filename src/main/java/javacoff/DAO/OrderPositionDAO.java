package javacoff.DAO;

import javacoff.entity.Order;
import javacoff.entity.OrderPosition;

import java.util.List;

public interface OrderPositionDAO {

    public void create(OrderPosition orderPosition);
    public List<OrderPosition> findAll();
    public OrderPosition findById(Long id);
}
