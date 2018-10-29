package javacoff.entity;


import javax.persistence.*;
import javax.persistence.criteria.Order;
import java.util.Set;

@Entity
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String coffeeName;

    private Integer costForCup;

    private boolean disabled;

    private Integer inputValue;

    @OneToMany(mappedBy = "coffee")
    private Set<OrderPosition> orderPosition;

    public Set<OrderPosition> getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(Set<OrderPosition> orderPosition) {
        this.orderPosition = orderPosition;
    }

    public Integer getInputValue() {
        return inputValue;
    }

    public void setInputValue(Integer inputValue) {
        this.inputValue = inputValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public Integer getCostForCup() {
        return costForCup;
    }

    public void setCostForCup(Integer costForCup) {
        this.costForCup = costForCup;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

}
