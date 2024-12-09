package org.main;

import org.markdown.*;

public class Main {
    public static void main(String[] args) {
        Table.Builder tableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .withRowLimit(8)
                .withColumns(2)
                .addRow("Index", "Random");
        for (int i = 1; i <= 5; i++) {
            int randomValue = (int) (Math.random() * 10);
            if (randomValue > 5) {
                Text formattedText = new Text.Builder(String.valueOf(randomValue)).bold().build();
                tableBuilder.addRow(i, formattedText);
            } else {
                tableBuilder.addRow(i, randomValue);
            }
        }
        System.out.println(tableBuilder.build().serialize());
    }
}
