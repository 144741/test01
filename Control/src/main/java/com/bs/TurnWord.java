package com.bs;

import com.aspose.words.*;
import com.aspose.words.Shape;
import javafx.scene.control.SelectionMode;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.poi.ss.usermodel.Hyperlink;

import javax.xml.soap.Text;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.nio.channels.SelectionKey;
import java.util.regex.Pattern;

public class TurnWord {
    public static void main(String[] args) throws Exception {
        Document doc = new Document("C:\\Users\\86178\\Desktop\\aaa.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);
        LayoutCollector layoutCollector = new LayoutCollector(doc);


        builder.startTable();
        builder.insertCell();
        builder.insertCell();
        builder.write("最chu的表");
        builder.endTable();

        NodeCollection runs = doc.getChildNodes(NodeType.PARAGRAPH, true);
        System.out.println(runs.get(20));
        System.out.println(runs.getCount());//duan
        builder.moveTo(runs.get(20));
        builder.write("20");
        builder.startTable();

        builder.insertCell();
        builder.insertCell();
        builder.write("1表");
        builder.endTable();
        for (int i = 0; i < runs.getCount(); i++) {
            Node r = runs.get(i);
            int numPage = layoutCollector.getStartPageIndex(r);//当前页；

        }

        builder.startTable();
        builder.insertCell();
        builder.write("sdddddddddd");
        builder.endTable();

        //获取所有表
        NodeCollection f = doc.getChildNodes(NodeType.TABLE, true);
//        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
//        builder.moveTo(table);
//        builder.startBookmark("表1");
//        builder.endBookmark("表1");
//        Table table1 = (Table)doc.getChild(NodeType.TABLE, 1, true);
//        builder.moveTo(table1);
//
//        builder.startBookmark("表2");
//        builder.endBookmark("表2");
//        Table table2 = (Table)doc.getChild(NodeType.TABLE, 2, true);
//        builder.moveTo(table2);
//        builder.startBookmark("表3");
//        builder.endBookmark("表3");

        Node[] tables = f.toArray();
        for (int i = 0; i < tables.length; i++) {
            Table table = (Table) tables[i];
            builder.moveTo(table);
            builder.startBookmark("表" + i);
            builder.endBookmark("表" + i);
        }


        NodeCollection shapeCollection = doc.getChildNodes(NodeType.SHAPE, true);
        Node[] shapes = shapeCollection.toArray();

        //如果图片存在于文档中

        for (int index = 0; index < shapes.length; index++) {
            Shape shape = (Shape) shapes[index];

            builder.moveTo(shape);
            builder.startBookmark("图" + index);
            builder.endBookmark("图" + index);


        }


//        doc.getRange().replace(String.valueOf(builder.insertHyperlink("表1","表1",true)),"表1");
        doc.getRange().getText().matches("表1");
        System.out.println(doc.getRange().getText().matches("表1"));//false
        doc.getText().matches("表1");
        System.out.println(doc.getText().matches("表1"));//false
        doc.getRange().getText().charAt(94);
        doc.getRange().getText().charAt(94);


        //获取Run节点用来插入书签或超链接；
        NodeCollection text = doc.getChildNodes(NodeType.RUN, true);
        Node[] texts = text.toArray();
        System.out.println(texts.length);//521
        builder.moveTo(texts[52]);
        builder.startBookmark("11");
        builder.endBookmark("11");
        builder.moveToBookmark("表1", true, false);
        builder.getCurrentNode().getParentNode().indexOf(texts[52]);
        System.out.println(builder.getCurrentNode().getRange().getText().matches("表1"));//false
        doc.getFirstSection().getBody().getParagraphs().getCount();
        doc.getFirstSection().getBody().getParagraphs().get(1);
        System.out.println(doc.getFirstSection().getBody().getParagraphs().getCount());//196
        System.out.println(doc.getFirstSection().getBody().getChildNodes().getCount());//199
        System.out.println(doc.getFirstSection().getBody().getParagraphs().get(3));//Paragraph 4.0.0
        System.out.println(doc.getFirstSection().getBody().getChildNodes().get(3));//Paragraph 3.0.0
        Range text3 = doc.getRange();
        System.out.println(text3.getFields().get(1));
        String text1 = doc.getRange().getText();
        text1.length();
        System.out.println(text1.length());


        FindReplaceOptions findReplaceOptions = new FindReplaceOptions();
        Field replace = builder.insertHyperlink("11", "表0", true);
        builder.moveToField(replace, true);
        builder.write("eeeeeeeeee");
        doc.getRange().replace("day1", "11", findReplaceOptions);
        NodeList fields = doc.selectNodes("\u4e0a\u5348");
        System.out.println(fields.get(0));
        System.out.println(text1.toLowerCase().length());
        builder.write("zhe ");
        Regex regex = new Regex("asa");


        NodeCollection hl = doc.getChildNodes(NodeType.DOCUMENT, true);
        Node[] hls = hl.toArray();
        System.out.println(hls.length);


        doc.getRange().replace("11", "11", findReplaceOptions);
        FieldHyperlink select = new FieldHyperlink();
        String s = "Find";


        System.out.println(doc.getRange().getText().charAt(92));


        builder.moveToBookmark("qqq", true, false);
        System.out.println(doc.getRange().getText().indexOf("上午"));//85


        builder.insertHyperlink("biao1", "表1", true);
        builder.insertHyperlink("biao2", "表2", true);
        builder.insertHyperlink("biao3", "表3", true);
        FindReplaceOptions options = new FindReplaceOptions();
//        Regex regex = new Regex("上午");
//        doc.getRange().replace(String.valueOf(regex),"上午",options);
//        System.out.println(doc.getRange().replace(String.valueOf(regex),"上午",options));//0
        options.getDirection();
        options.getReplacingCallback();
        options.getApplyFont().getPosition();
        System.out.println(options.getApplyFont().getPosition());//0.0
        System.out.println(options.getApplyFont().getSize());//10.0
        System.out.println(options.getApplyFont().getColor());//java.awt.Color[r=0,g=0,b=0]
        System.out.println(options.getApplyFont().getName());//Times New Roman
        System.out.println(options.getApplyFont().getBold());//false
        System.out.println(options.getApplyFont().getTextEffect());//0
        System.out.println(options.getApplyFont().getShading());//com.aspose.words.Shading@0
        System.out.println(options.getApplyFont().getBorder());
        System.out.println(options.getApplyFont().getUnderline());
        System.out.println(options.getApplyFont().getAllCaps());
        System.out.println(options.getApplyFont().getAutoColor());
        System.out.println(options.getApplyFont().getBidi());
        System.out.println(options.getApplyFont().getBoldBi());
        System.out.println(options.getApplyFont().getComplexScript());
        System.out.println(options.getApplyFont().getDoubleStrikeThrough());
        System.out.println(options.getApplyFont().getEmboss());
        System.out.println(options.getApplyFont().getEmphasisMark());
        System.out.println(options.getApplyFont().getEngrave());
        System.out.println(options.getApplyFont().getHighlightColor());
        System.out.println(options.getApplyFont().getItalic());
        System.out.println(options.getApplyFont().getKerning());
        //System.out.println(options.getApplyFont().getLineSpacing());
        System.out.println(options.getApplyFont().getLocaleId());
        System.out.println(options.getApplyFont().getLocaleIdBi());
        System.out.println(options.getApplyFont().getLocaleIdFarEast());
        //System.out.println(options.getApplyFont().getLineSpacing());
        System.out.println(options.getApplyFont().getNameAscii());
        System.out.println(options.getApplyFont().getName());
        System.out.println(options.getApplyFont().getNameBi());
        System.out.println(options.getApplyFont().getNameFarEast());
        System.out.println(options.getApplyFont().getNameOther());
        System.out.println(options.getApplyFont().getNoProofing());
        System.out.println(options.getApplyFont().getOutline());
        System.out.println(options.getApplyFont().getScaling());
        System.out.println(options.getApplyFont().getShadow());
        System.out.println(options.getApplyFont().getSizeBi());
        System.out.println(options.getApplyFont().getSmallCaps());
        System.out.println(options.getApplyFont().getSnapToGrid());
        System.out.println(options.getApplyFont().getSpacing());
        System.out.println(options.getApplyFont().getStrikeThrough());
        //System.out.println(options.getApplyFont().getStyle());
        //System.out.println(options.getApplyFont().getStyleIdentifier());
        //System.out.println(options.getApplyFont().getStyleName());
        System.out.println(options.getApplyFont().getSubscript());
        System.out.println(options.getApplyFont().getSuperscript());
        System.out.println(options.getApplyFont().getUnderlineColor());
        System.out.println(options.getFindWholeWordsOnly());

        //builder.moveTo();
        builder.getFont().setColor(Color.blue);
        builder.startBookmark("firstBookmark");
        builder.writeln("My first bookmark");
        builder.endBookmark("firstBookmark");
        builder.getFont().setColor(Color.black);
        builder.writeln("测试1");


        FindReplaceOptions options0 = new FindReplaceOptions();
        options0.setReplacingCallback(new ReplaceWithHtmlEvaluator(options0));
        doc.getRange().replace(Pattern.compile("上午"), "图1", options0);

        doc.save("C:\\Users\\86178\\Desktop\\bbb.docx");//保存文件
    }


    static class ReplaceWithHtmlEvaluator implements IReplacingCallback {


        ReplaceWithHtmlEvaluator(final FindReplaceOptions options0) {
            FindReplaceOptions mOptions = options0;
        }

        public int replacing(ReplacingArgs e) throws Exception {
            DocumentBuilder builder = new DocumentBuilder((Document) e.getMatchNode().getDocument());
            builder.moveTo(e.getMatchNode());

            // Replace '<CustomerName>' text with a red bold name
            //builder.insertHtml("<b><font color='red'>James Bond, </font></b>");
            builder.getFont().setColor(Color.blue);
            builder.insertHyperlink("图1","图1",true);
            e.setReplacement("图一");
            builder.getFont().setColor(Color.black);

            return ReplaceAction.REPLACE;
        }
    }
}
