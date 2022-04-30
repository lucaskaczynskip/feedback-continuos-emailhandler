package br.com.vimserdev.feedback.emailconsumer.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ForgotPasswordHandlerDTO {

    private String code;
    private String to;
}
