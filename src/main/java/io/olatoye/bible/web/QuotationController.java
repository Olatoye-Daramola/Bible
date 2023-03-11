package io.olatoye.bible.web;

import io.olatoye.bible.domain.ResponseBody;
import io.olatoye.bible.service.RandomQuotationGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/bible_quotation")
public class QuotationController {

    private final RandomQuotationGenerator quotationGen;

    public QuotationController(@Qualifier("initialLogic") RandomQuotationGenerator quotationGen) {
        this.quotationGen = quotationGen;
    }

    @GetMapping("/{version}")
    public ResponseEntity<ResponseBody> generateQuotation(@PathVariable("version") String version) {
        ResponseBody response = quotationGen.generateRandomQuotation(version.toUpperCase());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
