package enumTypeTest;

import annotations.EnumConstantAnnotation;

public enum EnumConstantValue {
    @EnumConstantAnnotation
    CONSTANT("VALUE");

    private String value;

    EnumConstantValue(String value) {
        this.value = value;
    }
}