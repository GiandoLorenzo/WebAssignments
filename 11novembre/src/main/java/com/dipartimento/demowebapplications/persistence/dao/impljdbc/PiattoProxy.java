package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public class PiattoProxy extends Piatto {


    public List<Piatto> getRistoranti() {
        if(this.ristoranti==null){
            this.restoranti= DBManager.getInstance().getRistoranteDao().findAllByRistoranteName(this.nome);
        }
        return ristoranti;
    }

}
