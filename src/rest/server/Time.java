package rest.server;

import rest.model.FormatLocaleDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("time")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Time {
    private TimeService timeService = new TimeService();

    @POST
    @Path("getFormattedDate")
    public String getFormattedDate(String format) {
        return timeService.getFormattedDate(format);
    }

    @POST
    @Path("getFormattedDateAt")
    public String getFormattedDateAt(FormatLocaleDTO formatLocaleDTO) {
        return timeService.getFormattedDateAt(formatLocaleDTO.getFormat(), formatLocaleDTO.getTimezone());
    }
}
