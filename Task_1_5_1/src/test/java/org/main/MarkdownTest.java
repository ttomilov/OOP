package org.main;

import org.junit.jupiter.api.Test;
import org.markdown.*;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownTest {

    @Test
    void headingTest() {
        Heading heading = new Heading.Builder()
                .setLevel(2)
                .setText("Test Heading")
                .build();
        String serialized = heading.serialize();
        assertEquals("## Test Heading", serialized);

        Heading heading1 = new Heading.Builder()
                .setLevel(1)
                .setText("Heading")
                .build();
        Heading heading2 = new Heading.Builder()
                .setLevel(1)
                .setText("Heading")
                .build();
        Heading heading3 = new Heading.Builder()
                .setLevel(2)
                .setText("Different Heading")
                .build();
        assertTrue(heading1.equals(heading2));
        assertFalse(heading1.equals(heading3));
    }

    @Test
    void textTest() {
        Text text = new Text.Builder("Test text")
                .bold()
                .italic()
                .strikethrough()
                .code()
                .build();
        String serialized = text.serialize();
        assertEquals("`~~***Test text***~~`", serialized);

        Text text1 = new Text.Builder("Sample Text")
                .italic()
                .bold()
                .build();
        Text text2 = new Text.Builder("Sample Text")
                .italic()
                .bold()
                .build();
        Text text3 = new Text.Builder("Different Text")
                .bold()
                .italic()
                .build();
        assertTrue(text1.equals(text2));
        assertFalse(text1.equals(text3));
    }

    @Test
    void listElementTest() {
        ListElement list = new ListElement.Builder()
                .addItem("Item 1")
                .addItem("Item 2")
                .addItem("Item 3")
                .build();
        ListElement list1 = new ListElement.Builder()
                .addItem("Item 1")
                .addItem("Item 2")
                .addItem("Item 3")
                .build();
        String serialized = list.serialize();
        String serialized1 = list1.serialize();
        assertEquals(serialized1, serialized);
        ListElement list2 = new ListElement.Builder()
                .addItem("Item")
                .build();
        assertFalse(list2.equals(list));
        assertTrue(list.equals(list1));
    }

    @Test
    void tableTest() {
        Table tableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER, Table.ALIGN_RIGHT)
                .withRowLimit(8)
                .withColumns(3)
                .addRow("Header 1", "Header 2", "Header 3")
                .addRow("Cell 1", "Cell 2", "Cell 3").build();
        String serialized = tableBuilder.serialize();
        Table tableBuilder1 = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER, Table.ALIGN_RIGHT)
                .withRowLimit(8)
                .withColumns(3)
                .addRow("Header 1", "Header 2", "Header 3")
                .addRow("Cell 1", "Cell 2", "Cell 3").build();
        String serialized1 = tableBuilder.serialize();
        assertEquals(serialized1, serialized);
        Table tableBuilder2 = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .withRowLimit(8)
                .withColumns(2)
                .addRow("Header 1", "Header 2")
                .addRow("Cell 1", "Cell 2").build();
        assertTrue(tableBuilder.equals(tableBuilder1));
        assertFalse(tableBuilder.equals(tableBuilder2));
    }

    @Test
    void linkTest() {
        Link link = new Link.Builder()
                .setText("Example Link")
                .setUrl("https://example.com")
                .build();
        String serialized = link.serialize();
        Link link1 = new Link.Builder()
                .setText("Example Link")
                .setUrl("https://example.com")
                .build();
        String serialized1 = link.serialize();
        assertEquals(serialized1, serialized);
        Link link2 = new Link.Builder()
                .setText("Link")
                .setUrl("https://example.com")
                .build();
        assertTrue(link.equals(link1));
        assertFalse(link.equals(link2));
    }

    @Test
    void imageTest() {
        Image image = new Image.Builder()
                .setAltText("Image Alt Text")
                .setUrl("https://example.com/image.png")
                .setTitle("Image Title")
                .build();
        String serialized = image.serialize();
        Image image1 = new Image.Builder()
                .setAltText("Image Alt Text")
                .setUrl("https://example.com/image.png")
                .setTitle("Image Title")
                .build();
        String serialized1 = image.serialize();
        assertEquals(serialized1, serialized);
        Image image2 = new Image.Builder()
                .setAltText("Image Text")
                .setUrl("https://example.com/image.png")
                .setTitle("Image Title")
                .build();
        assertTrue(image.equals(image1));
        assertFalse(image.equals(image2));
    }

    @Test
    void quoteTest() {
        Quote quote = new Quote.Builder()
                .addLine("This is a quoted line.")
                .build();
        String serialized = quote.serialize();
        Quote quote1 = new Quote.Builder()
                .addLine("This is a quoted line.")
                .build();
        Quote quote2 = new Quote.Builder()
                .addLine("Line")
                .build();
        String serialized1 = quote1.serialize();
        assertEquals(serialized1, serialized);
        assertTrue(quote.equals(quote1));
        assertFalse(quote.equals(quote2));
    }

    @Test
    void taskTest() {
        Task task = new Task.Builder()
                .setDescription("Complete the task")
                .setCompleted(false)
                .build();
        String serialized = task.serialize();
        Task task1 = new Task.Builder()
                .setDescription("Complete the task")
                .setCompleted(false)
                .build();
        String serialized1 = task.serialize();
        assertEquals(serialized1, serialized);
        Task task2 = new Task.Builder()
                .setDescription("Complete the task")
                .setCompleted(true)
                .build();
        assertTrue(task.equals(task1));
        assertFalse(task.equals(task2));
    }

    @Test
    void codeTest() {
        Code code = new Code.Builder()
                .setContent("System.out.println(\"Hello World\");")
                .setLanguage("java")
                .build();
        Code code1 = new Code.Builder()
                .setContent("System.out.println(\"Hello World\");")
                .setLanguage("java")
                .build();
        String serialized = code.serialize();
        String serialized1 = code1.serialize();
        assertEquals(serialized1, serialized);
        Code code2 = new Code.Builder()
                .setLanguage("C")
                .setContent("printf(\"Hello World\");")
                .build();
        assertTrue(code.equals(code1));
        assertFalse(code.equals(code2));
    }
}
