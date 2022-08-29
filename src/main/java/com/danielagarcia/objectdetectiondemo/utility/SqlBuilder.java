package com.danielagarcia.objectdetectiondemo.utility;

import java.util.Collections;
import java.util.List;

public class SqlBuilder {

    public static String buildInClause(String sql, List values) {
        String inSql = String.join("," , Collections.nCopies(values.size(), "?"));
        return String.format(sql, inSql);
    }
}
