package org.docksidestage.javatry.basic.st6.dbms;

public abstract class SQL {
    public String buildPagingQuery(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);
        return "limit " + offset + ", " + pageSize;
    }
}
