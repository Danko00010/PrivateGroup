package ru.privatee.bot.utils;

public enum AcceptType {
	OneMoth("1 �����"),Forever("��������"), Null("�����������");
    private String type;
    private AcceptType(String typename){
        this.type=typename;
    }

    @Override
    public String toString(){
        return String.valueOf(this.type);
    } 
}
