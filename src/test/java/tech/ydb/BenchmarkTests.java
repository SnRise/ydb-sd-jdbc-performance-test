package tech.ydb;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Madiyar Nurgazin
 */
@SpringBootTest
@State(Scope.Benchmark)
@BenchmarkMode({Mode.Throughput, Mode.SampleTime})
@OutputTimeUnit(TimeUnit.SECONDS)
public class BenchmarkTests {

    private static tech.ydb.springdata.PersonRepository personRepositorySpringDataJdbc;
    private static tech.ydb.jdbc.PersonRepository personRepositoryJdbc;

    @Autowired
    public void setPersonRepositoryJdbc(tech.ydb.jdbc.PersonRepository personRepositoryJdbc) {
        BenchmarkTests.personRepositoryJdbc = personRepositoryJdbc;
    }

    @Autowired
    public void setPersonRepositorySpringDataJdbc(tech.ydb.springdata.PersonRepository personRepositorySpringDataJdbc) {
        BenchmarkTests.personRepositorySpringDataJdbc = personRepositorySpringDataJdbc;
    }

    @Test
    public void runBenchmarks() throws Exception {
        Options opts = new OptionsBuilder()
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                .warmupIterations(1)
                .measurementIterations(1)
                .forks(0)
                .threads(16)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .resultFormat(ResultFormatType.CSV)
                .result("benchmark-result/" + System.currentTimeMillis() + ".csv")
                .build();
        new Runner(opts).run();
    }

    @Benchmark
    public void findAllWithLimitJdbc() {
        List<tech.ydb.jdbc.Person> result = personRepositoryJdbc.findAllWithLimit();
    }

    @Benchmark
    public void findAllWithLimitSpringDataJdbc() {
        List<tech.ydb.springdata.Person> result = personRepositorySpringDataJdbc.findAllWithLimit();
    }

    @Benchmark
    public void findAllByIdBetweenJdbc(FromToIds parameters) {
        String[] fromToId = parameters.splitFromToId();
        int from = Integer.parseInt(fromToId[0]);
        int to = Integer.parseInt(fromToId[1]);
        List<tech.ydb.jdbc.Person> result = personRepositoryJdbc.findByIdBetween(from, to);
    }

    @Benchmark
    public void findAllByIdBetweenSpringDataJdbc(FromToIds parameters) {
        String[] fromToId = parameters.splitFromToId();
        int from = Integer.parseInt(fromToId[0]);
        int to = Integer.parseInt(fromToId[1]);
        List<tech.ydb.springdata.Person> result = personRepositorySpringDataJdbc.findByIdBetween(from, to);
    }

    @State(value = Scope.Benchmark)
    public static class FromToIds {
        @Param({ "25-75", "100-200", "250-400", "500-700", "850-1100", "1200-1500", "1600-1950", "2100-2500",
                "2650-3100", "3300-3800", "4000-4600", "4800-5500", "5700-6500", "6800-7700", "8000-9000" })
        String fromToIds;

        String[] splitFromToId() {
            return fromToIds.split("-");
        }
    }
}
