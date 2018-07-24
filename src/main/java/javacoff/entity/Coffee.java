package javacoff.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String coffeeName;

    private Integer costForCup;

    private boolean isDisabled;

    @OneToMany(mappedBy = "coffee")
    private Set<Coffee> coffee;

    public Coffee() {}

    public Coffee(String coffeeName, Integer costForCup, boolean isDisabled) {
        this.coffeeName = coffeeName;
        this.costForCup = costForCup;
        this.isDisabled = isDisabled;
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
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }
}
