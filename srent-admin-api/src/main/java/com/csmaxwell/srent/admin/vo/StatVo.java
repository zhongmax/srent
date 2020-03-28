package com.csmaxwell.srent.admin.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by maxwell on 2019/8/23
 */
public class StatVo {
    private String[] columns = new String[0];
    private List<Map> rows = new ArrayList<>();

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public List<Map> getRows() {
        return rows;
    }

    public void setRows(List<Map> rows) {
        this.rows = rows;
    }
}
