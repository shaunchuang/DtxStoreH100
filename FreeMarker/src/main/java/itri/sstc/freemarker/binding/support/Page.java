package itri.sstc.freemarker.binding.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable { 
    private static final long serialVersionUID = 6196597978015166597L;

    protected int pageNo = 1;
    protected int pageSize = 10;
    protected boolean autoCount = true;

    protected List<T> result = new ArrayList<T>(); 
    protected List<T> result2 = new ArrayList<T>(); 
    protected List<T> result3 = new ArrayList<T>();
    
    protected long totalCount = -1L;
    protected boolean sortEnable = true;
    protected Sort sort;
    private Class<T> genericType; // 用于存储泛型类型 

    public Page(Class<T> genericType) {
        this.genericType = genericType;
    }
    
    public Page(Class<T> genericType, int pageSize) {
        this.genericType = genericType;
        this.pageSize = pageSize;
    } 

    public Class<T> getGenericType() {
        return genericType;
    } 

    public void setGenericType(Class<T> genericType) {
        this.genericType = genericType;
    }

    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;

        if (pageNo < 1)
            this.pageNo = 1;
    }

    public Page<T> pageNo(int thePageNo) {
        setPageNo(thePageNo);
        return this;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Page<T> pageSize(int thePageSize) {
        setPageSize(thePageSize);
        return this;
    } 

    public int getFirst() {
        return (this.pageNo - 1) * this.pageSize + 1;
    }

    public boolean isAutoCount() {
        return this.autoCount;
    }

    public void setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
    }

    public Page<T> autoCount(boolean theAutoCount) {
        setAutoCount(theAutoCount);
        return this;
    }

    public List<T> getResult() {
        return this.result;
    }

    public Page<T> setResult(List<T> result) {
        this.result = result;
        return this;
    }
    
    public List<T> getResult2() {
        return this.result2;
    }

    public Page<T> setResult2(List<T> result2) {
        this.result2 = result2;
        return this;
    }
    
    public List<T> getResult3() {
        return this.result3;
    }

    public Page<T> setResult3(List<T> result3) {
        this.result3 = result3;
        return this;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getTotalPages() {
        if (this.totalCount < 0L) {
            return -1L;
        }

        long count = this.totalCount / this.pageSize;
        if (this.totalCount % this.pageSize > 0L) {
            count += 1L;
        }
        return count;
    }

    public void setTotalPages(long totalPages) {

    } 

    public boolean isHasNext() {
        return this.pageNo + 1 <= getTotalPages();
    }

    public int getNextPage() {
        if (isHasNext()) {
            return this.pageNo + 1;
        }
        return this.pageNo;
    }

    public boolean isHasPre() {
        return this.pageNo - 1 >= 1;
    }

    public int getPrePage() {
        if (isHasPre()) {
            return this.pageNo - 1;
        }
        return this.pageNo;
    }

    public boolean isSortEnable() {
        return sortEnable;
    }
    
    public boolean getSortEnable() {
        return sortEnable;
    }

    public void setSortEnable(boolean sortEnable) {
        this.sortEnable = sortEnable;
    }

    public Sort getSort() {
        return this.sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public String toString() { 
        return "Page[totalCount=" + getTotalCount() + ", totalPages=" + getTotalPages() + ", pageNo=" + getPageNo() + ", pageSize=" + getPageSize() + "]";
    }

}