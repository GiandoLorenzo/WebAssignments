package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;

import java.util.List;


public class PiattoProxy extends Piatto {


    public PiattoProxy(String nome, String ingredienti) {
    }

    public PiattoProxy() {

    }

    public List<Ristorante> getRistoranti() {
        if(this.ristoranti==null){
            this.ristoranti= DBManager.getInstance().getRistoranteDao().findAllByPiattoName(this.getNome());
        }
        return ristoranti;
    }

}
