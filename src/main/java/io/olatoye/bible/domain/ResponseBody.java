package io.olatoye.bible.domain;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@RequiredArgsConstructor
@AllArgsConstructor

public class ResponseBody {

    private Integer statusCode;
    private String message;
    private Object data;
}
