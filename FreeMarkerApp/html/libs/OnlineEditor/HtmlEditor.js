function HtmlEditor() {
}

HtmlEditor.contentId = 'content';

HtmlEditor.options = {
    basePath: './',
    filterMode: false,
    wellFormatMode: false
};

HtmlEditor.getReadyFunction = function () {
    let script = document.createElement('script');
    script.innerHTML = "KindEditor.ready(function (KEditor) {\n"
            + "api.KEditor = KEditor;\n"
            + "console.info((api.KEditor)? 'OK':'NULL');\n"
            + "api.htmlEditor = KEditor.create('#" + HtmlEditor.contentId + "', " + JSON.stringify(HtmlEditor.options) + ");\n"
            //+ "editor.id = 'html_editor';\n"
            //+ "api.htmlEditor = editor;\n"
            //+ "api.htmlContent = document.getElementById('" + HtmlEditor.contentId + "');\n"
            + "document.getElementById('edit_dialog_shield').style.display = 'none';\n"
            + "});";
    //
    script.id = 'HtmlEditorScript';
    return script;
};

/* 編輯器主模組 */
HtmlEditor.CORE = [
    "/core/core.js",
    "/core/config.js",
    "/core/ajax.js",
    "/core/event.js",
    "/core/html.js",
    "/core/selector.js",
    "/core/node.js",
    "/core/range.js",
    "/core/cmd.js",
    "/core/widget.js",
    "/core/edit.js",
    "/core/toolbar.js",
    "/core/menu.js",
    "/core/colorpicker.js",
    "/core/uploadbutton.js",
    "/core/dialog.js",
    "/core/tabs.js",
    "/core/main.js"
];

/* 插件 */
HtmlEditor.PLUGINS = [
    "/plugins/emoticons/emoticons.js",
    "/plugins/flash/flash.js",
    "/plugins/image/image.js",
    "/plugins/link/link.js",
    "/plugins/media/media.js",
    "/plugins/plainpaste/plainpaste.js",
    "/plugins/table/table.js",
    "/plugins/wordpaste/wordpaste.js",
    "/plugins/filemanager/filemanager.js",
    "/plugins/preview/preview.js",
    "/plugins/code/code.js",
    "/plugins/map/map.js",
    "/plugins/lineheight/lineheight.js"
];

/* 語言包 */
HtmlEditor.LANG = [
    "/lang/en.js",
    "/lang/zh-TW.js"
];

HtmlEditor.getScripts = function () {
    let scripts = new Array();
    for (var i in HtmlEditor.CORE) {
        let script = document.createElement('script');
        script.src = HtmlEditor.location + HtmlEditor.CORE[i];
        scripts.push(script);
    }
    for (var i in HtmlEditor.PLUGINS) {
        let script = document.createElement('script');
        script.src = HtmlEditor.location + HtmlEditor.PLUGINS[i];
        scripts.push(script);
    }
    for (var i in HtmlEditor.LANG) {
        let script = document.createElement('script');
        script.src = HtmlEditor.location + HtmlEditor.LANG[i];
        scripts.push(script);
    }
    return scripts;
};
