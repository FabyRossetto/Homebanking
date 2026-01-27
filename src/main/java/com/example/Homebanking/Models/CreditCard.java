/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CREDIT")
public class CreditCard extends Card {
    
    private Double maxLimit;

    public CreditCard() {}

    public void setMaxLimit(Double maxLimit) {
        this.maxLimit = maxLimit;
    }
    public Double getMaxLimit() {
        return maxLimit;
    }
    
    
    
}