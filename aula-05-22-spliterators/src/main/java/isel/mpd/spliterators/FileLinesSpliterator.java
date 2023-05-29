package isel.mpd.spliterators;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Spliterators;
import java.util.function.Consumer;

public class FileLinesSpliterator extends Spliterators.AbstractSpliterator<String> {
    private final String fileName;
    private BufferedReader reader;
    private boolean closed;

    public FileLinesSpliterator(String textFileName) {
        super(Long.MAX_VALUE, 0);
        this.fileName = textFileName;
    }

    private void ensureReaderInit() {
        if (closed) return;
        if (reader == null) {
            try {
                reader = new BufferedReader(new FileReader(fileName));
            }
            catch(IOException e) {
               closed = true;
            }
        }
    }

    private void close() {
        if (closed) return;
        if (reader != null) {
            try {
                reader.close();
            }
            catch(IOException e) {}
        }
        closed = true;
    }

    private String getNextLine() {
        ensureReaderInit();
        if (closed) return null;
        try {
            return reader.readLine();
        }
        catch(Exception e) {
           close();
           return null;
        }
    }

    @Override
    public boolean tryAdvance(Consumer<? super String> action) {
        var line = getNextLine();
        if (line != null) {
            action.accept(line);
            return true;
        }
        return false;
    }
}
