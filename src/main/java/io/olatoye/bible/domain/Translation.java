package io.olatoye.bible.domain;

public enum Translation {
    KJV("KJV"),
    ASV("ASV"),
    BBE("BBE");

    private final String translation;

    Translation(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
