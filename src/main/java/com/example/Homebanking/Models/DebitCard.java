
package com.example.Homebanking.Models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DEBIT")
public class DebitCard extends Card {
  
    public DebitCard() {}
}
