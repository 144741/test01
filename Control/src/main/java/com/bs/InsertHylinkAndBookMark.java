package com.bs;

import com.aspose.words.*;

import java.awt.*;
import java.util.regex.Pattern;

public class InsertHylinkAndBookMark {
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

        Node[] tables = f.toArray();
        for (int i = 0; i < tables.length; i++) {
            Table table = (Table) tables[i];
            builder.moveTo(table);
            builder.startBookmark("表" + i);
            builder.endBookmark("表" + i);
        }
        Node[] inserts = f.toArray();
        FindReplaceOptions options = new FindReplaceOptions();
        //options.setReplacingCallback(new InsertHylinkAndBookMark.ReplaceWithHtmlEvaluator(options));
        for (int j = 0;j <inserts.length;j++){

            options.setReplacingCallback(new InsertHylinkAndBookMark.ReplaceWithHtmlEvaluator(options)  );
            doc.getRange().replace(Pattern.compile("表"+ (j + 1)), "", options);
            //options.setReplacingCallback(new InsertHylinkAndBookMark.ReplaceWithHtmlEvaluator(options));

        }
//        NodeCollection shapeCollection = doc.getChildNodes(NodeType.SHAPE, true);
//        Node[] shapes = shapeCollection.toArray();
//
//        //如果图片存在于文档中
//
//        for (int index = 0; index < shapes.length; index++) {  
//            Shape shape = (Shape) shapes[index];
//
//            builder.moveTo(shape);
//            builder.startBookmark("图" + index);
//            builder.endBookmark("图" + index);
//
//
//        }


        doc.save("C:\\Users\\86178\\Desktop\\ccc.docx");//保存文件
    }

    private static class ReplaceWithHtmlEvaluator implements IReplacingCallback {


        ReplaceWithHtmlEvaluator(final FindReplaceOptions options) {
            FindReplaceOptions mOptions = options;
        }

        public int replacing(ReplacingArgs e) throws Exception {
            DocumentBuilder builder = new DocumentBuilder((Document) e.getMatchNode().getDocument());
            builder.moveTo(e.getMatchNode());

            // Replace '<CustomerName>' text with a red bold name
            //builder.insertHtml("<b><font color='red'>James Bond, </font></b>");
            NodeCollection tables =((Document) e.getMatchNode().getDocument()).getChildNodes(NodeType.TABLE, true);
            Node[] is = tables.toArray();
            for(int k = 0; k < is.length;k++) {

                builder.getFont().setColor(Color.blue);
                builder.insertHyperlink("表" + (k + 1), "表"+ k, true);
                e.setReplacement("");
                builder.getFont().setColor(Color.black);
            }

            return ReplaceAction.REPLACE;
        }
    }
}

