
package itri.sstc.freemarker.binding.directive; 

import freemarker.template.TemplateDirectiveModel;
 
public abstract class AbstractDirectiveModel implements TemplateDirectiveModel {
    public abstract String getKey();
    
//
//    public int countSqlObjects(JdbcTemplate jdbcTemplate, String countSqlString, Object params[])
//        throws DataAccessException
//    { 
//    	return jdbcTemplate.queryForObject(countSqlString, params, Integer.class);
//    } 
//
//    public List listSqlObjects(JdbcTemplate jdbcTemplate, String querySqlString, Object params[])
//    { 
//        return jdbcTemplate.queryForList(querySqlString, params);
//    }
//
//    public Map getSqlObject(JdbcTemplate jdbcTemplate, String querySqlString, Object params[])
//    { 
//        List rows = jdbcTemplate.queryForList(querySqlString, params);
//        if(rows != null && rows.size() > 0)
//            return (Map)rows.get(0);
//        else
//            return null;
//    }
//
//    public boolean hasSqlObject(JdbcTemplate jdbcTemplate, String querySqlString, Object params[])
//    { 
//        List rows = jdbcTemplate.queryForList(querySqlString, params);
//        return rows != null && rows.size() > 0;
//    }
}
