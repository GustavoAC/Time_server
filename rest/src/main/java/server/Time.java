package server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.FormatDTO;
import model.FormatLocaleDTO;
import model.TimeParsingException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/time")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Time {
    private TimeService timeService = new TimeService();
    private ObjectMapper mapper = new ObjectMapper();

    @POST
    @Path("/getFormattedDate")
    public String getFormattedDate(String json) {
        try {
            FormatDTO format = mapper.readValue(json, FormatDTO.class);

            return "{ \"time\": \"" + timeService.getFormattedDate(format.getFormat()) + "\" }";
        } catch (TimeParsingException e) {
            return "{ \"error\": \"" + e.getMessage() + "\" }";
        } catch (IOException e) {
            return "{ \"error\": \"Error on the parsing of the input\"}";
        }
    }

    @POST
    @Path("/getFormattedDateAt")
    public String getFormattedDateAt(String json) {
        try {
            FormatLocaleDTO formatLocaleDTO = mapper.readValue(json, FormatLocaleDTO.class);

            return "{ \"time\": \"" + timeService.getFormattedDateAt(formatLocaleDTO.getFormat(), formatLocaleDTO.getTimezone()) + "\" }";
        } catch (IOException e) {
            return "{ \"error\": \"Error on the parsing of the input\"}";
        } catch (TimeParsingException e) {
            return "{ \"error\": \"" + e.getMessage() + "\" }";
        }
    }
}
