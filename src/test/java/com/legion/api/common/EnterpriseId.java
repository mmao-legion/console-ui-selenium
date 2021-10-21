package com.legion.api.common;

public enum EnterpriseId {
    kendrascott2("67840cca-5c4a-4411-8c6a-211fe631eb10"),
    LegionCoffee("4f31d62f-b3cd-4b67-87ab-40b39c9c5626"),
    LegionCoffee2("feaa92d6-4bae-4af6-8051-80b043469091"),
    vailqacn("40f80a2d-21b4-4507-8ba9-3b596829bd24"),
    Vail3("19b538bc-e524-45ea-9e5c-c9ab10eec0dd"),
    Dgstage("fac86359-4880-45c3-943e-d414abbdf7aa"),
    Americold("65f71271-fa70-4920-a5ce-aa11d70994b0"),
    DGRC("85887433-cac7-445f-9ba3-564894585921"),
    DGCH("afddcae2-2960-4900-8e9f-f8ca2985140d"),
    CircleK("d9eaeb1c-c30d-4f93-aca6-cf8345e3a67e"),
    OP("fee4f15e-bbe6-41dc-84c8-47e6352d6f18"),
    OPAUTO("aee2dfb5-387d-4b8b-b3f5-62e86d1a9d95"),
    Panda2("ff0ebe2d-2fc1-465e-a58e-73e2e6f4a9f6"),
    RT("ccf4508d-d38b-4e06-b8bb-e72d55ceab5a"),
    Cinemark_wkdy("781c5d8f-fd6d-47a5-888a-abd1d3a6961c");
    private final String value;
    EnterpriseId(final String newValue){
        value = newValue;
    }
    public String getValue(){
        return value;
    }
}
