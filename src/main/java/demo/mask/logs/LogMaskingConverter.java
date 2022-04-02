package demo.mask.logs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

@Plugin(name="LogMaskingConverter", category = "Converter")
@ConverterKeys({"spi"})
public class LogMaskingConverter extends LogEventPatternConverter{
    private static final String CREDIT_CARD_REGEX = "([0-9]{16})";;
    private static final Pattern CREDIT_CARD_PATTERN = Pattern.compile(CREDIT_CARD_REGEX);
    private static final String CAREDIT_CARD_REPLACEMENT_REGEX = "XXXXXXXXXXXXXXXX";

    private static final String CVV_REGEX = "([0-9]{3})";
    private static final Pattern CVV_PATTERN = Pattern.compile(CVV_REGEX);
    private static final String CVV_REPLACEMENT_REGEX = "+++";

    private static final String SSN_REGEX = "([0-9]{9})";
    private static final Pattern SSN_PATTERN = Pattern.compile(SSN_REGEX);
    private static final String SSN_REPLACEMENT_REGEX = "*********";

    private static final String PSWD_REGEX = "password=([^ \\n\\r\\t]*)";
    private static final Pattern PSWD_PATTERN = Pattern.compile(PSWD_REGEX);
    private static final String PSWD_REPLACEMENT_REGEX = "password=########";

    protected LogMaskingConverter(String name, String style) {
        super(name, style);
    }

    public static LogMaskingConverter newInstance(String[] options) {
        return new LogMaskingConverter("spi",Thread.currentThread().getName());
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

        matcher = CREDIT_CARD_PATTERN.matcher(message);
        maskMatcher(matcher, buffer,CAREDIT_CARD_REPLACEMENT_REGEX);
        message=buffer.toString();
        buffer.setLength(0);

        matcher = SSN_PATTERN.matcher(message);
        maskMatcher(matcher, buffer,SSN_REPLACEMENT_REGEX);
        message=buffer.toString();
        buffer.setLength(0);

        matcher = CVV_PATTERN.matcher(message);
        maskMatcher(matcher, buffer,CVV_REPLACEMENT_REGEX);
        message=buffer.toString();
        buffer.setLength(0);

        matcher = PSWD_PATTERN.matcher(message);
        maskMatcher(matcher, buffer,PSWD_REPLACEMENT_REGEX);

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