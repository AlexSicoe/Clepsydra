package ro.alexsicoe.clepsydra.model;

public enum RequestType {
    ADD(1),
    EDIT(2);

    private int mCode;

    RequestType(int code) {
        mCode = code;
    }

    public int getCode() {
        return mCode;
    }
}
