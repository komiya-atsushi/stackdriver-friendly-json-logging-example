package me.k11i.logback.stackdriver;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;

import java.util.Map;

public class StackdriverFriendlyJsonLayout extends JsonLayout {
    @Override
    protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent event) {
        long timestampMillis = event.getTimeStamp();
        map.put("timestampSeconds", timestampMillis / 1000);
        map.put("timestampNanos", (timestampMillis % 1000) * 1_000_000);
        map.put("severity", String.valueOf(event.getLevel()));
    }
}
