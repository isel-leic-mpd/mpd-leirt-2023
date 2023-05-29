package isel.mpd.sequences;

import isel.mpd.spliterators.ConcatSpliterator;
import isel.mpd.spliterators.FileLinesSpliterator;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {
    public static <T> Stream<T> concat(Stream<T> s1, Stream<T> s2) {
        return StreamSupport.stream(new ConcatSpliterator<>(s1,s2), false );
    }

    public static  Stream<String> lines(String textFileName) {

        return StreamSupport.stream(new FileLinesSpliterator(textFileName), false );
    }

}
