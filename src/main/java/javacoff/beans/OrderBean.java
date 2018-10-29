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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.util.*;
import java.util.logging.Logger;

@ManagedBean
@RequestScoped
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


    private List<Coffee> coffees = coffeeDAO.findNotDis();
    private Map<Coffee, Long> quantities = new HashMap<>();;

    private Double cost;
    private Double costWithDelivery;
    private String address;
    private String clientName;
    private boolean showConfirm;

    //@NotNull
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public boolean isShowConfirm() {
        return showConfirm;
    }

    public void setShowConfirm(boolean showConfirm) {
        this.showConfirm = showConfirm;
    }

    public List<Coffee> getCoffees() {
        return coffees;
    }

    public void setCoffees(List<Coffee> coffees) {
        this.coffees = coffees;
    }

    public Map<Coffee, Long> getQuantities() {
        return quantities;
    }

    public void setQuantities(Map<Coffee, Long> quantities) {
        this.quantities = quantities;
    }

    public void submit() {
        for (Coffee coffee : coffees) {
            Long quantity = quantities.get(coffee);
            System.out.println("Quantity: " + quantity);
        }
    }

    public void calculate() {

        for (Coffee coffee : coffees) {
            Integer inputvalue = coffee.getInputValue();
            System.out.println(inputvalue);
        }
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

        public String toOrder(){

        cost = 0.0;
        costWithDelivery = 0.0;
        coffeeToOrder.clear();

        Coffee coffee;


            for(Map.Entry<Long, Integer> entry : selectedCoffee.entrySet()){
                coffee = coffeeDAO.findById(entry.getKey());
                coffeeToOrder.put(coffee, entry.getValue());
                int tmp = entry.getValue()/freeCup;// переменная необходима для расчета количества чашек для скидки
                logger.info("Coffee: " + coffee.getCoffeeName() + "| Quantity " + entry.getValue()+"| tmp: " + tmp
                +"quantity%freeCup = " + entry.getValue()%freeCup);

                cost += coffee.getCostForCup() * ((entry.getValue()-tmp));

            }

            logger.info("Cost equals freeDelivery : " + cost.equals(freeDelivery));

            costWithDelivery = (cost > freeDelivery || cost.equals(freeDelivery)) ? cost :
                    ( cost < freeDelivery && cost!=0) ? cost + costOfDelivery : 0.0;



            selectedCoffee.clear();
            if(cost > 0) showConfirm = true;

            return "/confirm.xhtml?faces-redirect=true&";
        }

        public String confirmOrder(String address, String clientName){
            Order order = new Order(address,
                    clientName,
                    costWithDelivery,
                    new Date());
            OrderPosition orderPosition;
            orderDAO.create(order);
            for(Map.Entry<Coffee, Integer> entry : coffeeToOrder.entrySet()) {
                orderPosition = new OrderPosition(entry.getKey(), order, entry.getValue());
                orderDAO.createOP(orderPosition);
            }
            coffeeToOrder.clear();
            cost = 0.0;
            costOfDelivery = 0.0;
            costWithDelivery = 0.0;
            showConfirm = false;

            return "/orders.xhtml?faces-redirect=true&";


        }

    public List<Map.Entry<Coffee, Integer>> getCoffeeInOrder() {
        coffeeToOrderTemp = coffeeToOrder;
        Set<Map.Entry<Coffee, Integer>> productSet =
                coffeeToOrderTemp.entrySet();
        return new ArrayList<Map.Entry<Coffee, Integer>>(productSet);
    }

    public String redirectToIndex(){
        return "/index.xhtml?faces-redirect=true&";
    }

    public String redirectToOrders(){
        return "/orders.xhtml?faces-redirect=true&";
    }

    public String redirectToCurrentOrder(){
        return "/confirm.xhtml?faces-redirect=true&";

    }

}
