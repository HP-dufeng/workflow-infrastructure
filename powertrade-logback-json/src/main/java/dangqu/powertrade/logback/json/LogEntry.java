package dangqu.powertrade.logback.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.*;


import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("Category")
    private String category;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("LogLevel")
    private String logLevel;
    @JsonProperty("Exception")
    private String exception;
}
