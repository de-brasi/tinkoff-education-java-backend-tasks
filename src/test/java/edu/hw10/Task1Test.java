package edu.hw10;

import edu.hw10.task1.RandomObjectGenerator;
import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @Test
    @DisplayName("Base types (Integer, String) objects generation")
    public void test1() {
        var rog = new RandomObjectGenerator();

        var exampleString = rog.nextObject(String.class);
        var exampleInteger = rog.nextObject(Integer.class);

        assertThat(exampleString instanceof String).isTrue();
        assertThat(exampleInteger instanceof Integer).isTrue();
    }

    @Test
    @DisplayName("Class object generation with constructor")
    public void test2() {
        var rog = new RandomObjectGenerator();

        var exampleClassObj = rog.nextObject(Example.class);
        assertThat(exampleClassObj instanceof Example).isTrue();
        assertThat(((Example) exampleClassObj).getName() != null).isTrue();
    }

    @Test
    @DisplayName("Class object generation with builder")
    public void test3() {
        var rog = new RandomObjectGenerator();

        var exampleClassObj = rog.nextObject(Example.class, "buildExample");
        assertThat(exampleClassObj instanceof Example).isTrue();
        assertThat(((Example) exampleClassObj).getName() != null).isTrue();
    }

    @Test
    @DisplayName("Record object")
    public void test4() {
        var rog = new RandomObjectGenerator();

        var exampleRecordObj = rog.nextObject(ExampleRecord.class);
        assertThat(exampleRecordObj instanceof ExampleRecord).isTrue();
        assertThat(((ExampleRecord) exampleRecordObj).name() != null).isTrue();
        assertThat(((ExampleRecord) exampleRecordObj).age() != null).isTrue();
    }

    @Test
    @DisplayName("Some record object")
    public void test5() {
        var rog = new RandomObjectGenerator();

        var exampleRecordWrapperObj = rog.nextObject(RecordWrapper.class);
        assertThat(exampleRecordWrapperObj instanceof RecordWrapper).isTrue();
        assertThat(((RecordWrapper) exampleRecordWrapperObj).entity() != null).isTrue();
        assert ((RecordWrapper) exampleRecordWrapperObj).entity() != null;
        assertThat(((RecordWrapper) exampleRecordWrapperObj).entity().getName() != null).isTrue();
    }

    @Test
    @DisplayName("Record object with annotations")
    public void test6() {
        // TODO
        var rog = new RandomObjectGenerator();

        var exampleRecordObj = rog.nextObject(ExampleRecord.class);
        assertThat(exampleRecordObj instanceof ExampleRecord).isTrue();

        assert exampleRecordObj instanceof ExampleRecord;
        ExampleRecord someEntity = (ExampleRecord) exampleRecordObj;

        assertThat(someEntity.age()).isLessThanOrEqualTo(MAX_NUMERIC_ANNOTATION_VALUE);
        assertThat(MIN_NUMERIC_ANNOTATION_VALUE).isLessThanOrEqualTo(someEntity.age());
    }

    public static class Example {
        public Example(String name) {
            this.name = name;
        }

        public static Example buildExample(String someName) {
            return new Example(someName);
        }

        public String getName() {
            return name;
        }

        private final String name;
    }

    public record ExampleRecord(
        String name,
        @Min(MIN_NUMERIC_ANNOTATION_VALUE) @Max(MAX_NUMERIC_ANNOTATION_VALUE) Integer age
    ) {}

    public record RecordWrapper(Example entity) {}

    private static final Integer MIN_NUMERIC_ANNOTATION_VALUE = 7;
    private static final Integer MAX_NUMERIC_ANNOTATION_VALUE = 18;
}
