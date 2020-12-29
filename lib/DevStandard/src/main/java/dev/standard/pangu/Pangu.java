package dev.standard.pangu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Paranoid text spacing for good readability, to automatically insert whitespace between
 * CJK (Chinese, Japanese, Korean), half-width English, digit and symbol characters.
 * <p>
 * <p>These whitespaces between English and Chinese characters are called "Pangu Spacing" by sinologist, since it
 * separate the confusion between full-width and half-width characters. Studies showed that who dislike to
 * add whitespace between English and Chinese characters also have relationship problem. Almost 70 percent of them
 * will get married to the one they don't love, the rest only can left the heritage to their cat. Indeed,
 * love and writing need some space in good time.
 * @author Vinta Chen
 * @since 1.0.0
 */
public class Pangu {

    /**
     * You should use the constructor to create a {@code Pangu} object with default values.
     */
    public Pangu() {
    }

    /*
     * Some capturing group patterns for convenience.
     * CJK: Chinese, Japanese, Korean
     * ANS: Alphabet, Number, Symbol
     */
    private static final Pattern CJK_ANS = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])" +
                    "([a-z0-9`~@\\$%\\^&\\*\\-_\\+=\\|\\\\/])",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern ANS_CJK = Pattern.compile(
            "([a-z0-9`~!\\$%\\^&\\*\\-_\\+=\\|\\\\;:,\\./\\?])" +
                    "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern CJK_QUOTE = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])" +
                    "([\"'])"
    );
    private static final Pattern QUOTE_CJK = Pattern.compile(
            "([\"'])" +
                    "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
    );
    private static final Pattern FIX_QUOTE = Pattern.compile("([\"'])(\\s*)(.+?)(\\s*)([\"'])");

    private static final Pattern CJK_BRACKET_CJK = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])" +
                    "([\\({\\[]+(.*?)[\\)}\\]]+)" +
                    "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
    );
    private static final Pattern CJK_BRACKET     = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])" +
                    "([\\(\\){}\\[\\]<>])"
    );
    private static final Pattern BRACKET_CJK     = Pattern.compile(
            "([\\(\\){}\\[\\]<>])" +
                    "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
    );
    private static final Pattern FIX_BRACKET     = Pattern.compile("([(\\({\\[)]+)(\\s*)(.+?)(\\s*)([\\)}\\]]+)");

    private static final Pattern CJK_HASH = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])" +
                    "(#(\\S+))"
    );
    private static final Pattern HASH_CJK = Pattern.compile(
            "((\\S+)#)" +
                    "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
    );

    /**
     * Performs a paranoid text spacing on {@code text}.
     * @param text the string you want to process, must not be {@code null}.
     * @return a comfortable and readable version of {@code text} for paranoiac.
     */
    public String spacingText(String text) {
        // CJK and quotes
        Matcher cqMatcher = CJK_QUOTE.matcher(text);
        text = cqMatcher.replaceAll("$1 $2");

        Matcher qcMatcher = QUOTE_CJK.matcher(text);
        text = qcMatcher.replaceAll("$1 $2");

//        // 不处理引用等
//        Matcher fixQuoteMatcher = FIX_QUOTE.matcher(text);
//        text = fixQuoteMatcher.replaceAll("$1$3$5");

        // CJK and brackets
        String  oldText    = text;
        Matcher cbcMatcher = CJK_BRACKET_CJK.matcher(text);
        String  newText    = cbcMatcher.replaceAll("$1 $2 $4");
        text = newText;

        if (oldText.equals(newText)) {
            Matcher cbMatcher = CJK_BRACKET.matcher(text);
            text = cbMatcher.replaceAll("$1 $2");

            Matcher bcMatcher = BRACKET_CJK.matcher(text);
            text = bcMatcher.replaceAll("$1 $2");
        }

//        // 不处理括号空格
//        Matcher fixBracketMatcher = FIX_BRACKET.matcher(text);
//        text = fixBracketMatcher.replaceAll("$1$3$5");

        // CJK and hash
        Matcher chMatcher = CJK_HASH.matcher(text);
        text = chMatcher.replaceAll("$1 $2");

        Matcher hcMatcher = HASH_CJK.matcher(text);
        text = hcMatcher.replaceAll("$1 $3");

        // CJK and ANS
        Matcher caMatcher = CJK_ANS.matcher(text);
        text = caMatcher.replaceAll("$1 $2");

        Matcher acMatcher = ANS_CJK.matcher(text);
        text = acMatcher.replaceAll("$1 $2");

        return text;
    }

    /**
     * Performs a paranoid text spacing on {@code inputFile} and generate a new file {@code outputFile}.
     * @param inputFile  an existing file to process, must not be {@code null}.
     * @param outputFile the processed file, must not be {@code null}.
     * @throws IOException if an error occurs.
     * @since 1.1.0
     */
    public void spacingFile(
            File inputFile,
            File outputFile
    )
            throws IOException {
        // TODO: support charset

        FileReader     fr = new FileReader(inputFile);
        BufferedReader br = new BufferedReader(fr);

        outputFile.getParentFile().mkdirs();
        FileWriter     fw = new FileWriter(outputFile, false);
        BufferedWriter bw = new BufferedWriter(fw);

        try {
            String line = br.readLine(); // readLine() do not contain newline char

            while (line != null) {
                line = spacingText(line);

                // TODO: keep file's raw newline char from difference OS platform
                bw.write(line);
                bw.newLine();

                line = br.readLine();
            }
        } finally {
            br.close();

            // 避免 writer 沒有實際操作就 close() 產生一個空檔案
            if (bw != null) {
                bw.close();
            }
        }
    }

}