package com.tenx.ms.retail.order.constants;

public enum OrderStatusEnum {

    ORDERED, PACKING, SHIPPED;

    public static OrderStatusEnum parse(int ordinal){

        for(OrderStatusEnum e : OrderStatusEnum.values()) {
            if(e.ordinal() == ordinal) {
                return e;
            }
        }

        return null;
    }

}
