package org.main;

import org.junit.jupiter.api.Test;
import org.markdown.*;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownTest {

    @Test
    void testHeadingSerialization() {
        Heading heading = new Heading.Builder()
                .setLevel(2)
                .setText("Test Heading")
                .build();
        String serialized = heading.serialize();
        assertEquals("## Test Heading", serialized);
    }

    @Test
    void testTextSerialization() {
        Text text = new Text.Builder("Test text")
                .bold()
                .italic()
                .build();
        String serialized = text.serialize();
        assertEquals("***Test text***", serialized);
    }

    @Test
    void testListElementSerialization() {
        ListElement list = new ListElement.Builder()
                .addItem("Item 1")
                .addItem("Item 2")
                .addItem("Item 3")
                .build();
        String serialized = list.serialize();
        String expected = "- Item 1\n- Item 2\n- Item 3";
        assertEquals(expected, serialized);
    }

    @Test
    void testTableSerializationWithAlignments() {
        Table tableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER, Table.ALIGN_RIGHT)
                .withRowLimit(8)
                .withColumns(3)
                .addRow("Header 1", "Header 2", "Header 3")
                .addRow("Cell 1", "Cell 2", "Cell 3").build();
        String serialized = tableBuilder.serialize();
        String expected = "| Header 1 | Header 2 | Header 3 |\n|:-------- |:--------:| --------:|\n| Cell 1   |  Cell 2  |   Cell 3 |\n";
        assertEquals(expected, serialized);
    }

    @Test
    void testLinkSerialization() {
        Link link = new Link.Builder()
                .setText("Example Link")
                .setUrl("https://example.com")
                .build();
        String serialized = link.serialize();
        assertEquals("[Example Link](https://example.com)", serialized);
    }

    @Test
    void testImageSerialization() {
        Image image = new Image.Builder()
                .setAltText("Image Alt Text")
                .setUrl("https://example.com/image.png")
                .setTitle("Image Title")
                .build();
        String serialized = image.serialize();
        assertEquals("![Image Alt Text](https://example.com/image.png \"Image Title\")", serialized);
    }

    @Test
    void testQuoteSerialization() {
        Quote quote = new Quote.Builder()
                .addLine("This is a quoted line.")
                .build();
        String serialized = quote.serialize();
        assertEquals("> This is a quoted line.", serialized);
    }

    @Test
    void testTaskSerialization() {
        Task task = new Task.Builder()
                .setDescription("Complete the task")
                .setCompleted(false)
                .build();
        String serialized = task.serialize();
        assertEquals("- [ ] Complete the task", serialized);
    }

    @Test
    void testCodeSerialization() {
        Code code = new Code.Builder()
                .setContent("System.out.println(\"Hello World\");")
                .setLanguage("java")
                .build();
        String serialized = code.serialize();
        assertEquals("```java\nSystem.out.println(\"Hello World\");\n```", serialized);
    }

    @Test
    void testEquals() {
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
    void testTextEquals() {
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
}
