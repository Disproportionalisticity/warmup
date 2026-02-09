package Behavioral;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class DocumentMemento {
    private String content;

    public DocumentMemento(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }
}

class DocumentHistory {
    private List<DocumentMemento> mementos;
    private int internalIndex;

    public DocumentHistory() { 
        this.mementos = new ArrayList<>();
        this.internalIndex = 0;
    }

    public void addMemento(DocumentMemento memento) {
        if (this.internalIndex < this.mementos.size()) {
            this.mementos.subList(this.internalIndex, this.mementos.size()).clear();
        }
        this.mementos.add(memento);
        this.internalIndex += 1;
    }

    public DocumentMemento undo() {
        if (this.internalIndex > 0) {
            this.internalIndex -= 1;
            return this.mementos.get(this.internalIndex);
        } else {
            return new DocumentMemento(""); // This should be treates with a Throw/Catch but I can't be bothered for this example
        }
    }

    public DocumentMemento redo() {
        if (this.internalIndex < this.mementos.size()) {
            this.internalIndex += 1;
            return this.mementos.get(this.internalIndex - 1);
        } else {
            return new DocumentMemento(""); // This should be treates with a Throw/Catch but I can't be bothered for this example
        }
    }
}

class Document {
    private String content;
    private DocumentHistory history;

    public Document() {
        this.content = "";
        this.history = new DocumentHistory();
    }

    public void write(String text) {
        this.content += text;
        this.history.addMemento(new DocumentMemento(text));
    }

    public String getWholeContent() {
        return this.content;
    }

    public void undo() {
        DocumentMemento tempMemento = this.history.undo();
        this.content = this.content.replaceFirst("(?s)(.*)" + Pattern.quote(tempMemento.getContent()), "$1");
    }

    public void redo() {
        DocumentMemento tempMemento = this.history.redo();
        this.content += tempMemento.getContent();
    }
}

class Memento {
    public static void main(String[] args) {
        Document document = new Document();
        System.out.println(document.getWholeContent());
        document.write("Initial content.");
        document.write("Additional content 1.");
        document.write("Additional content 2.");
        System.out.println(document.getWholeContent());
        document.undo();
        System.out.println(document.getWholeContent());
        document.undo();
        System.out.println(document.getWholeContent());
        document.undo();
        System.out.println(document.getWholeContent());
        document.undo();
        System.out.println(document.getWholeContent());
        document.redo();
        System.out.println(document.getWholeContent());
        document.write("Separate content 1.");
        System.out.println(document.getWholeContent());
        document.undo();
        System.out.println(document.getWholeContent());
        document.redo();
        System.out.println(document.getWholeContent());
        
    }
}
