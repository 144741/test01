package com.bs.Service;

import com.aspose.words.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @ClassName:AsposeUtils
 * @Description: aspose.words操作文档工具类
 * @Date:2019年1月11日

 *
 */
public class AsposeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsposeUtils.class);
    private static boolean AsposeLicense = false;
//    static{
//        try {
//            //license.xml
//            InputStream is = AsposeUtils.class.getClassLoader().getResourceAsStream("license.xml");
//            new License().setLicense(is);
//            AsposeLicense = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * 验证License
     * @return boolean
     */
    private static void getLicense() {
        if (!AsposeLicense) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            LOGGER.info("**********验证失败,会产生水印***********");
        }
        LOGGER.info("************验证成功,已去除默认水印***********");
    }
    /**
     * 保存pdf
     * @param path保存目录
     * @param doc原文档
     */
    public static void savePdf(String path,Document doc){
        String format = "pdf";
        save(path,doc,SaveFormat.PDF,format);
    }
    /**
     * 保存doc
     * @param path保存目录
     * @param doc原文档
     */
    public static void saveDoc(String path,Document doc){
        String format = "doc";
        save(path,doc,SaveFormat.DOC,format);
    }
    public static void saveDocx(String path,Document doc){
        String format = "docx";
        save(path,doc,SaveFormat.DOCX,format);
    }
    /**
     * 保存各种格式的文档
     * @param path保存地址
     * @param doc保存文件
     * @param saveFormat保存格式
     */
    private static void save(String path,Document doc,int saveFormat,String format){
        getLicense();
        try {
            String os = System.getProperty("os.name");
            if(os.toLowerCase().indexOf("linux") >= 0){
                LOGGER.info("************当前使用*****linux**"+ os +"*********");
                //设置一个字体目录
                FontSettings.getDefaultInstance().setFontsFolder("/usr/share/fonts/", false);
            }
            doc.save(path, saveFormat);
        } catch (Exception e) {
            LOGGER.info("************保存文档（格式为"+format+"）出现异常***********");
            e.printStackTrace();
        }
    }
    /**
     * 制作报表总标题
     * @param builder
     * @param title
     */
    public static void setTitle(DocumentBuilder builder,String title){
        try {
            //设置字体格式
            builder.insertHtml("<p style='text-align:center'><font face='宋体' size='30' color='black'>"+ title +"</font></p>");
        } catch (Exception e) {
            LOGGER.info("************制作标题"+title+"出现异常***********");
            e.printStackTrace();
        }
    }
    /**
     * 制作一级标题
     * @param builder
     * @param title
     */
    private static void setTitle1(DocumentBuilder builder,String title1){
        try {
            builder.insertHtml("<h1 style='text-align:left;font-family:Simsun;'>"+ title1 +"</h1>");
        } catch (Exception e) {
            LOGGER.info("************制作一级标题"+title1+"出现异常***********");
            e.printStackTrace();
        }
    }
    /**
     * 制作二级标题
     * @param builder
     * @param title
     */
    private static void setTitle2(DocumentBuilder builder,String title2){
        try {
            builder.insertHtml("<h2 style='text-align:left;font-family:Simsun;'>"+ title2 +"</h2>");
        } catch (Exception e) {
            LOGGER.info("************制作二级标题"+title2+"出现异常***********");
            e.printStackTrace();
        }
    }
    /**
     * 制作三级标题
     * @param builder
     * @param title
     */
    private static void setTitle3(DocumentBuilder builder,String title3){
        try {
            builder.insertHtml("<h3 style='text-align:left;font-family:Simsun;'>"+ title3 +"</h3>");
        } catch (Exception e) {
            LOGGER.info("************制作三级标题"+title3+"出现异常***********");
            e.printStackTrace();
        }
    }
    /**
     * 区别各级标题
     * @param builder
     * @param title
     * @param level
     */
//    public static void setTitleS(DocumentBuilder builder,String title,String level){
//        switch (level) {
//            case "1":
//                setTitle1(builder, title);
//                break;
//            case "2":
//                setTitle2(builder, title);
//                break;
//            case "3":
//                setTitle3(builder, title);
//                break;
//            default:
//                break;
//        }
//    }
    /**
     * 制作报表段落
     * @param builder
     * @param pragraph
     */
    public static void setParagraph(DocumentBuilder builder,String pragraph){
        try {
            //设置字体格式
            builder.insertHtml("<p><font face='宋体'>&nbsp;&nbsp; "+ pragraph +"</font></p>");
        } catch (Exception e) {
            LOGGER.info("************制作段落"+pragraph+"出现异常***********");
            e.printStackTrace();
        }
    }
    /**
     * 制作一个单元格并追加数据,单元格不合并
     * @param builder
     * @param width 设置单元格宽度
     * @param text
     */
    public static void setCell(DocumentBuilder builder,Double width,String text){
        builder.insertCell();
        if(width==null) width = 3d;
        builder.getCellFormat().setWidth(width);
        builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
        builder.write(text);
    }
    /**
     * 插入单元格，不设置宽度,单元格不合并
     * @param builder
     * @param text
     */
    public static void setCell(DocumentBuilder builder,String text){
        builder.insertCell();
        builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        builder.write(text);
    }
    public static void setCell(DocumentBuilder builder,Long text){
        builder.insertCell();
        builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        if(text == null){
            builder.write("");
        }else{
            builder.write(text.toString());
        }
    }
    public static void setCell(DocumentBuilder builder,Double text){
        builder.insertCell();
        builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        if(text == null){
            builder.write("");
        }else{
            builder.write(text.toString());
        }
    }
    /**
     * 垂直合并单元格的第一格
     * @param builder
     * @param text
     */
    public static void setStartVerticalMerge(DocumentBuilder builder,String text){
        builder.insertCell();
        builder.getCellFormat().setVerticalMerge(CellMerge.FIRST);
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        builder.write(text);
    }
    /**
     * 垂直合并单元格的后面格
     * @param builder
     * @param text
     */
    public static void setThenVerticalMerge(DocumentBuilder builder){
        builder.insertCell();
        // This cell is vertically merged to the cell above and should be empty.
        builder.getCellFormat().setVerticalMerge(CellMerge.PREVIOUS);
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
    }
    /**
     * 水平合并单元格的第一格
     * @param builder
     * @param text
     */
    public static void setStartHorizontalMerge(DocumentBuilder builder,String text){
        builder.insertCell();
        builder.getCellFormat().setHorizontalMerge(CellMerge.FIRST);
        builder.write(text);
    }
    /**
     * 水平合并单元格的后面格
     * @param builder
     * @param text
     */
    public static void setThenHorizontalMerge(DocumentBuilder builder){
        builder.insertCell();
        // This cell is vertically merged to the cell above and should be empty.
        builder.getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);
    }
    /**
     * 制作表格数据行
     * @param builder
     */
    public static void setRow(DocumentBuilder builder){
        try {
            builder.getRowFormat().setHeadingFormat(true);
            builder.getRowFormat().getBorders().setLineStyle(LineStyle.SINGLE);
            builder.getRowFormat().setHeightRule(HeightRule.AUTO);
            builder.getRowFormat().setAllowBreakAcrossPages(true);
        } catch (Exception e) {
            LOGGER.info("************制作表格数据行出现异常***********");
            e.printStackTrace();
        }
    }
    /**
     * 制作表格
     * @param builder
     * @throws Exception
     */
    public static Table setTable(DocumentBuilder builder){
        builder.moveToDocumentEnd();
        Table table = builder.startTable();
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        return table;
    }







    public static DocumentBuilder findString(DocumentBuilder builder, String str){

        try {
            builder.moveToDocumentStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder;

    }
}