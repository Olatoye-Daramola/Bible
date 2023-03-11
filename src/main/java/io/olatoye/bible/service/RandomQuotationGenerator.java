package io.olatoye.bible.service;

import io.olatoye.bible.domain.ResponseBody;

public interface RandomQuotationGenerator {

    ResponseBody generateRandomQuotation(String translation);
}
