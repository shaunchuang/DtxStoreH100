/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dto;

import demo.freemarker.model.DtxTag;
import demo.freemarker.model.LessonMainInfo;
import java.util.List;

/**
 *
 * @author zhush
 */
public class LessonMainDTO {

    private LessonMainInfo lesson;
    // 加入收藏
    private boolean isFavor;
    private List<DtxTag> tags;
    // 平均評分
    private Double avgRating;
    // 評論數量
    private Integer commentCount;

    public LessonMainInfo getLesson() {
        return lesson;
    }

    public void setLesson(LessonMainInfo lesson) {
        this.lesson = lesson;
    }

    public boolean isIsFavor() {
        return isFavor;
    }

    public void setIsFavor(boolean isFavor) {
        this.isFavor = isFavor;
    }

    public List<DtxTag> getTags() {
        return tags;
    }

    public void setTags(List<DtxTag> tags) {
        this.tags = tags;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

}
