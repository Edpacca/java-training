package scottlogic.javatraining.interfaces;

import scottlogic.javatraining.models.Order;

public interface IFilterCondition {
    boolean isMatch (Order dBOrder, Order newOrder);
}
