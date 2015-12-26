package com.fernflower.orderbook.enums;

/**
 * Created by SONY on 27.08.2015.
 */
public enum Priority {

    High(3),
    Medium(2),
    Low(1);

    private int p;

    Priority(){this.p=3;}
    Priority (int priority){this.p=priority;}

    int getPriority(){return p;}
}
