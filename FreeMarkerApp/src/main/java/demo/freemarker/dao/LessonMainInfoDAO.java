/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.dto.LessonMainDTO;
import demo.freemarker.model.DtxTag;
import demo.freemarker.model.LessonComment;
import demo.freemarker.model.LessonMainInfo;
import demo.freemarker.model.LessonTag;
import demo.freemarker.model.UserFavoriteLesson;
import itri.sstc.framework.core.database.IntIdBaseDAO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Root;

/**
 *
 * @author zhush
 */
public class LessonMainInfoDAO extends IntIdBaseDAO {

    final static LessonMainInfoDAO instance = new LessonMainInfoDAO("DTXSTORE_PU");

    public final static LessonMainInfoDAO getInstance() {
        return instance;
    }

    private LessonMainInfoDAO(String puName) {
        super(puName, LessonMainInfo.class);
    }

    @Override
    public String getTableName() {
        return "lesson_main_info";
    }

    public List<LessonMainDTO> findLessonsWithPagination(List<Integer> tagIds, Integer uMinAge, Integer uMaxAge,
            Double minPrice, Double maxPrice, String sortField, // 第一排序欄位
            String sortDirection, // 第一排序方向
            String sortTwoField, // 第二排序欄位
            String sortTwoOrder, // 第二排序方向
            int offset, int limit, String lessonName, Long userId, Boolean isLibrary) {

        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LessonMainInfo> query = cb.createQuery(LessonMainInfo.class);
        Root<LessonMainInfo> lesson = query.from(LessonMainInfo.class);
        
        // ===== 1. 事先建立共用的 Expression =====
        // 2.1 csrrRate → 數值
        Expression<Integer> csrrValue = cb.<Integer>selectCase()
                .when(cb.equal(lesson.get("csrrRate"), "G"), 0)
                .when(cb.equal(lesson.get("csrrRate"), "P6"), 6)
                .when(cb.equal(lesson.get("csrrRate"), "C12"), 12)
                .when(cb.equal(lesson.get("csrrRate"), "C15"), 15)
                .when(cb.equal(lesson.get("csrrRate"), "R"), 18)
                .otherwise(0);

        // 2.2 minAge 正規化（0→0，否則原值）
        Expression<Integer> minAgeExpr = cb.<Integer>selectCase()
                .when(cb.equal(lesson.get("minAge"), 0), 0)
                .otherwise(lesson.get("minAge"));

        // 2.3 計算 ageMax = max(minAge, csrrValue)
        Expression<Integer> absDiff = cb.abs(cb.diff(minAgeExpr, csrrValue));
        Expression<Number> sumAges = cb.sum(cb.sum(minAgeExpr, csrrValue), absDiff);
        Expression<Integer> ageMaxExpr = cb.quot(sumAges, cb.literal(2)).as(Integer.class);

        // 2.4 favorite flag: CASE WHEN EXISTS(...) THEN 1 ELSE 0
        Subquery<Long> favSubQry = query.subquery(Long.class);
        Root<UserFavoriteLesson> favRoot = favSubQry.from(UserFavoriteLesson.class);
        favSubQry.select(cb.literal(1L))
                .where(
                        cb.equal(favRoot.get("lessonId"), lesson.get("id")),
                        cb.equal(favRoot.get("userId"), userId));
        Expression<Integer> favFlag = cb.<Integer>selectCase()
                .when(cb.exists(favSubQry), 1)
                .otherwise(0);
        // ===== . 建立所有篩選條件 =====
        List<Predicate> predicates = new ArrayList<>();

        // (1) 標籤篩選
        if (tagIds != null && !tagIds.isEmpty()) {
            Subquery<Long> sub = query.subquery(Long.class);
            Root<LessonTag> lt = sub.from(LessonTag.class);
            sub.select(lt.get("lessonId"))
                    .where(lt.get("tagId").in(tagIds));
            predicates.add(lesson.get("id").in(sub));
        }

        // (2) 價格篩選
        if (minPrice != null) {
            predicates.add(cb.or(
                    cb.ge(lesson.get("price"), minPrice),
                    cb.isNull(lesson.get("price")),
                    cb.lt(lesson.get("price"), 0)));
        }
        if (maxPrice != null) {
            predicates.add(cb.or(
                    cb.le(lesson.get("price"), maxPrice),
                    cb.isNull(lesson.get("price")),
                    cb.gt(lesson.get("price"), 3500)));
        }

        // (3) 名稱篩選
        if (lessonName != null && !lessonName.isEmpty()) {
            predicates.add(cb.like(lesson.get("lessonName"), "%" + lessonName + "%"));
        }

        // (4) 我的最愛篩選（library 模式）
        if (Boolean.TRUE.equals(isLibrary)) {
            Subquery<Long> favSub = query.subquery(Long.class);
            Root<UserFavoriteLesson> favSubRoot = favSub.from(UserFavoriteLesson.class);
            favSub.select(favSubRoot.get("lessonId"))
                .where(cb.equal(favSubRoot.get("userId"), userId));
            predicates.add(lesson.get("id").in(favSub));
        }

        // (5) 年齡篩選
        if (uMinAge != null && uMaxAge != null && (uMinAge != 3 || uMaxAge != 65)) {
            predicates.add(cb.and(
                    cb.or(cb.equal(lesson.get("minAge"), 0), cb.le(lesson.get("minAge"), uMaxAge)),
                    cb.or(cb.equal(lesson.get("maxAge"), 0), cb.ge(lesson.get("maxAge"), uMinAge))));
        }

        // (6) 等級 (csrrRate) 篩選
        predicates.add(cb.le(csrrValue, uMaxAge));

        // (7) 狀態篩選：只要 PUBLISHED
        predicates.add(cb.equal(lesson.get("status"), LessonMainInfo.LessonStatus.PUBLISHED));
        // ===== 2. 排序邏輯 =====
        List<Order> orderList = new ArrayList<>();
        // Helper：依欄位名回傳對應的 Order
        BiFunction<String, String, Order> orderFor = (field, dir) -> {
            boolean desc = "DESC".equalsIgnoreCase(dir);
            switch (field.toLowerCase()) {
                case "favorite":
                    return desc ? cb.desc(favFlag) : cb.asc(favFlag);
                case "age":
                    return desc ? cb.desc(ageMaxExpr) : cb.asc(ageMaxExpr);
                case "release":
                    return desc ? cb.desc(lesson.get("releaseDate")) : cb.asc(lesson.get("releaseDate"));
                case "price":
                    return desc ? cb.desc(lesson.get("price")) : cb.asc(lesson.get("price"));                case "rating":
                    // 創建臨時映射來查詢評分
                    // 建立子查詢來獲取教案的平均評分
                    Subquery<Double> ratingSubquery = query.subquery(Double.class);
                    Root<LessonComment> commentRoot = ratingSubquery.from(LessonComment.class);
                    ratingSubquery.select(cb.avg(commentRoot.<Number>get("rating").as(Double.class)))
                            .where(cb.equal(commentRoot.get("lessonId"), lesson.get("id")));
                    // 處理可能為null的情況，如果沒有評分則默認為0
                    Expression<Double> avgRating = cb.coalesce(ratingSubquery, 0.0);
                    return desc ? cb.desc(avgRating) : cb.asc(avgRating);
                default:
                    return desc ? cb.desc(lesson.get(field)) : cb.asc(lesson.get(field));
            }
        };
        // 第一排序
        if (sortField != null && !sortField.isEmpty()) {
            orderList.add(orderFor.apply(sortField, sortDirection));
            // 如果第一欄是 favorite，次排序固定 releaseDate
            if ("favorite".equalsIgnoreCase(sortField)) {
                orderList.add(cb.desc(lesson.get("releaseDate")));
            }
        }
        // 第二排序
        if (sortTwoField != null && !sortTwoField.isEmpty()) {
            orderList.add(orderFor.apply(sortTwoField, sortTwoOrder));
        }
        // 預設
        if (orderList.isEmpty()) {
            orderList.add(cb.asc(lesson.get("id")));
        }

        // ===== 3. 組合 Query & 分頁 =====
        query.select(lesson)
                .where(predicates.toArray(new Predicate[0]))
                .orderBy(orderList);

        List<LessonMainInfo> lessonList = em.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        if (lessonList.isEmpty()) {
            return new ArrayList<>();
        }

        // ===== 4. 後處理：撈標籤、評分、評論數 & 組 DTO =====
        Set<Long> lessonIds = lessonList.stream()
                .map(LessonMainInfo::getId)
                .collect(Collectors.toSet());
        List<Long> lessonIdList = new ArrayList<>(lessonIds);

        // 標籤
        List<LessonTag> allTags = LessonTagDAO.getInstance().listTagByLessonIds(lessonIdList);
        List<Long> tagIdList = allTags.stream()
                .map(LessonTag::getTagId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, DtxTag> tagMap = DtxTagDAO.getInstance().findByIds(tagIdList)
                .stream().collect(Collectors.toMap(DtxTag::getId, t -> t));
        Map<Long, List<DtxTag>> lessonTags = new HashMap<>();
        for (LessonTag lt : allTags) {
            lessonTags.computeIfAbsent(lt.getLessonId(), k -> new ArrayList<>())
                    .add(tagMap.get(lt.getTagId()));
        }

        // 我的最愛
        Set<Long> favIds = UserFavoriteLessonDAO.getInstance()
                .findByUserId(userId).stream()
                .map(UserFavoriteLesson::getLessonId)
                .collect(Collectors.toSet());

        // 評分＆評論數
        Map<Long, Double> avgRatings = LessonCommentDAO.getInstance()
                .getAvgRatingsByLessonIds(lessonIdList);
        Map<Long, Integer> commentCounts = LessonCommentDAO.getInstance()
                .getCommentCountsByLessonIds(lessonIdList);

        // 組 DTO
        List<LessonMainDTO> dtoList = lessonList.stream().map(l -> {
            LessonMainDTO dto = new LessonMainDTO();
            dto.setLesson(l);
            dto.setIsFavor(favIds.contains(l.getId()));
            dto.setTags(lessonTags.getOrDefault(l.getId(), Collections.emptyList()));
            dto.setAvgRating(avgRatings.getOrDefault(l.getId(), 0.0));
            dto.setCommentCount(commentCounts.getOrDefault(l.getId(), 0));
            return dto;
        }).collect(Collectors.toList());

        return dtoList;
    }

    public int countLessonsWithFilters(List<Integer> tagIds, Integer uMinAge, Integer uMaxAge,
            Double minPrice, Double maxPrice, String lessonName, Long userId, Boolean isLibrary) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<LessonMainInfo> lesson = query.from(LessonMainInfo.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(lesson.get("status"), LessonMainInfo.LessonStatus.PUBLISHED));
        // (1) 標籤篩選
        if (tagIds != null && !tagIds.isEmpty()) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<LessonTag> lessonTag = subquery.from(LessonTag.class);
            subquery.select(lessonTag.get("lessonId"))
                    .where(lessonTag.get("tagId").in(tagIds));
            predicates.add(lesson.get("id").in(subquery));
        }
        // (2) 價格篩選（與分頁查詢條件相同）
        if (minPrice != null) {
            predicates.add(cb.or(
                    cb.ge(lesson.get("price"), minPrice),
                    cb.isNull(lesson.get("price")),
                    cb.lt(lesson.get("price"), 0)));
        }
        if (maxPrice != null) {
            predicates.add(cb.or(
                    cb.le(lesson.get("price"), maxPrice),
                    cb.isNull(lesson.get("price")),
                    cb.gt(lesson.get("price"), 3500)));
        }
        // (3) 教案名稱
        if (lessonName != null && !lessonName.isEmpty()) {
            predicates.add(cb.like(lesson.get("lessonName"), "%" + lessonName + "%"));
        }

        if (isLibrary != null && isLibrary) {
            // 建立子查詢：查詢該使用者收藏的教案 ID
            Subquery<Long> favoriteSubquery = query.subquery(Long.class);
            Root<UserFavoriteLesson> favoriteRoot = favoriteSubquery.from(UserFavoriteLesson.class);
            favoriteSubquery.select(favoriteRoot.get("lessonId"))
                    .where(cb.equal(favoriteRoot.get("userId"), userId));
            // 新增條件：僅顯示 lesson.id 在子查詢結果中的教案
            predicates.add(lesson.get("id").in(favoriteSubquery));
        }

        // (4) 年齡交集篩選
        if (uMinAge != null && uMaxAge != null && (uMinAge != 3 || uMaxAge != 65)) {
            predicates.add(cb.and(
                    cb.or(cb.equal(lesson.get("minAge"), 0), cb.le(lesson.get("minAge"), uMaxAge)),
                    cb.or(cb.equal(lesson.get("maxAge"), 0), cb.ge(lesson.get("maxAge"), uMinAge))));
        }
        // (5) 分級 (csrrRate) 篩選
        if (uMaxAge != null && uMaxAge != 65) {
            Expression<Integer> csrrRateExpr = cb.<Integer>selectCase()
                    .when(cb.equal(lesson.get("csrrRate"), "G"), 0)
                    .when(cb.equal(lesson.get("csrrRate"), "P6"), 6)
                    .when(cb.equal(lesson.get("csrrRate"), "C12"), 12)
                    .when(cb.equal(lesson.get("csrrRate"), "C15"), 15)
                    .when(cb.equal(lesson.get("csrrRate"), "R"), 18)
                    .otherwise(0);
            predicates.add(cb.le(csrrRateExpr, uMaxAge));
        }

        query.select(cb.count(lesson)).where(predicates.toArray(new Predicate[0]));
        Long count = em.createQuery(query).getSingleResult();
        return count.intValue();
    }

    public List<LessonMainInfo> findLessonMainInfoByCreator(Long creator) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonMainInfo.listLessonMainInfoByCreator");
            q.setParameter("creator", creator);
            return q.getResultList();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            em.close();
        }
    }
}
