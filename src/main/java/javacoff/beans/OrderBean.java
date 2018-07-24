package javacoff.beans;


import javacoff.DAO.CoffeeDAO;
import javacoff.DAO.OrderDAO;
import javacoff.DAO.OrderPositionDAO;
import javacoff.entity.Coffee;
import javacoff.entity.Order;
import javacoff.entity.OrderPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.*;

@ManagedBean
@SessionScoped
public class OrderBean extends SpringBeanAutowiringSupport {

    @Autowired
    private CoffeeDAO coffeeDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderPositionDAO orderPositionDAO;

    @Autowired
    private Order order;

    @Value("${order.freeCup}")
    private Integer freeCup;

    @Value("${order.freeDelivery}")
    private Double freeDelivery;

    @Value("${order.costOfDelivery}")
    private Double costOfDelivery;

    private Map<Long, Integer> selectedCoffee = new HashMap<>();
    private Map<Coffee, Integer> coffeeToOrder = new HashMap<>();
    private Map<Coffee, Integer> coffeeToOrderTemp = new HashMap<>();


    private String theProperty;
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

    public String getTheProperty() {
        return theProperty;
    }

    public void setTheProperty(String theProperty) {
        this.theProperty = theProperty;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
        System.out.println(id + " " +numOfCups);
        System.out.println(selectedCoffee);
        System.out.println("freeCup:" + freeCup +" costOfDelivery: " + costOfDelivery + " freDelivery" + freeDelivery);

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
                System.out.println("Coffee: " + coffee.getCoffeeName() + "| Quantity " + entry.getValue()+"| tmp: " + tmp
                +"quantity%freeCup = " + entry.getValue()%freeCup);
                if(entry.getValue() < freeCup){
                    cost += coffee.getCostForCup() * entry.getValue();
                }
                else if(entry.getValue()>=freeCup){

                    cost += coffee.getCostForCup() * ((entry.getValue()-tmp));

                }
            }

            System.out.println(cost.equals(freeDelivery));
        if(cost > freeDelivery || cost.equals(freeDelivery)){
            costWithDelivery = cost;
            System.out.println("cost=" + cost);
        }
        else if( cost < freeDelivery && cost!=0){
            costWithDelivery = cost + costOfDelivery;
        }
        else costWithDelivery = 0.0;



            selectedCoffee.clear();

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/confirm.xhtml");
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        public void confirmOrder(String adress, String clientName){
            Order order = new Order();
            OrderPosition orderPosition = new OrderPosition();
            order.setAdress(adress);
            order.setClientName(clientName);
            order.setCost(costWithDelivery);
            order.setDate(new Date());
            orderDAO.create(order);
            for(Map.Entry<Coffee, Integer> entry : coffeeToOrder.entrySet()) {
                orderPosition.setCoffee(entry.getKey());
                orderPosition.setOrder(order);
                orderPosition.setQuantity(entry.getValue());
                orderPositionDAO.create(orderPosition);
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

}
