# Getting Started

### How to mask sensitive data in log4j

1. customize LogEventPatternConverter 
 
```java
// create customized LogEventPatternConverter class to implement format() method, inside this method to replace the 
// sensitive data by pattern matches. Its converter keys must match the definition from configuration file.

@Plugin(name="LogMaskingConverter", category = "Converter")
@ConverterKeys({"sensitiveMask"})
public class LogMaskingConverter extends LogEventPatternConverter {
    // .... ....
}

```

2. log4j configuration file

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration xmlns="http://logging.apache.org/log4j/2.0/config" status="OFF" 
               packages="${customized converter package}">
    <!-- exmaple
           packages="demo.mask.logs">
    -->
<Appenders>
<Console name="STDOUT" target="SYSTEM_OUT">
    <PatternLayout pattern="%d{yyyyMMdd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %${ConverterKeys in customized converter}%n"/>
    <!-- exmaple
    <PatternLayout pattern="%d{yyyyMMdd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %sensitiveMask%n"/>
    -->
        </Console>
    </Appenders>
    <loggers>
        <Logger name="org.apache.log4j.xml" level="all" />
        <root level="all">
            <appender-ref ref="STDOUT" level="TRACE" />
        </root>
    </loggers>
</configuration>
```
