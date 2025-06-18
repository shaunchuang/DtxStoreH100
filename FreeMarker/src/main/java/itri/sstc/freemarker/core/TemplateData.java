package itri.sstc.freemarker.core;

/**
 * 要求資料封裝
 *
 * @author schung
 */
public class TemplateData {

    public final Model model;
    public final String templateFile;

    public TemplateData(Model model, String templateFile) { 
        this.model = model;
        this.templateFile = templateFile;
    }

}
