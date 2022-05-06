package org.users.constant;

/**
 * ProjectEnum
 *
 * @author YL.
 * @version V1.0
 * @date 2022-05-04 9:23 PM:51
 */
public enum ProjectEnum {
    USER("user"),
    ROLE("role");

    ProjectEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
