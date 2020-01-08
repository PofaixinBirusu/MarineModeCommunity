package xurong.marinemode.community.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginationDTO {
    private List<QuestionDTO> questionList;
    /* 是否展示上一页、第一页、下一页、最后一页 */
    private boolean showPrevious=false;
    private boolean showFirstPage=false;
    private boolean showNextPage=false;
    private boolean showEndPage=false;
    /* 当前的页码 */
    private int currentPage;
    /* 展示在前端的页码 */
    private List<Integer> pageList=new ArrayList<>();
    /* 总也数 */
    private int totlePageNumber;

    public PaginationDTO(int currentPage, int size, int totleQuestionNumber) {
        this.currentPage = currentPage;
        totlePageNumber = (totleQuestionNumber / size) + ((totleQuestionNumber % size) == 0 ? 0 : 1);
        this.currentPage = Math.max(this.currentPage, 1);
        this.currentPage = Math.min(this.currentPage, totlePageNumber);
        if (this.currentPage != 1) {
            this.showPrevious = true;
        }
        if (this.currentPage != totlePageNumber) {
            this.showNextPage = true;
        }
        /* 当前页码      展示按钮         */
        /*    1         1 2 3 4         */
        /*    2         1 2 3 4 5       */
        /*    3         1 2 3 4 5 6     */
        /*    4         1 2 3 4 5 6 7   */
        /*    5         2 3 4 5 6 7 8   */
        int startPage = Math.max(this.currentPage-3, 1), endPage = Math.min(this.currentPage+3, totlePageNumber);
        for (int i = startPage; i <= endPage; i++)
            pageList.add(i);
        /* 如果包含第一页, 就不展示第一页按钮, 不包含第一页, 就展示第一页按钮 */
        if (!pageList.contains(1)) {
            this.showFirstPage = true;
        }
        /* 如果包含最后一页, 就不展示最后一页按钮, 不包含最后一页, 就展示最后一页按钮 */
        if (!pageList.contains(totlePageNumber)) {
            this.showEndPage = true;
        }
    }

    public int getTotlePageNumber() {
        return totlePageNumber;
    }

    public void setTotlePageNumber(int totlePageNumber) {
        this.totlePageNumber = totlePageNumber;
    }

    public List<QuestionDTO> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionDTO> questionList) {
        this.questionList = questionList;
    }

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowNextPage() {
        return showNextPage;
    }

    public void setShowNextPage(boolean showNextPage) {
        this.showNextPage = showNextPage;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Integer> getPageList() {
        return pageList;
    }

    public void setPageList(List<Integer> pageList) {
        this.pageList = pageList;
    }
}
