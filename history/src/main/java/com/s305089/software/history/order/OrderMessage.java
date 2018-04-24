package com.s305089.software.history.order;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class OrderMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userID;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal total;
    private BigDecimal remaningTotal;
    private BigDecimal remainingAmount;
    private BigDecimal tradedTotal;
    private BigDecimal tradedAmount;
    private String market;
    private String transactionType;
    private Boolean active = true;
    private Date timestamp = new Date();

    @Override
    public String toString() {
        return "OrderMessage{" +
                "id=" + id +
                ", userID='" + userID + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", total=" + total +
                ", remaningTotal=" + remaningTotal +
                ", remainingAmount=" + remainingAmount +
                ", tradedTotal=" + tradedTotal +
                ", tradedAmount=" + tradedAmount +
                ", market='" + market + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", active=" + active +
                ", timestamp=" + timestamp +
                '}';
    }
}
