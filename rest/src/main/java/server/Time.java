package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/time")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Time {
    private TimeService timeService = new TimeService();
    private ObjectMapper mapper = new ObjectMapper();

    @POST
    @Path("/formatted_date")
    public Response getFormattedDate(FormatDTO formatDTO) {
        try {
            TimeResponseDTO timeResponseDTO = new TimeResponseDTO();
            timeResponseDTO.setTime(timeService.getFormattedDate(formatDTO.getFormat()));
            return Response.ok(timeResponseDTO).build();
        } catch (TimeParsingException e) {
            return Response.serverError().entity(new ErrorDTO(e.getMessage())).build();
        }
    }

    @POST
    @Path("/timezone_formatted_date")
    public Response getFormattedDateAt(FormatLocaleDTO formatLocaleDTO) {
        try {
            TimeResponseDTO timeResponseDTO = new TimeResponseDTO();
            timeResponseDTO.setTime(timeService.getFormattedDateAt(formatLocaleDTO.getFormat(), formatLocaleDTO.getTimezone()));
            return Response.ok(timeResponseDTO).build();
        } catch (TimeParsingException e) {
            return Response.serverError().entity(new ErrorDTO(e.getMessage())).build();
        }
    }

    @GET
    @Path("/timezones")
    public Response getTimezones() {
        TimezoneDTO timezoneDTO = new TimezoneDTO();
        timezoneDTO.setTimezones(timeService.getValidTimezones());
        return Response.ok(timezoneDTO).build();
    }
}
