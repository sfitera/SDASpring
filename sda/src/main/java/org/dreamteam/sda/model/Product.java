package org.dreamteam.sda.model;

import lombok.Builder;

/*TODO:
    Přidejte do projektu rest controller a další potřebné třídy,
    tak aby bylo možné zobrazit, přidat, updatovat a smazat produkty.
    Produkt pro zjednodušení má pouze (id,) název a cenu.
*/
@Builder
public record Product(String id, String name, String price) {
}
