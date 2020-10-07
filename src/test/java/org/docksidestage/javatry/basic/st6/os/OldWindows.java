package org.docksidestage.javatry.basic.st6.os;

public class OldWindows extends St6OperationSystem {
    private static final String osType = "OldWindows";
    private final String loginId;

    public OldWindows(String loginId) {
        this.loginId = loginId;
    }

    @Override
    protected String getFileSeparator() {
        return "\\";
    }

    @Override
    protected String getUserDirectory() {
        return "/Documents and Settigs/" + loginId;
    }
}
