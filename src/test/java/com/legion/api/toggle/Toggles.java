package com.legion.api.toggle;

public enum Toggles {

    UseLegionAccrual("UseLegionAccrual"),
    MinorRulesTemplate("MinorRulesTemplate"),
    DynamicGroupV2("DynamicGroupV2"),
    ScheduleShowFullNames("ScheduleShowFullNames"),
    EnableDemandDriverTemplate("EnableDemandDriverTemplate"),
    MixedModeDemandDriverSwitch("MixedModeDemandDriverSwitch"),
    MealAndRestTemplate("MealAndRestTemplate"),
    ScheduleEditShiftTimeNew( "ScheduleEditShiftTimeNew"),
    EnableTahoeStorage("EnableTahoeStorage"),
    EnableLongTermBudgetPlan("EnableLongTermBudgetPlan"),
    ShowAnnouncementGroupOP("ShowAnnouncementGroupOP"),
    UseDemandDriverTemplateSwitch("UseDemandDriverTemplateSwitch"),
    EnableChangeLocationGroupSetting("EnableChangeLocationGroupSetting");
    private final String value;

    Toggles(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
