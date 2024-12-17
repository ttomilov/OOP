package org.markdown;

import org.main.Element;

public class Code implements Element {
    private final String content;
    private final String language;

    private Code(Builder builder) {
            this.content = builder.content;
            this.language = builder.language;
    }

    @Override
    public String serialize() {
        StringBuilder serialized = new StringBuilder();
        serialized.append("```");
        if (language != null && !language.isEmpty()) {
            serialized.append(language);
        }
        serialized.append("\n")
                .append(content)
                .append("\n```");
        return serialized.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Code)) return false;
        Code other = (Code) obj;
        return content.equals(other.content) &&
                ((language == null && other.language == null) ||
                        (language != null && language.equals(other.language)));
    }

    public static class Builder {
        private String content;
        private String language;

        public Builder setContent(String content) {
            if (content == null || content.isEmpty()) {
                throw new IllegalArgumentException("Code content cannot be null or empty.");
            }
            this.content = content;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Code build() {
            if (content == null || content.isEmpty()) {
                throw new IllegalStateException("Code content must be set.");
            }
            return new Code(this);
        }
    }
}
