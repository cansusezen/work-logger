import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by c.sezen on 15.4.2016.
 */
public class DateHelper {
    static Date parseToDate(String dateFormat, String dateString) throws ParseException {
        return (new SimpleDateFormat(dateFormat)).parse(dateString);
    }
}
