package org.markdown;

import org.main.Element;

public class  Image implements Element{
    private String altText;
    private String url;
    private String title;

    private Image(Builder builder) {
        this.altText = builder.altText;
        this.url = builder.url;
        this.title = builder.title;
    }

    @Override
    public String serialize() {
        StringBuilder serialized = new StringBuilder();
        serialized.append("!["+ altText + "](" + url);
        if (title != null && !title.isEmpty()) {
            serialized.append(" \"").append(title).append("\"");
        }
        serialized.append(")");
        return serialized.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Image)) return false;
        Image other = (Image) obj;
        return altText.equals(other.altText) &&
                url.equals(other.url) &&
                ((title == null && other.title == null) ||
                        (title != null && title.equals(other.title)));
    }


    public static class Builder {
        private String altText;
        private String url;
        private String title;

        public Builder setAltText(String altText) {
            this.altText = altText;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Image build() {
            if (url == null || url.isEmpty()) {
                throw new IllegalStateException("Image URL cannot be null or empty.");
            }
            return new Image(this);
        }
    }
}
