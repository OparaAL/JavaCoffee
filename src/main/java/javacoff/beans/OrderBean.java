package javacoff.beans;


import javacoff.DAO.CoffeeDAO;
import javacoff.DAO.OrderDAO;
import javacoff.entity.Coffee;
import javacoff.entity.Order;
import javacoff.entity.OrderPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
public class OrderBean extends SpringBeanAutowiringSupport {

    @Autowired
    private CoffeeDAO coffeeDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Value("${order.freeCup}")
    private Integer freeCup;

    @Value("${order.freeDelivery}")
    private Double freeDelivery;

    @Value("${order.costOfDelivery}")
    private Double costOfDelivery;

    private Logger logger = Logger.getLogger(OrderBean.class.getName());

    private Map<Long, Integer> selectedCoffee = new HashMap<>();
    private Map<Coffee, Integer> coffeeToOrder = new HashMap<>();
    private Map<Coffee, Integer> coffeeToOrderTemp = new HashMap<>();


    private Double cost;
    private Double costWithDelivery;
    private String adress;
    private String clientName;

    private Integer quantityOfCups;

    public Map<Coffee, Integer> getCoffeeToOrderTemp() {
        return coffeeToOrderTemp;
    }

    public void setCoffeeToOrderTemp(Map<Coffee, Integer> coffeeToOrderTemp) {
        this.coffeeToOrderTemp = coffeeToOrderTemp;
    }

    public List<Order> getAllOrders(){
        return orderDAO.findAll();
    }

    public Integer getFreeCup() {
        return freeCup;
    }

    public void setFreeCup(Integer freeCup) {
        this.freeCup = freeCup;
    }

    public Double getFreeDelivery() {
        return freeDelivery;
    }

    public void setFreeDelivery(Double freeDelivery) {
        this.freeDelivery = freeDelivery;
    }

    public Double getCostOfDelivery() {
        return costOfDelivery;
    }

    public void setCostOfDelivery(Double costOfDelivery) {
        this.costOfDelivery = costOfDelivery;
    }

    public Map<Coffee, Integer> getCoffeeToOrder() {
        return coffeeToOrder;
    }

    public void setCoffeeToOrder(Map<Coffee, Integer> coffeeToOrder) {
        this.coffeeToOrder = coffeeToOrder;
    }

    public Map<Long, Integer> getSelectedItems() {
        return selectedCoffee;
    }

    public void setSelectedItems(Map<Long, Integer> selectedItems) {
        this.selectedCoffee = selectedItems;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getQuantityOfCups() {
        return quantityOfCups;
    }

    public void setQuantityOfCups(Integer quantityOfCups) {
        this.quantityOfCups = quantityOfCups;
    }

    public Double getCostWithDelivery() {
        return costWithDelivery;
    }

    public void setCostWithDelivery(Double costWithDelivery) {
        this.costWithDelivery = costWithDelivery;
    }

    public void addCoffee(Long id, Integer numOfCups){
        if(numOfCups > 0){
            selectedCoffee.put(id, numOfCups);
        }
        if(numOfCups == 0){
            selectedCoffee.remove(id);
        }
        logger.info("id: " + id.toString() + "| " + "numOfCups: " + numOfCups.toString());
        logger.info(selectedCoffee.toString());

        }

        public void toOrder(){

        cost = 0.0;
        costWithDelivery = 0.0;
        coffeeToOrder.clear();

        Coffee coffee;


            for(Map.Entry<Long, Integer> entry : selectedCoffee.entrySet()){
                coffee = coffeeDAO.findById(entry.getKey());
                coffeeToOrder.put(coffee, entry.getValue());
                int tmp = entry.getValue()/freeCup;
                logger.info("Coffee: " + coffee.getCoffeeName() + "| Quantity " + entry.getValue()+"| tmp: " + tmp
                +"quantity%freeCup = " + entry.getValue()%freeCup);

                cost += coffee.getCostForCup() * ((entry.getValue()-tmp));

            }

            System.out.println(cost.equals(freeDelivery));

            costWithDelivery = (cost > freeDelivery || cost.equals(freeDelivery)) ? cost :
                    ( cost < freeDelivery && cost!=0) ? cost + costOfDelivery : 0.0;



            selectedCoffee.clear();

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/confirm.xhtml");
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        public void confirmOrder(String adress, String clientName){
            Order order = new Order(adress,clientName,costWithDelivery, new Date());
            OrderPosition orderPosition;
            orderDAO.create(order);
            for(Map.Entry<Coffee, Integer> entry : coffeeToOrder.entrySet()) {
                orderPosition = new OrderPosition(entry.getKey(), order, entry.getValue());
                orderDAO.createOP(orderPosition);
            }
            coffeeToOrder.clear();

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/orders.xhtml");
            } catch(IOException e){
                e.printStackTrace();
            }
        }

    public List<Map.Entry<Coffee, Integer>> getCoffeeInOrder() {
        coffeeToOrderTemp = coffeeToOrder;
        Set<Map.Entry<Coffee, Integer>> productSet =
                coffeeToOrderTemp.entrySet();
        return new ArrayList<Map.Entry<Coffee, Integer>>(productSet);
    }

    public void redirectToIndex(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/index.xhtml");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void redirectToOrders(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/orders.xhtml");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void redirectToCurrentOrder(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/confirm.xhtml");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
