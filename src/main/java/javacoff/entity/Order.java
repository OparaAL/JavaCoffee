package javacoff.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientName;

    private String address;

    private Double cost;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "order")
    private Set<OrderPosition> orderPosition;

    public Order(){}

    public Order(String clientName, String address, Double cost, Date date) {
        this.clientName = clientName;
        this.address = address;
        this.cost = cost;
        this.date = date;
    }

    public Set<OrderPosition> getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(Set<OrderPosition> orderPosition) {
        this.orderPosition = orderPosition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
