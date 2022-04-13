package com.epam.esm.errors;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class AnswerMessageJson {
    String status;
    String message;
    String code;
}
