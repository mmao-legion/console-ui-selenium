package com.legion.api.toggle;

public enum Toggles {

    UseLegionAccrual("UseLegionAccrual"),
    MinorRulesTemplate("MinorRulesTemplate"),
    ScheduleShowFullNames("ScheduleShowFullNames");

    private final String value;

    Toggles(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
