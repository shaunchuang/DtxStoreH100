/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import demo.freemarker.model.PointAllocation;
import itri.sstc.framework.core.database.IntIdBaseDAO;

/**
 *
 * @author zhush
 */
public class PointAllocationDAO extends IntIdBaseDAO {

    final static PointAllocationDAO instance = new PointAllocationDAO("DTXSTORE_PU");

    public final static PointAllocationDAO getInstance() {
        return instance;
    }

    private PointAllocationDAO(String puName) {
        super(puName, PointAllocation.class);
    }

    @Override
    public String getTableName() {
        return "point_allocation";
    }

    public Long getUserAvailablePoints(Long userId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("PointAllocation.getUserAvailablePoints");
            q.setParameter("userId", userId);
            Object result = q.getSingleResult();
            System.out.println("result1： " + result);
            if (result == null) {
                System.out.println("result is null");
                return 0L;
            }
            System.out.println("result2： " + result);
            if (result instanceof BigDecimal) {
                return ((BigDecimal) result).longValue();
            } else if (result instanceof Number) {
                return ((Number) result).longValue();
            } else {
                throw new RuntimeException("Unexpected type for user available points: " + result.getClass());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        } finally {
            em.close();
        }
    }


    public List<PointAllocation> findActivePointAllocations() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("PointAllocation.listActivePointAllocations");
            return q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public void batchUpdate(List<PointAllocation> allocations) {
        // Implementation for batch updating point allocations
        try {
            for (PointAllocation allocation : allocations) {
                // Update each allocation
                edit(allocation);
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
