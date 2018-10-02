package me.k11i.log4j2.stackdriver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.util.StringBuilderWriter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@Plugin(name = "StackdriverFriendlyJsonLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE)
public class StackdriverFriendlyJsonLayout extends AbstractStringLayout {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @PluginFactory
    public static StackdriverFriendlyJsonLayout create() {
        return new StackdriverFriendlyJsonLayout();
    }

    public StackdriverFriendlyJsonLayout() {
        this(StandardCharsets.UTF_8);
    }

    protected StackdriverFriendlyJsonLayout(Charset charset) {
        super(charset);
    }

    @Override
    public String toSerializable(LogEvent event) {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("timestampSeconds", event.getTimeMillis() / 1000);
        map.put("timestampNanos", (event.getTimeMillis() % 1000) * 1_000_000);
        putIfNotNull("severity", event.getLevel().toString(), map);
        putIfNotNull("thread", event.getThreadName(), map);
        putIfNotNull("logger", event.getLoggerFqcn(), map);
        putIfNotNull("message", event.getMessage().getFormattedMessage(), map);
        putIfNotNull("exception", getThrowableAsString(event.getThrownProxy()), map);

        try {
            StringBuilderWriter writer = new StringBuilderWriter();
            OBJECT_MAPPER.writeValue(writer, map);
            writer.append('\n');
            return writer.toString();
        } catch (Exception e) {
            LOGGER.error(e);
            return null;
        }
    }

    private String getThrowableAsString(ThrowableProxy thrownProxy) {
        if (thrownProxy != null) {
            return thrownProxy.getExtendedStackTraceAsString();
        }
        return null;
    }

    private void putIfNotNull(String key, String value, Map<String, Object> map) {
        if (value != null) {
            map.put(key, value);
        }
    }
}
