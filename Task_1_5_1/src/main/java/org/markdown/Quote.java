package org.markdown;

import org.main.Element;

public class Quote implements Element {
    private final String text;

    private Quote(Builder builder) {
        this.text = builder.textBuilder.toString().trim();
    }

    @Override
    public String serialize() {
        return "> " + text.replace("\n", "\n> ");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Quote)) return false;
        Quote other = (Quote) obj;
        return text.equals(other.text);
    }

    public static class Builder {
        private final StringBuilder textBuilder = new StringBuilder();

        public Builder addLine(String line) {
            if (line != null && !line.isEmpty()) {
                if (textBuilder.length() > 0) {
                    textBuilder.append("\n");
                }
                textBuilder.append(line);
            }
            return this;
        }
        
        public Quote build() {
            if (textBuilder.length() == 0) {
                throw new IllegalStateException("Block quote cannot be empty.");
            }
            return new Quote(this);
        }
    }
}
