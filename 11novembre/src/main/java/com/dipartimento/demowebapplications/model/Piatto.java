package com.dipartimento.demowebapplications.model;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Piatto {

    private String nome;
    private String ingredienti;
    protected List<Ristorante> ristoranti;

    public List<Ristorante> getRistoranti() {
        return ristoranti;
    }

}
