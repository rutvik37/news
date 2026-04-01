package com.rutvik.app;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SearchPage extends BasePage {
    private static final List<String> CITIES_AND_TOPICS = List.of(
        "Valsad", "Surat", "Chota Udaipur", "Patan", "Vadodara", "Ahmedabad",
        "Gandhinagar", "Bhavnagar", "Porbandar", "Junagadh", "Banaskatha",
        "Kheda", "Navsari", "Gir Somnath", "Amreli", "Anad",
        "Jamangar", "Kutch", "Panch Mahal", "Morbi", "Mahesana",
        "Sabarkatha", "Dwarka", "Surendarnagar", "Tapi", "Rajkot",
        "Dahod", "Arvali", "Botad", "Dang"
    );

    public SearchPage(Page page) {
        super(page);
    }

    public void clickSearchInput() {
        byRole(AriaRole.IMG, "search-input").click();
    }

    public String getRandomCity() {
        return CITIES_AND_TOPICS.get(ThreadLocalRandom.current().nextInt(CITIES_AND_TOPICS.size()));
    }

    public String clickRandomCity() {
        String selectedCity = getRandomCity();
        clickAndWait(byRole(AriaRole.LINK, selectedCity).first());
        return selectedCity;
    }

    public String clickOneRandomCity() {
        clickSearchInput();
        String city = clickRandomCity();
        return city;
    }
}
