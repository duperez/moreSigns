package duperez.moresigns;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** The language files are generated; this keeps any of them from missing a line English has. */
class LanguageFilesTest {
    private static final Path LANG = Path.of("src/generated/resources/assets/moresigns/lang");
    private static final Pattern ENTRY = Pattern.compile("\"([^\"]+)\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"");

    private static Set<String> keys(Path file) throws IOException {
        Set<String> keys = new TreeSet<>();
        Matcher matcher = ENTRY.matcher(Files.readString(file));
        while (matcher.find()) {
            keys.add(matcher.group(1));
        }
        return keys;
    }

    @Test
    void englishExistsAndEveryOtherLanguageHasTheSameKeys() throws IOException {
        Set<String> english = keys(LANG.resolve("en_us.json"));
        assertFalse(english.isEmpty(), "en_us.json is the default language and must exist");
        try (Stream<Path> files = Files.list(LANG)) {
            for (Path file : files.filter(path -> path.toString().endsWith(".json")).toList()) {
                assertEquals(english, keys(file), file.getFileName() + " must have exactly the keys of en_us.json");
            }
        }
    }

    @Test
    void portugueseAndSpanishAreProvided() {
        assertTrue(Files.exists(LANG.resolve("pt_br.json")));
        assertTrue(Files.exists(LANG.resolve("es_es.json")));
    }

    @Test
    void placeholdersMatchTheEnglishOnes() throws IOException {
        Pattern placeholder = Pattern.compile("%(\\d+\\$)?s");
        Matcher english = ENTRY.matcher(Files.readString(LANG.resolve("en_us.json")));
        while (english.find()) {
            Set<String> expected = placeholders(placeholder, english.group(2));
            for (String locale : new String[]{"pt_br", "es_es"}) {
                Matcher other = Pattern.compile("\"" + Pattern.quote(english.group(1)) + "\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"")
                        .matcher(Files.readString(LANG.resolve(locale + ".json")));
                assertTrue(other.find(), locale + " is missing " + english.group(1));
                assertEquals(expected, placeholders(placeholder, other.group(1)), locale + " " + english.group(1) + " must use the same placeholders");
            }
        }
    }

    private static Set<String> placeholders(Pattern placeholder, String text) {
        Set<String> found = new TreeSet<>();
        Matcher matcher = placeholder.matcher(text);
        int position = 1;
        while (matcher.find()) {
            found.add(matcher.group(1) != null ? matcher.group(1) : (position++) + "$");
        }
        return found;
    }
}
