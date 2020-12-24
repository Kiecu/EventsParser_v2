package Infrastructure.Services;

import java.io.InputStream;
import java.util.ArrayList;

public interface StreamParser<T> {
    ArrayList<T> Parse(InputStream stream);
}
