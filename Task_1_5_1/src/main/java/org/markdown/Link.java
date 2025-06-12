package org.markdown;

import org.main.Element;

public class Link implements Element {
    private final String text;
    private final String url;

    private Link(Builder builder) {
        this.text = builder.text;
        this.url = builder.url;
    }

    @Override
    public String serialize() {
        return "[" + text + "](" + url + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Link)) {
            return false;
        }
        Link other = (Link) obj;
        return text.equals(other.text) && url.equals(other.url);
    }

    public static class Builder {
        private String text;
        private String url;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Link build() {
            if (text == null || text.isEmpty()) {
                throw new IllegalStateException("Link text cannot be null or empty.");
            }
            if (url == null || url.isEmpty()) {
                throw new IllegalStateException("Link URL cannot be null or empty.");
            }
            return new Link(this);
        }
    }
}
