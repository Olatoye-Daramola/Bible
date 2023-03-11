package io.olatoye.bible.service;

import io.olatoye.bible.domain.Quotation;
import io.olatoye.bible.domain.ResponseBody;
import io.olatoye.bible.domain.Translation;
import io.olatoye.bible.repository.QuotationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service("initialLogic")
@RequiredArgsConstructor
@Slf4j
public class RandomQuotationGeneratorImpl implements RandomQuotationGenerator {

    private final QuotationRepository quotationRepository;

    private final SecureRandom randomGenerator = new SecureRandom();

    @Override
    public ResponseBody generateRandomQuotation(String translation) {
        try {
            if (translation.equals("KJV")
                    || translation.equals("ASV")
                    || translation.equals("BBE")
            ) {
                Translation foundTranslation = Translation.valueOf(translation);
                List<Quotation> trans = quotationRepository.findAll()
                        .stream()
                        .filter(quote -> quote.getTranslation().equals(foundTranslation))
                        .toList();

                Quotation generatedQuotation = generateQuotation(trans);

                if (generatedQuotation == null)
                    return new ResponseBody(500, "Unable to generate Quotation at this time", null);

                return new ResponseBody(200, "Successfully generated Quotation", generatedQuotation);
            }
            return new ResponseBody(400, "Invalid translation selected", null);
        } catch (Exception e) {
            return new ResponseBody(500, "Error generating random Quotation", e.getMessage());
        }
    }


    //-------------------------------------------- HELPER METHOD --------------------------------------------
    private Quotation generateQuotation(List<Quotation> foundTranslation) {
        String bookName = generateBook();

        List<Quotation> queriedByBook = foundTranslation.stream()
                .filter(quote -> quote.getBook().equals(bookName))
                .toList();

        List<Integer> chapters = queriedByBook.stream()
                .map(Quotation::getChapter)
                .distinct()
                .toList();
        Integer chapterNumber = generateNumberWithLimit(chapters.size());

        List<Integer> verses = queriedByBook.stream()
                .filter(quote -> quote.getChapter().equals(chapterNumber))
                .map(Quotation::getVerse)
                .distinct()
                .toList();
        Integer verseNumber = generateNumberWithLimit(verses.size());

        return foundTranslation
                .stream()
                .filter(quote -> quote.getBook().equals(bookName)
                        && quote.getChapter().equals(chapterNumber)
                        && quote.getVerse().equals(verseNumber))
                .findFirst()
                .orElse(null);
    }

    private String generateBook() {
        final int numberOfBooks = 66;
        int bookNumber = randomGenerator.nextInt(numberOfBooks);
        return getBookName(bookNumber);
    }

    private Integer generateNumberWithLimit(Integer limit) {
        return randomGenerator.nextInt(1, limit + 1);
    }

    private String getBookName(int bookNumber) {
        List<String> bookNames = new ArrayList<>(List.of(
                "Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", " Joshua", "Judges", "Ruth", "1 Samuel",
                "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job",
                "Psalms", "Proverbs", "Ecclesiastes", "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations",
                "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk",
                "Zephaniah", "Haggai", "Zechariah", "Malachi",

                "Matthew", "Mark", "Luke", "John", "Acts", "Romans", "1 Corinthians", "2 Corinthians", "Galatians",
                "Ephesians", "Philippians", "Colossians", "1 Thessalonians", "2 Thessalonians", "1 Timothy",
                "2 Timothy", "Titus", "Philemon", "Hebrews", "James", "1 Peter", "2 Peter", "1 John", "2 John", "3 John",
                "Jude", "Revelation"
        ));
        return bookNames.get(bookNumber);
    }

//    @PostConstruct
//    public void seedDb() {
//        String filename = "src/main/resources/csv_files/t_bbe.csv";
//        String translation = "BBE";
//        try {
//            CSVReader reader = new CSVReaderBuilder(new FileReader(filename))
//                    .withSkipLines(1)
//                    .build();
//
//            reader.readAll().stream()
//                    .map(data -> {
//                        String bookName = getBookName(Integer.parseInt(data[1]));
//
//                        return Quotation.builder()
//                                .translation(Translation.valueOf(translation))
//                                .verseId(String.valueOf(data[0]))
//                                .book(bookName)
//                                .chapter(Integer.parseInt(data[2]))
//                                .verse(Integer.parseInt(data[3]))
//                                .text(String.valueOf(data[4]))
//                                .build();
//                    })
//                    .forEach(quotationRepository::save);
//            log.info("\nDONE\n");
//        } catch (Exception e) {
//            log.info("\nERROR OCCURRED -> {}\n", e.getMessage());
//        }
//    }
}
