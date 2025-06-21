package com.estudo.dscommerce.exception.error;

public class FieldMessage {

    private String fieldName;
    private String massage;

    public FieldMessage(String fieldName, String massage) {
        this.fieldName = fieldName;
        this.massage = massage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMassage() {
        return massage;
    }
}
