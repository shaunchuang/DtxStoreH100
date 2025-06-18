package demo.freemarker.dto;

public class LessonManageDTO {
    private Long id;
    private String lessonName;
    private String disease;
    private Integer achievementCount;
    private Integer statCount;
    private Integer distributionCount;
    private Double commentLevel;
    private String status;

    public LessonManageDTO() {
    }

    public LessonManageDTO(Long id, String lessonName, String disease, Integer achievementCount, Integer statCount, Integer distributionCount, Double commentLevel, String status) {
        this.id = id;
        this.lessonName = lessonName;
        this.disease = disease;
        this.achievementCount = achievementCount;
        this.statCount = statCount;
        this.distributionCount = distributionCount;
        this.commentLevel = commentLevel;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Integer getAchievementCount() {
        return achievementCount;
    }

    public void setAchievementCount(Integer achievementCount) {
        this.achievementCount = achievementCount;
    }

    public Integer getStatCount() {
        return statCount;
    }

    public void setStatCount(Integer statCount) {
        this.statCount = statCount;
    }

    public Integer getDistributionCount() {
        return distributionCount;
    }

    public void setDistributionCount(Integer distributionCount) {
        this.distributionCount = distributionCount;
    }

    public Double getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Double commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
