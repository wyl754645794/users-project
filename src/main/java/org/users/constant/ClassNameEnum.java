package org.users.constant;

/**
 * ClassNameEnum
 *
 * @author YL.
 * @version V1.0
 * @date 2022-05-04 10:28 AM:11
 */
public enum  ClassNameEnum {

    USER("org.users.model.User"),ROLE("org.users.model.Role");

    private ClassNameEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

}
