package rest.server;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TimeService {
    private static List<String> validTimezones = Arrays.asList(TimeZone.getAvailableIDs());

    public String getFormattedDate(String format) {
        return getFormattedDateAt(format, TimeZone.getDefault().getID());
    }

    public String getFormattedDateAt(String format, String timezone) {
        if (!isTimezoneValid(timezone)) {
            return "Invalid Timezone";
        }

        Date date = new Date();
        SimpleDateFormat sdf;

        try {
            sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(TimeZone.getTimeZone(timezone));
            return sdf.format(date);
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder("Invalid date format.\n");
            sb.append("\ty   = year   (yy or yyyy)\n")
                    .append("\tM   = month  (MM)\n")
                    .append("\td   = day in month (dd)\n")
                    .append("\th   = hour (0-12)  (hh)\n")
                    .append("\tH   = hour (0-23)  (HH)\n")
                    .append("\tm   = minute in hour (mm)\n")
                    .append("\ts   = seconds (ss)\n")
                    .append("\tS   = milliseconds (SSS)\n")
                    .append("\tz   = time zone  text        (e.g. Pacific Standard Time...)\n")
                    .append("\tZ   = time zone, time offset (e.g. -0800)\n")
                    .append("Example: yyyy-MM-dd");

            return sb.toString();
        }
    }

    private boolean isTimezoneValid(String timezone) {
        return validTimezones.contains(timezone);
    }
}
