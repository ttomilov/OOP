package org.markdown;

import org.main.Element;
import java.util.ArrayList;
import java.util.List;

public class ListElement implements Element {
    private final List<String> items;
    private final boolean ordered;

    private ListElement(Builder builder) {
        this.items = builder.items;
        this.ordered = builder.ordered;
    }

    @Override
    public String serialize() {
        StringBuilder serialized = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (ordered) {
                serialized.append((i + 1)).append(". ").append(items.get(i));
            } else {
                serialized.append("- ").append(items.get(i));
            }
            if (i < items.size() - 1) {
                serialized.append("\n");
            }
        }
        return serialized.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ListElement)) {
            return false;
        }
        ListElement other = (ListElement) obj;
        return items.equals(other.items) && ordered == other.ordered;
    }

    public static class Builder {
        private final List<String> items = new ArrayList<>();
        private boolean ordered;

        public Builder setOrdered(boolean ordered) {
            this.ordered = ordered;
            return this;
        }

        public Builder addItem(String item) {
            this.items.add(item);
            return this;
        }

        public ListElement build() {
            if (items.isEmpty()) {
                throw new IllegalStateException("List must contain at least one item.");
            }
            return new ListElement(this);
        }
    }
}
