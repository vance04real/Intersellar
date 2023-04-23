package za.co.discovery.assignment.evanschikuni.utils.enums;

import lombok.Getter;

@Getter
public enum ExcelSheetType {

    PLANET("Planet Names"),
    ROUTE("Routes"),
    TRAFFIC("Traffic");

    public final String value;

    ExcelSheetType(String value) {
        this.value = value;
    }
}
