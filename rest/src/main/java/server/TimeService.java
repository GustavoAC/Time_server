package server;

import model.TimeParsingException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TimeService {
    private static List<String> validTimezones = Arrays.asList(TimeZone.getAvailableIDs());

    public String getFormattedDate(String format) throws TimeParsingException {
        return getFormattedDateAt(format, TimeZone.getDefault().getID());
    }

    public String getFormattedDateAt(String format, String timezone) throws TimeParsingException {
        if (!isTimezoneValid(timezone)) {
            StringBuilder str = new StringBuilder();
            for (String t : validTimezones) {
                str.append(t);
                str.append(", ");
            }

            throw new TimeParsingException("Invalid Timezone. Valid timezones are: " + str.toString());
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

            throw new TimeParsingException(sb.toString());
        }
    }

    public List<String> getValidTimezones() {
        return validTimezones;
    }

    private boolean isTimezoneValid(String timezone) {
        return validTimezones.contains(timezone);
    }
}
