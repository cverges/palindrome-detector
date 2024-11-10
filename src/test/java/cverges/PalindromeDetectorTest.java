package cverges;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for the {@link PalindromeDetector}.
 */
public class PalindromeDetectorTest {

    @BeforeEach
    public void setUp() throws Exception {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.ALL);
    }

    @ParameterizedTest(name = "{index}. {0} -> {1}")
    @MethodSource("palindromesWithDefaultSettingsProvider")
    public void palindromesWithDefaultSettings(final String candidate, final Boolean expected)
    {
        final PalindromeDetector detector = PalindromeDetector.builder().build();
        assertEquals(expected, detector.check(candidate));
    }

    private static Stream<Arguments> palindromesWithDefaultSettingsProvider() {
        return Stream.of(
            // Happy path test cases
            Arguments.of(null, true),
            Arguments.of("", true),
            Arguments.of(" ", true),
            Arguments.of("  ", true),
            Arguments.of("a", true),
            Arguments.of(" a", true),
            Arguments.of("a ", true),
            Arguments.of(" a ", true),
            Arguments.of("  a ", true),
            Arguments.of("racecar", true),
            Arguments.of("r a d   a r", true),
            Arguments.of("r a   d a r", true),
            Arguments.of("Tacocat", true),
            Arguments.of("osó", true),
            Arguments.of("Osó", true),
            Arguments.of("A man, a plan, a canal -- Panama", true),
            Arguments.of("T!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~acocat", true),
            Arguments.of("f\n\r\toof", true)
        );
    }

}
