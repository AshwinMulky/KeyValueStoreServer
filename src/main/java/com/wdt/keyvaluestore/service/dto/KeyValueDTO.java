package com.wdt.keyvaluestore.service.dto;

import org.hibernate.validator.constraints.NotBlank;

public class KeyValueDTO {

    @NotBlank
    private String key;

    @NotBlank
    private String value;

    public KeyValueDTO() {}

    public KeyValueDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyValueDTO{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
