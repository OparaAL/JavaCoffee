package javacoff.beans;


import javacoff.DAO.CoffeeDAO;
import javacoff.entity.Coffee;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean
@RequestScoped
public class CoffeeBean extends SpringBeanAutowiringSupport {

    @Autowired
    private CoffeeDAO coffeeDAO;

    public CoffeeDAO getCoffeeDAO() {
        return coffeeDAO;
    }

    public void setCoffeeDAO(CoffeeDAO coffeeDAO) {
        this.coffeeDAO = coffeeDAO;
    }

    public List<Coffee> getAllCoffee(){
        return coffeeDAO.findAll();
    }

    public Coffee getCoffee(Long id){
        return coffeeDAO.findById(id);
    }

    public List<Coffee> getNotDis(){return coffeeDAO.findNotDis();}


}
