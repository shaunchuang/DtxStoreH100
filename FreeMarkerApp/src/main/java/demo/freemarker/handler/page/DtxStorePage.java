/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.page;

import demo.freemarker.api.DtxTagAPI;
import demo.freemarker.api.LessonMainInfoAPI;
import demo.freemarker.core.SecurityUtils;
import demo.freemarker.dto.UserRoleDTO;
import demo.freemarker.model.DtxTag;
import demo.freemarker.model.LessonMainInfo;
import itri.sstc.freemarker.core.Model;
import itri.sstc.freemarker.core.RequestData;
import itri.sstc.freemarker.core.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import itri.sstc.framework.core.Config;
import java.util.List;


/**
 *
 * @author zhush
 */
public class DtxStorePage extends RequestHandler {

    @Override
    public String getName() {
        return "dtxstore";
    }

    // http://127.0.0.1:7000/ftl/dtxstore
    @RequestMapping(pattern = "", description = "模組根目錄")
    public String index0(RequestData request, Model model) {
        return "/login";
    }

    // http://127.0.0.1:7000/ftl/dtxstore
    @RequestMapping(pattern = "/", description = "模組根目錄")
    public String index1(RequestData request, Model model) {
        String blogname = Config.get("blogname","測試平台");
        System.out.println("blogname: "+ blogname);
        model.addAttribute("blogname", blogname);
        
        String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
        model.addAttribute("baseUrl", baseUrl);
        
        UserRoleDTO currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser == null) {
            return "redirect:/ftl/dtxstore/query";
        }
        return "redirect:/ftl/dtxstore/dashboard";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/login
    @RequestMapping(pattern = "/login", description = "登入")
    public String login(RequestData request, Model model) {
        String blogname = Config.get("blogname", "測試平台");
        model.addAttribute("blogname", blogname);
        String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
        model.addAttribute("baseUrl", baseUrl);
        
        return "/login";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/register
    @RequestMapping(pattern = "/register", description = "註冊")
    public String register(RequestData request, Model model) {
        String blogname = Config.get("blogname", "測試平台");
        System.out.println("blogname: " + blogname);
        model.addAttribute("blogname", blogname);
        return "/register";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/logout
    @RequestMapping(pattern = "/logout", description = "")
    public String logout(RequestData request, Model model) {
        SecurityUtils.clearCurrentUserAndSession(request);
        return "redirect:/ftl/dtxstore/query";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/home
    @RequestMapping(pattern = "/home", description = "登入時搜尋頁面或Dashboard")
    public String home(RequestData request, Model model) {
        String blogname = Config.get("blogname", "測試平台");
        System.out.println("blogname: " + blogname);
        model.addAttribute("blogname", blogname);
        String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
        model.addAttribute("baseUrl", baseUrl);
        UserRoleDTO currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser == null) {
            return "redirect:/ftl/dtxstore/query";
        }
        return "redirect:/ftl/dtxstore/dashboard";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/dashboard
    @RequestMapping(pattern = "/dashboard", description = "登入時搜尋頁面或Dashboard")
    public String dashboard(RequestData request, Model model) {
        UserRoleDTO currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser == null) {
            return "redirect:/ftl/dtxstore/query";
        }
        String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
        model.addAttribute("baseUrl", baseUrl);
        String blogname = Config.get("blogname", "測試平台");
        System.out.println("blogname: " + blogname);
        model.addAttribute("blogname", blogname);
        model.addAttribute("currentUser", currentUser);
        return "/login/dashboard";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/query
    @RequestMapping(pattern = "/query", description = "搜尋頁面")
    public String query(RequestData request, Model model) {
        UserRoleDTO currentUser = SecurityUtils.getCurrentUser(request);
        
        String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
        model.addAttribute("baseUrl", baseUrl);
        
        String blogname = Config.get("blogname", "測試平台");
        System.out.println("blogname: " + blogname);
        
        model.addAttribute("blogname", blogname);
        if (currentUser == null) {
            List<DtxTag> tags = DtxTagAPI.getInstance().listDtxTagByType(1);
            List<LessonMainInfo> lessons = LessonMainInfoAPI.getInstance().listLessonMainInfo();
            model.addAttribute("indicationNum", tags.size());
            model.addAttribute("lessonNum", lessons.size());
            return "/lessonQuery/query";
        }
        model.addAttribute("currentUser", currentUser);

        return "/lessonQuery/query";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/application/
    @RequestMapping(pattern = "/application", description = "教案介紹頁面")
    public String application(RequestData request, Model model) {
        
        String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
        model.addAttribute("baseUrl", baseUrl);
        
        String blogname = Config.get("blogname", "測試平台");
        System.out.println("blogname: " + blogname);
        model.addAttribute("blogname", blogname);
        UserRoleDTO currentUser = SecurityUtils.getCurrentUser(request);
        System.out.println("request.exchange.getRequestURI(): " + request.exchange.getRequestURI());
        Long lessonId = Long.parseLong(getValueOfKeyInQuery(request.exchange.getRequestURI(), "app"));
        System.out.println("lessonId" + lessonId);
        LessonMainInfo lessonMain = LessonMainInfoAPI.getInstance().getLessonMainInfo(lessonId);
        try {
            // 將 LessonMainInfo 轉換為 JSON 字串
            ObjectMapper objectMapper = new ObjectMapper();
            String lessonMainJson = objectMapper.writeValueAsString(lessonMain);
            model.addAttribute("lessonMain", lessonMainJson);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert lessonMainInfo to JSON", e);
        }
        model.addAttribute("blogname", "DTx 教案市集平台");
        model.addAttribute("currentUser", currentUser);

        return "/lessonQuery/appPage";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/library
    @RequestMapping(pattern = "/library", description = "搜尋頁面")
    public String library(RequestData request, Model model) {
        UserRoleDTO currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser == null) {
            return "redirect:/ftl/dtxstore/query";
        }
        String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
        model.addAttribute("baseUrl", baseUrl);
        
        String blogname = Config.get("blogname", "測試平台");
        System.out.println("blogname: " + blogname);
        model.addAttribute("blogname", blogname);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isLibrary", Boolean.TRUE);

        return "/lessonQuery/query";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/manage/list
    @RequestMapping(pattern = "/manage/list", description = "搜尋頁面")
    public String manageList(RequestData request, Model model) {
        UserRoleDTO currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser == null) {
            return "redirect:/ftl/dtxstore/query";
        }
        String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
        model.addAttribute("baseUrl", baseUrl);
        
        String blogname = Config.get("blogname", "測試平台");
        System.out.println("blogname: " + blogname);
        model.addAttribute("blogname", blogname);
        model.addAttribute("currentUser", currentUser);

        return "/login/lessonManage/lessonList";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/manage/edit?lessonId={id}
    @RequestMapping(pattern = "/manage/edit", description = "教案編輯頁面")
    public String manageLesson(RequestData request, Model model) {
        UserRoleDTO currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser == null) {
            return "redirect:/ftl/dtxstore/query";
        }
        try{

            System.out.println("request.exchange.getRequestURI(): " + request.exchange.getRequestURI());
            Long lessonId = Long.parseLong(getValueOfKeyInQuery(request.exchange.getRequestURI(), "lessonId"));
            if(lessonId != null) {
                String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
                model.addAttribute("baseUrl", baseUrl);
                
                model.addAttribute("blogname", "DTx 教案市集平台");
                model.addAttribute("currentUser", currentUser);
                
                model.addAttribute("lessonId", lessonId);
                // 取得 LessonMainInfo
                LessonMainInfo lessonMain = LessonMainInfoAPI.getInstance().getLessonMainInfo(lessonId);
                
                // 將物件轉為 JSON
                ObjectMapper objectMapper = new ObjectMapper();
                String lessonMainJson = objectMapper.writeValueAsString(lessonMain);
                
                model.addAttribute("lessonMainInfo", lessonMainJson);
                return "/login/lessonManage/lessonUpload";
            } else {
                return "redirect:/ftl/dtxstore/manage/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/ftl/dtxstore/manage/list";
        }
    }

    // http://127.0.0.1:7000/ftl/dtxstore/manage/upload
    @RequestMapping(pattern = "/manage/upload", description = "搜尋頁面")
    public String manageUpload(RequestData request, Model model) {
        UserRoleDTO currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser == null) {
            return "redirect:/ftl/dtxstore/query";
        }
        String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
        model.addAttribute("baseUrl", baseUrl);
        
        String blogname = Config.get("blogname", "測試平台");
        System.out.println("blogname: " + blogname);
        model.addAttribute("blogname", blogname);
        model.addAttribute("currentUser", currentUser);

        return "/login/lessonManage/lessonUpload";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/manage/SteamAppImport
    @RequestMapping(pattern = "/manage/SteamAppImport", description = "搜尋頁面")
    public String steamAppImport(RequestData request, Model model) {
        UserRoleDTO currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser == null) {
            return "redirect:/ftl/dtxstore/query";
        }
        String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
        model.addAttribute("baseUrl", baseUrl);
        
        String blogname = Config.get("blogname", "測試平台");
        System.out.println("blogname: " + blogname);
        model.addAttribute("blogname", blogname);
        model.addAttribute("currentUser", currentUser);

        return "/admin/steamAppImport";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/develop/scratch
    @RequestMapping(pattern = "/develop/scratch", description = "搜尋頁面")
    public String developScratch(RequestData request, Model model) {
        UserRoleDTO currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser == null) {
            return "redirect:/ftl/dtxstore/query";
        }
        String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
        model.addAttribute("baseUrl", baseUrl);
        
        String blogname = Config.get("blogname", "測試平台");
        System.out.println("blogname: " + blogname);
        model.addAttribute("blogname", blogname);
        model.addAttribute("currentUser", currentUser);

        return "/login/lessonDevelop/scratchAppDevelop";
    }

    // http://127.0.0.1:7000/ftl/dtxstore/account/pointhistory
    @RequestMapping(pattern = "/account/pointhistory", description = "積分記錄頁面")
    public String pointsHistory(RequestData request, Model model) {
        UserRoleDTO currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser == null) {
            return "redirect:/ftl/dtxstore/query";
        }
        String baseUrl = Config.get("baseUrl", "http://127.0.0.1:7000");
        model.addAttribute("baseUrl", baseUrl);
        
        String blogname = Config.get("blogname", "測試平台");
        System.out.println("blogname: " + blogname);
        model.addAttribute("blogname", blogname+" - 積分紀錄");
        model.addAttribute("currentUser", currentUser);

        return "/login/myAccount/pointsHistory";
    }


}
