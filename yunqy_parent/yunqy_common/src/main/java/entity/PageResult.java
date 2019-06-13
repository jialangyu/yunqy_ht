package entity;

import java.util.List;

/**
 * 分页的返回结果数
 */
public class PageResult<T> {

    private long total; //总记录数
    private List<T> rows; //结果

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    //空构造函数
    public PageResult() {
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
