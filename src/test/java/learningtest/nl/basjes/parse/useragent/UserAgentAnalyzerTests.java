package learningtest.nl.basjes.parse.useragent;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link UserAgentAnalyzer}.
 *
 * @author Johnny Lim
 */
class UserAgentAnalyzerTests {

    @Test
    void parse() {
        UserAgentAnalyzer analyzer = UserAgentAnalyzer.newBuilder().build();
        UserAgent.ImmutableUserAgent agent = analyzer.parse("Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko)");
        System.out.println(agent);
        assertThat(agent.getValue(UserAgent.OPERATING_SYSTEM_NAME)).isEqualTo("iOS");
        assertThat(agent.getValue(UserAgent.OPERATING_SYSTEM_VERSION)).isEqualTo("16.6");
    }

    @Test
    void parseWithField() {
        UserAgentAnalyzer analyzer = UserAgentAnalyzer.newBuilder().withField(UserAgent.OPERATING_SYSTEM_NAME).withCache(100).build();
        UserAgent.ImmutableUserAgent agent = analyzer.parse("Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko)");
        System.out.println(agent);
        assertThat(agent.getValue(UserAgent.OPERATING_SYSTEM_NAME)).isEqualTo("iOS");
        assertThat(agent.getValue(UserAgent.OPERATING_SYSTEM_VERSION)).isEqualTo("??");
    }

}
