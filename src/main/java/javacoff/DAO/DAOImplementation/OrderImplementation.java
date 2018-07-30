package javacoff.DAO.DAOImplementation;

import javacoff.DAO.OrderDAO;
import javacoff.entity.Order;
import javacoff.entity.OrderPosition;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class OrderImplementation implements OrderDAO {

    private HibernateTemplate hibernateTemplate;


    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }


    @Override
    @Transactional
    public Order findById(Long id) {
        return hibernateTemplate.get(Order.class, id);
    }

    @Override
    @Transactional
    public void create(Order order) {hibernateTemplate.save(order);}

    @Override
    @Transactional
    public List<Order> findAll() {
        return hibernateTemplate.loadAll(Order.class);
    }

    //OrderPosition
    @Override
    public void createOP(OrderPosition orderPosition) {
        hibernateTemplate.save(orderPosition);
    }

    @Override
    public List<OrderPosition> findAllOP() {
        return hibernateTemplate.loadAll(OrderPosition.class);
    }

    @Override
    public OrderPosition findByIdOP(Long id) {
        return hibernateTemplate.get(OrderPosition.class, id);
    }
}
