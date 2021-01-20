package ru.zhivov.web_tool;

public class DataChecker implements WebToolConst {
    private static String reg_date = "^\\d{2}.\\d{2}.\\d{4}$";
    private static String reg_doc_link = "^htt(p|ps)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";


    public static Boolean doMatch(String value, String webToolConst) {
        switch (webToolConst) {
            case DOC_LINK:
                return value.matches(reg_doc_link);
            case EDIT_DATE:
                return value.matches(reg_date);
            case ACTUAL_DATE:
                return value.matches(reg_date);
        }
        return false;
    }
}
