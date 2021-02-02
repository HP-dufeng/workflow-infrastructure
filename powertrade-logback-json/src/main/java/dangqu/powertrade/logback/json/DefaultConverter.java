package dangqu.powertrade.logback.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.CoreConstants;

public class DefaultConverter extends ClassicConverter {

   private ObjectMapper mapper = new ObjectMapper();

   @Override
   public String convert(ILoggingEvent iLoggingEvent) {
       String category = iLoggingEvent.getLoggerName();
       String logLevel = toCommonLogLevelStr(iLoggingEvent.getLevel());
       String message = iLoggingEvent.getMessage();
       String exception= getThrowableEntry(iLoggingEvent.getThrowableProxy());
       LogEntry entry = new LogEntry(category,message,logLevel,exception);
       try {
           return mapper.writeValueAsString(entry);
       } catch (JsonProcessingException e) {
           e.printStackTrace();
           return "";
       }
   }

   private String toCommonLogLevelStr(Level logLevel) {
       if (logLevel == Level.DEBUG) {
           return "Debug";
       } else if (logLevel == Level.INFO) {
           return "Information";
       } else if (logLevel == Level.ERROR) {
           return "Error";
       } else if (logLevel == Level.TRACE) {
           return "Trace";
       } else if (logLevel == Level.WARN) {
           return "Warning";
       } else {
           return "Error";
       }
   }
   private String getThrowableEntry(IThrowableProxy throwableProxy){
       if(throwableProxy==null){
           return null;
       }
       StringBuilder builder = new StringBuilder();
       for (StackTraceElementProxy step : throwableProxy
               .getStackTraceElementProxyArray()) {
           String string = step.toString();
           builder.append(string);
           ThrowableProxyUtil.subjoinPackagingData(builder, step);
           builder.append(CoreConstants.LINE_SEPARATOR);
       }
       return builder.toString();
   }
}
