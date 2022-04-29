package br.com.vimserdev.feedback.emailconsumer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailHandlerDTO {

    private String to;
    private String from;
}
