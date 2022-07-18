package com.legion.utils;

public class Constants {
    public static final String NoLongEligibleTakeShiftErrorMessage = "Error!We are sorry. You are not eligible to claim this shift.";
    public static final String WillTriggerWeeklyOTErrorMessage = "Error!We are sorry. You are not eligible to claim this shift, as it will trigger Over Time for the week.";
    public static final String WillTriggerDailyOTErrorMessage = "Error!We are sorry. You are not eligible to claim this shift, as it will trigger Over Time for the day.";
    public static final String ClaimSuccessMessage = "Success! This shift is yours, and has been added to your schedule.";
    public static final String ClaimRequestBeenSendForApprovalMessage = "Your claim request has been received and sent for approval";
    public static final String Hr = "HR";
    public static final String Ftp = "FTP";
    public static final String Custom = "CUSTOM";
    public static final String Enabled = "ENABLED";
    public static final String Disabled = "DISABLED";
    public static final String Utc = "UTC";
    public static final String Terminate = "Terminate";
    public static final String Activate = "Activate";
    public static final String Transfer = "Transfer";
    public static final String Deactivate = "Deactivate";
    public static final String SendUsername = "Send Username";
    public static final String ResetPassword = "Reset Password";
    public static final String ChangePassword = "Change Password";
    public static final String ManualOnboard = "Manual Onboard";
    public static final String ControlEnterprice = "Vailqacn_Enterprise";
    public static final String OpEnterprice = "CinemarkWkdy_Enterprise";

    //accrual
    public static final String loginUrlRC ="https://rc-enterprise.dev.legion.work/legion/authentication/login";
    public static final String getTemplateByWorkerId ="https://rc-enterprise.dev.legion.work/legion/configTemplate/getTemplateByWorkerAndType";
    public static final String deleteAccrualByWorkerId ="https://rc-enterprise.dev.legion.work/legion/accrual/deleteAccrualByWorkerId";
    public static final String runAccrualJobWithSimulateDateAndWorkerId="https://rc-enterprise.dev.legion.work/legion/accrual/runAccrualJobWithSimulateDateAndWorkerId";
    public static final String toggles ="https://rc-enterprise.dev.legion.work/legion/toggles";
    public static final String getHoliday ="https://rc-enterprise.dev.legion.work/legion/metadata/getHolidays";

    //downloadTranslation
    public static final String downloadTransation1 ="https://rc-enterprise.dev.legion.work/legion/translation/downloadTranslations";

    //accessRole
    public static final String uploadUserAccessRole ="https://rc-enterprise.dev.legion.work/legion/integration/uploadUserAccessRole";

    //uploadTranslation
    public static final String uploadTransation ="https://rc-enterprise.dev.legion.work/legion/translation/uploadTranslations";

    //copyWorkRolesFromControlsToOP
    public static final String copyWorkRole ="https://rc-enterprise.dev.legion.work/legion/configTemplate/copyWorkRolesFromControlsToOP";

    //uploadFiscalCalendar
    public static final String uploadFiscalCalendar = "https://rc-enterprise.dev.legion.work/legion/fiscalCalendars/upload";

    //refreshCache
    public static final String refreshCache = "https://rc-enterprise.dev.legion.work/legion/cache/refreshCache";
}
