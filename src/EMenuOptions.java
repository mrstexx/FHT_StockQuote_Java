public enum EMenuOptions {
    ADD (1),
    DEL (2),
    IMPORT(3),
    SEARCH(4),
    PLOT(5),
    SAVE(6),
    LOAD(7),
    QUIT(8);
    private int numValue;

    EMenuOptions(int numValue) {
        this.numValue = numValue;
    }

    public int getNumValue() {
        return this.numValue;
    }
}
