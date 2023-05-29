package isel.mpd.spliterators;

import java.io.*;
import java.util.Spliterators;
import java.util.function.Consumer;

public class LinesSpliterator extends Spliterators.AbstractSpliterator<String> {

    private BufferedReader reader;
    private final String textFileName;
    private boolean closed;

    public LinesSpliterator(String textFileName) {
        super(Long.MAX_VALUE, 0);
        this.textFileName = textFileName;
    }

    private boolean ensureReader() {
        try {
            if (closed) return false;
            if (reader == null) reader =
                new BufferedReader(new FileReader(textFileName));
            return true;
        }
        catch(IOException e) {
            close();
            return false;
        }
    }

    @Override
    public boolean tryAdvance(Consumer<? super String> action) {
        if (!ensureReader()) return false;
        try {
            action.accept(reader.readLine());
            return true;
        }
        catch(IOException e) {
            close();
            return false;
        }
    }

    public void close() {
        if (closed) return;
        try {
            if (reader != null) reader.close();
        }
        catch(IOException e) {
        }
        finally {
            closed = true;
            reader = null;
        }
    }
}
