package pro.hexa.backend.domain.model.model;

public interface EnumModel {

    default EnumValue getEnumValue() {
        return new EnumValue(getKey(), getValue());
    }

    String getKey();

    String getValue();
}
