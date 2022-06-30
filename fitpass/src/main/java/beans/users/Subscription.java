package beans.users;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Subscription {
    private String id;
    private SubscriptionType type;
    private LocalDate payDate;
    private LocalDateTime validUntil;
    private double price;
    private Customer customer;
    private boolean status;
    private int enteringNumber;
}
