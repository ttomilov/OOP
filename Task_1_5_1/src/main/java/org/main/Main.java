package org.main;

import org.markdown.*;

public class Main {
    public static void main(String[] args) {
        Table tableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER, Table.ALIGN_RIGHT)
                .withRowLimit(8)
                .withColumns(4)
                .addRow("Header 1", "Header 2", "Header 3", "Header 4")
                .addRow("Cell 1", "Cell 2", "Cell 3").build();
        System.out.println(tableBuilder.serialize());
    }
}
