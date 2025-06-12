package org.markdown;

import org.main.Element;

public class Task implements Element {
    private final String description;
    private final boolean isCompleted;

    // Приватный конструктор, используется билдер
    private Task(Builder builder) {
        this.description = builder.description;
        this.isCompleted = builder.isCompleted;
    }

    @Override
    public String serialize() {
        return (isCompleted ? "- [x] " : "- [ ] ") + description;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Task)) return false;
        Task other = (Task) obj;
        return description.equals(other.description) && isCompleted == other.isCompleted;
    }

    public static class Builder {
        private String description;
        private boolean isCompleted;

        public Builder setDescription(String description) {
            if (description == null || description.isEmpty()) {
                throw new IllegalArgumentException("Task description cannot be null or empty.");
            }
            this.description = description;
            return this;
        }

        public Builder setCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
            return this;
        }

        public Task build() {
            if (description == null || description.isEmpty()) {
                throw new IllegalStateException("Task description must be set.");
            }
            return new Task(this);
        }
    }
}
