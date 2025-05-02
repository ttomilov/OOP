package org.markdown;

import org.main.Element;

public class Heading implements Element {
    private final int level;
    private final String text;

    private Heading(Builder builder) {
        if (builder.level < 1 || builder.level > 6) {
            throw new IllegalArgumentException("Heading level must be between 1 and 6.");
        }
        this.level = builder.level;
        this.text = builder.text;
    }

    @Override
    public String serialize() {
        return "#".repeat(level) + " " + text;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Heading)) return false;
        Heading other = (Heading) obj;
        return level == other.level && text.equals(other.text);
    }

    public static class Builder {
        private int level = 1;
        private String text = "";

        public Builder setLevel(int level) {
            this.level = level;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Heading build() {
            if (text == null || text.isEmpty()) {
                throw new IllegalStateException("Heading text cannot be null or empty.");
            }
            return new Heading(this);
        }
    }
}
