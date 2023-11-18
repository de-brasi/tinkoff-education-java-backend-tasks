// Task 3

package edu.hw6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

@FunctionalInterface
public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    default AbstractFilter and(AbstractFilter otherFilter) {
        return new AbstractFilter() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return AbstractFilter.this.accept(entry) && otherFilter.accept(entry);
            }
        };
    }

    default AbstractFilter or(AbstractFilter otherFilter) {
        return new AbstractFilter() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return AbstractFilter.this.accept(entry) || otherFilter.accept(entry);
            }
        };
    }

    default AbstractFilter not() {
        return new AbstractFilter() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return !AbstractFilter.this.accept(entry);
            }
        };
    }

}
