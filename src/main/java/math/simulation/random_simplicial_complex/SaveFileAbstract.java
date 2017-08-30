package math.simulation.random_simplicial_complex;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class SaveFileAbstract {

    private static String saveCode;

    public static String generateNameBase() {
        DateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd-HH-mm-ss");
        return (dateFormat.format(Calendar.getInstance().getTime())).replace(".", "_");
    }

    public static String generateSaveCode() {
        return String.format("./exports/%s", generateNameBase());
    }

    public static String getSaveCode() {
        if (saveCode == null || saveCode.length() == 0) {
            saveCode = generateSaveCode();
        }
        return saveCode;
    }

    public static void setSaveCode(String code) {
        saveCode = String.format("./exports/%s", code);
    }

    protected String loadCode = SaveFileAbstract.getSaveCode();

    public String getLoadCode() {
        return loadCode;
    }

    public void setLoadCode(String aLoadCode) {
        loadCode = aLoadCode;
    }

    public String getSaveSuffix() {
        return this.getClass().getSimpleName();
    }

    public String getFullSaveName() {
        return String.format("%s.%s", getSaveCode(), getSaveSuffix());
    }

    public String getFullLoadName() {
        return String.format("./exports/%s.%s", getLoadCode(), getSaveSuffix());
    }

}
