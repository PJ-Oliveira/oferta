package com.pj.offer.domain.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OfferForm implements Serializable{


    private Long id;

    private List<ProductForm> products;

    @NotNull(message = "A data de início da oferta é necessária")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "GMT-03")
    @FutureOrPresent
    private LocalDate inicio;
    @NotNull(message = "A data de fim da oferta é necessária")
    @Future
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "GMT-03")
    private LocalDate fim;
    @NotBlank(message = "A descrição da oferta é necessária")
    private String descricao;
    @Column(precision = 5, scale = 4)
    @NotNull(message = "Informe um valor para o campo desconto")
    @Range(min=1, max=50, message = "O desconto deve ser de no mínimo 1 e no máximo 50")
    @Positive
    private BigDecimal desconto;
    private Boolean active;

}
