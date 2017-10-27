package com.sciencom.osd.enums;

public enum RoleEnum {
    ADMIN(1), MANAGER(2);

    private int value;

    RoleEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static RoleEnum parse(int id){
        for (RoleEnum item: RoleEnum.values()){
            if (item.getValue() == id){
                return item;
            }
        }
        return null;
    }
}
