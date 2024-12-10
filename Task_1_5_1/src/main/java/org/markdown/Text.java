package org.markdown;

import org.main.Element;

public class Text implements Element {
    private final String text;

    private Text(Builder builder) {
        this.text = builder.Builder.toString();
    }

    @Override
    public String serialize() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Text)) {
            return false;
        }
        return text.equals(((Text) obj).text);
    }

    public static class Builder {
        private final StringBuilder Builder;

        public Builder(String text) {
            this.Builder = new StringBuilder(text);
        }

        public Builder setText(String text) {
            this.Builder.setLength(0);
            this.Builder.append(text);
            return this;
        }

        public Builder bold() {
            wrap("**");
            return this;
        }

        public Builder italic() {
            wrap("*");
            return this;
        }

        public Builder strikethrough() {
            wrap("~~");
            return this;
        }

        public Builder inlineCode() {
            wrap("`");
            return this;
        }

        private void wrap(String wrapper) {
            Builder.insert(0, wrapper);
            Builder.append(wrapper);
        }

        public Builder append(String text) {
            this.Builder.append(text);
            return this;
        }

        public Text build() {
            if (Builder.length() == 0) {
                throw new IllegalStateException("Text cannot be empty");
            }
            return new Text(this);
        }
    }
}
