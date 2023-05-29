package isel.mpd.spliterators;

import isel.mpd.sequences.StreamUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static isel.mpd.sequences.StreamUtils.concat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpliteratorsTests {

    @Test
    public void concatOfTwoStreamsOfIntegers() {
        var s1 = Stream.of(1,3,2);
        var s2 = Stream.of(4,5,6);

        var lRes = concat(s1, s2).collect(Collectors.toList());


        assertEquals(6, lRes.size());

        assertEquals(List.of(1,3,2,4,5,6), lRes );
    }
    @Test
    public void linesStreamOnInexistentFileProduceEmptyStream() {
        Stream<String> fileLines =
            StreamUtils.lines("xx");

        try(fileLines) {
            assertEquals(Optional.empty(), fileLines.findFirst());
        }

    }

    @Test
    public void countLinesStreamForAnExistentTextFile() {
        Stream<String> fileLines =
            StreamUtils.lines("LinesSpliterator.java");
        var expected = 56;
        try(fileLines) {
            assertEquals(expected, fileLines.count());
        }
    }

    @Test
    public void getFirstLineOfLinesStreamForAnExistentTextFile() {
        Stream<String> fileLines =
            StreamUtils.lines("LinesSpliterator.java");
        var expected = Optional.of("package isel.mpd.spliterators;");
        try(fileLines) {
            assertEquals(expected, fileLines.findFirst());
        }
    }
}
