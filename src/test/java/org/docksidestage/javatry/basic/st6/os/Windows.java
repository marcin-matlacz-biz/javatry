package org.docksidestage.javatry.basic.st6.os;

public class Windows extends St6OperationSystem {
    private static final String osType = "Windows";
    private final String loginId;

    public Windows(String loginId) {
        this.loginId = loginId;
    }

    @Override
    protected String getFileSeparator() {
        return "\\";
    }

    @Override
    protected String getUserDirectory() {
        return "/Users/" + loginId;
    }
}
