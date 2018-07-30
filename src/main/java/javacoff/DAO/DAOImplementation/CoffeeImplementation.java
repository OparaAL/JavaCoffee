package javacoff.DAO.DAOImplementation;


import javacoff.DAO.CoffeeDAO;
import javacoff.entity.Coffee;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CoffeeImplementation implements CoffeeDAO {

    private HibernateTemplate hibernateTemplate;

    public void setSessionFactory(SessionFactory sessionFactory){
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }


    @Override
    @Transactional
    public Coffee findById(Long id) {
        return hibernateTemplate.get(Coffee.class, id);
    }

    @Override
    @Transactional
    public List<Coffee> findAll() {
        List<Coffee> coffee = hibernateTemplate.loadAll(Coffee.class);
        return coffee;
    }

    @Override
    @Transactional
    public void create(Coffee coffee) {
        hibernateTemplate.save(coffee);
    }

    @Override
    @Transactional
    public List<Coffee> findNotDis() {
        return (List<Coffee>) hibernateTemplate.findByCriteria(DetachedCriteria.forClass(Coffee.class)
                .add(Restrictions.eq("disabled",false)));

    }

}
