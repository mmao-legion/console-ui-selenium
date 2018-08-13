package com.legion.pages;

import java.util.HashMap;

public interface SchedulePage {
	public void gotoToSchedulePage() throws Exception;
	public boolean isSchedulePage() throws Exception;


	public void goToProjectedSales() throws Exception;
    public boolean isProjectedSales() throws Exception;


    public void goToStaffingGuidance() throws Exception;
    public boolean isStaffingGuidance() throws Exception;

    public void goToSchedule() throws Exception;
    public boolean isSchedule() throws Exception;

    public boolean editWeeklySchedule(HashMap<String,String> propertyMap) throws Exception;
}
