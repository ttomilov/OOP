package org.markdown;

import org.main.Element;

import java.util.ArrayList;
import java.util.Arrays;

public class Table implements Element {
    public static final int ALIGN_RIGHT = 0;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_CENTER = 2;

    private ArrayList<ArrayList<Text>> contents;
    private final ArrayList<Integer> alignments;
    private final int numColumns;
    private final int numRows;

    public Table(Builder builder) {
        this.numColumns = builder.numColumns;
        this.numRows = builder.numRows;
        this.contents = builder.contents;
        this.alignments = builder.alignments;
    }

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numColumns; i++) {
            builder.append("| ").append(contents.get(0).get(i).toString()).append(" ");
        }
        builder.append("|\n");
        builder.append("|");
        for (int i = 0; i < numColumns; i++) {
            String header = contents.get(0).get(i).toString();
            int headerLength = header.length();
            if (alignments.get(i) == ALIGN_LEFT) {
                builder.append(":").append("-".repeat(headerLength)).append(" |");
            } else if (alignments.get(i) == ALIGN_RIGHT) {
                builder.append(" " + "-".repeat(headerLength)).append(":|");
            } else if (alignments.get(i) == ALIGN_CENTER) {
                builder.append(":").append("-".repeat(headerLength)).append(":|");
            }
        }
        builder.append("\n");
        for (int rowIndex = 1; rowIndex < contents.size(); rowIndex++) {
            builder.append("| ");
            for (int colIndex = 0; colIndex < numColumns; colIndex++) {
                String text = contents.get(rowIndex).get(colIndex).serialize();
                String header = contents.get(0).get(colIndex).toString();
                int headerLength = header.length();
                int textLength = text.length();

                if (alignments.get(colIndex) == ALIGN_LEFT) {
                    builder.append(text).append(" ".repeat(headerLength - textLength)).append(" |");
                } else if (alignments.get(colIndex) == ALIGN_RIGHT) {
                    builder.append(" ".repeat(headerLength - textLength + 1)).append(text).append(" |");
                } else if (alignments.get(colIndex) == ALIGN_CENTER) {
                    int totalPadding = headerLength - textLength;
                    int leftPadding = totalPadding / 2;
                    int rightPadding = totalPadding - leftPadding;

                    builder.append(" ".repeat(leftPadding + 1)).append(text).append(" ".repeat(rightPadding - 1)).append("  |");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Table table = (Table) obj;

        return numColumns == table.numColumns &&
                numRows == table.numRows &&
                alignments.equals(table.alignments) &&
                contents.equals(table.contents);
    }

    public static class Builder {
        private ArrayList<ArrayList<Text>> contents = new ArrayList<>();
        private ArrayList<Integer> alignments;
        private int numColumns;
        private int numRows;
        private int rowLimit = Integer.MAX_VALUE;

        public Builder withAlignments(Integer... alignments) {
            this.alignments = new ArrayList<>(Arrays.asList(alignments));
            return this;
        }

        public Builder addRow(Object... cells) {
            if (contents.size() >= rowLimit) {
                throw new IllegalStateException("Row limit exceeded.");
            }
            if (cells.length != numColumns) {
                throw new IllegalArgumentException("Number of cells in row must match the number of columns.");
            }

            ArrayList<Text> row = new ArrayList<>();
            for (Object cell : cells) {
                if (cell instanceof Text) {
                    row.add((Text) cell);
                } else {
                    row.add(new Text.Builder(cell.toString()).build());
                }
            }
            contents.add(row);

            return this;
        }


        public Builder withColumns(int numColumns) {
            this.numColumns = numColumns;
            return this;
        }

        public Builder withRowLimit(int rowLimit) {
            this.rowLimit = rowLimit;
            return this;
        }

        public Table build() {
            if (numColumns <= 0) {
                throw new IllegalStateException("Number of columns must be greater than zero.");
            }
            if (alignments == null || alignments.size() != numColumns) {
                throw new IllegalStateException("Alignments must be specified for each column.");
            }
            return new Table(this);
        }
    }
}
