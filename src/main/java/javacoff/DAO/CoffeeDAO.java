package javacoff.DAO;

import javacoff.entity.Coffee;

import java.util.List;

public interface CoffeeDAO {

    public Coffee findById(Long id);

    public List<Coffee> findAll();

    public void create(Coffee coffee);

}
