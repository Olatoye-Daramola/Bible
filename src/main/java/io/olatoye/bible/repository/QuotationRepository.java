package io.olatoye.bible.repository;

import io.olatoye.bible.domain.Quotation;
import io.olatoye.bible.domain.Translation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface QuotationRepository extends MongoRepository<Quotation, String> {
    List<Quotation> findByTranslation(Translation translation);
    List<Quotation> findByBook(String bookName);
}
