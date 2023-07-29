package kr.eddi.demo.domain.account.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckEmailDuplicateRequestForm {
    private String email;
}
