package com.doel.joybngla.pojoclass;

public class IntroContent {
    String intro_title,intro_details;

    public IntroContent(String intro_title, String intro_details) {
        this.intro_title = intro_title;
        this.intro_details = intro_details;
    }

    public String getIntro_title() {
        return intro_title;
    }

    public void setIntro_title(String intro_title) {
        this.intro_title = intro_title;
    }

    public String getIntro_details() {
        return intro_details;
    }

    public void setIntro_details(String intro_details) {
        this.intro_details = intro_details;
    }
}
