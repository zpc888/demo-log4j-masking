package demo.mask.logs;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Plugin(name="PswdMaskingConverter", category = "Converter")
//@ConverterKeys({"spi","trscId"})
public class PswdMaskingConverter extends LogEventPatternConverter{
    private static final String PSWD_REGEX = "password=([^ \\n\\r\\t]*)";
    private static final Pattern PSWD_PATTERN = Pattern.compile(PSWD_REGEX);
    private static final String PSWD_REPLACEMENT_REGEX = "password=*****";


    protected PswdMaskingConverter(String name, String style) {
        super(name, style);
    }

    public static PswdMaskingConverter newInstance(String[] options) {
        return new PswdMaskingConverter("spi",Thread.currentThread().getName());
    }

    @Override
    public void format(LogEvent event, StringBuilder outputMessage) {
        String message = event.getMessage().getFormattedMessage();
        String maskedMessage = message;
        try {
            maskedMessage = mask(message);
        } catch (Exception e) {
            System.out.println("Failed While Masking");
            maskedMessage = message;
        }
        outputMessage.append(maskedMessage);

    }

    private String mask(String message) {
        Matcher matcher =null;
        StringBuffer buffer = new StringBuffer();

        matcher = PSWD_PATTERN.matcher(message);
        maskMatcher(matcher, buffer, PSWD_REPLACEMENT_REGEX);

        return buffer.toString();
    }

    private StringBuffer maskMatcher(Matcher matcher, StringBuffer buffer, String maskStr)
    {
        while (matcher.find()) {
            matcher.appendReplacement(buffer,maskStr);
        }
        matcher.appendTail(buffer);
        return buffer;
    }

}