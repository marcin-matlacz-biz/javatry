package org.docksidestage.javatry.basic.st6.os;

public class MacOS extends St6OperationSystem {
    private static final String osType = "Mac";
    private final String loginId;

    public MacOS(String loginId) {
        this.loginId = loginId;
    }

    @Override
    protected String getFileSeparator() {
        return "/";
    }

    @Override
    protected String getUserDirectory() {
        return "/Users/" + loginId;
    }
}
