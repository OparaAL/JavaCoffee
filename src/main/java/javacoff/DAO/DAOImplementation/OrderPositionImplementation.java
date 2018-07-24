package javacoff.DAO.DAOImplementation;

import javacoff.DAO.OrderPositionDAO;
import javacoff.entity.Order;
import javacoff.entity.OrderPosition;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public class OrderPositionImplementation implements OrderPositionDAO {

    private HibernateTemplate hibernateTemplate;

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @Override
    public void create(OrderPosition orderPosition) {
      hibernateTemplate.save(orderPosition);
    }

    @Override
    public List<OrderPosition> findAll() {
        return hibernateTemplate.loadAll(OrderPosition.class);
    }

    @Override
    public OrderPosition findById(Long id) {
        return hibernateTemplate.get(OrderPosition.class, id);
    }
}
