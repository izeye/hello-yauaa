package learningtest.nl.basjes.parse.useragent;

import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Memory usage tests for {@link UserAgentAnalyzer}.
 *
 * @author Johnny Lim
 */
class UserAgentAnalyzerMemoryTests {

    @Disabled("With default Gradle test task max heap size (512 MB), 4th instance creation triggers OOM.")
    @Test
    void test() {
        test(() -> createInitialUserAgentAnalyzer(), (initialUserAgentAnalyzer) -> createInitialUserAgentAnalyzer());
    }

    private UserAgentAnalyzer createInitialUserAgentAnalyzer() {
        return UserAgentAnalyzer.newBuilder().withCache(0).build();
    }

    @Disabled("With default Gradle test task max heap size (512 MB), 5th instance creation triggers OOM.")
    @Test
    void testWithCloneWithSharedAnalyzerConfig() {
        test(() -> createInitialUserAgentAnalyzer(), (initialUserAgentAnalyzer) -> initialUserAgentAnalyzer.cloneWithSharedAnalyzerConfig(false, false));
    }

    void test(Supplier<UserAgentAnalyzer> initialUserAgentAnalyzerFactory, Function<UserAgentAnalyzer, UserAgentAnalyzer> subsequentUserAgentAnalyzerFactory) {
        List<UserAgentAnalyzer> analyzers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            System.out.println("i: " + i);

            System.gc();
            long beforeMemory = Runtime.getRuntime().totalMemory();

            UserAgentAnalyzer analyzer = i == 0 ? initialUserAgentAnalyzerFactory.get() : subsequentUserAgentAnalyzerFactory.apply(analyzers.get(0));
            analyzer.parse("Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko)");
            analyzers.add(analyzer);

            System.gc();
            long afterMemory = Runtime.getRuntime().totalMemory();

            long diffMemory = afterMemory - beforeMemory;
            System.out.println("beforeMemory: " + beforeMemory);
            System.out.println("afterMemory: " + afterMemory);
            System.out.println("diffMemory: " + diffMemory);
        }
    }

}
