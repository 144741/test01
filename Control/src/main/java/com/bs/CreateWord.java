package com.bs;

import com.aspose.words.*;
import com.aspose.words.Shape;
import com.bs.Service.AsposeService;
import com.bs.Service.AsposeUtils;
import com.sun.istack.internal.FragmentContentHandler;
import jdk.nashorn.internal.ir.SplitNode;
import jdk.nashorn.internal.ir.annotations.Ignore;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.poi.ss.formula.functions.Value;
import sun.awt.SunHints;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CreateWord extends AsposeUtils{

    public static void main(String[] args) {
        try {//载入模板
            Document doc = new Document("C:\\Users\\86178\\Desktop\\test.docx");
            DocumentBuilder builder = new DocumentBuilder(doc);
            doc.getPageCount();

            System.out.println(doc.getPageCount());//1
            System.out.println(doc.getPageColor());//System.out.println(builder.getFont().getSize());



            //System.out.println(doc.getNodeType());
            //添加复选框（其中有书签的）
            FormField formField = builder.insertCheckBox("san", false, 12);

//            builder.insertHtml("超链接",false);
            //添加超链接
            builder.insertHyperlink("超链接","san",true);
            builder.insertHtml("<p><font color='red'>&nbsp;&nbsp; \"+ paragraphs +\"</font></p>");
            builder.startBookmark("sa");

//            builder.moveToBookmark("san",true,false);
            FormField formField1 = builder.insertCheckBox("sa", false, 12);

//            System.out.println(doc.getPageInfo(1));
           builder.write("shisi ");//填入内容，
            String str;
            str = builder.insertHyperlink("加","三",true).getFieldCode();
            System.out.println(str);//System.out.println(builder.getFont().getSize());
            //builder.moveToSection(0);

            Field field3 = builder.insertField("12","22");
            builder.moveToField(field3,true);

            builder.insertTextInput("添加input",3,"1qw","三个",14);
            //当前段落全部颜色改为蓝色
//            builder.getFont().getShading().setBackgroundPatternColor(Color.blue);
            //单元格背景颜色改为蓝色
            //builder.getCellFormat().getShading().setBackgroundPatternColor(Color.blue);
            //字体改为蓝色
            builder.getFont().setColor(Color.green);
            builder.getFont().getPosition();
            //doc.getRange().replace()
            System.out.println(builder.getFont().getPosition());//0.0


            Paragraph s1  = builder.getCurrentParagraph();
            System.out.println(s1);//System.out.println(builder.getFont().getSize());
//绘制一个7*22的表格 并填充内容
            for (int i = 0; i < 22; i++) {
                for (int j = 0; j < 7; j++) {
                   builder.insertCell();
                    builder.write("lian接");//填入内容
                      //builder.insertHyperlink("加入超链接","san",true);
                }
                builder.endRow();
            }

            //builder.getFont().setColor();
            builder.insertHyperlink("加入超链接","san",true);

            //插入一个复选框，书签为三
              FormField formField2 = builder.insertCheckBox("三", true, 12);
            //移动到sa书签的后方位置
            builder.moveToBookmark("三",false,true);
            builder.moveTo(formField1);
            //在第三个标签前添加
            BookmarkCollection bms = doc.getRange().getBookmarks();
            String bookmark = "三";
            Bookmark bm = bms.get(bookmark);
            builder.moveToBookmark(bookmark,false,true);
            builder.writeln();
            builder.write("加入书签");
            Node insertAfterNode = builder.getCurrentParagraph().getPreviousSibling();

           // System.out.println(insertAfterNode);//书签前一个位置；


           // System.out.println(builder.getCurrentNode());
            //builder.moveTo(formField);
           // System.out.println(builder.getCurrentNode());
//            builder.moveTo(formField2);
           // System.out.println(builder.getCurrentNode());

//            builder.startBookmark("sa");
           // builder.moveToDocumentStart();
            builder.getCurrentNode();//FieldStart 0.0.0.0
            //System.out.println(builder.getCurrentNode());//FieldStart 0.0.0.0
            //tianjiadanyuange
            AsposeUtils.setCell(builder,"text");
            AsposeUtils.setTitle(builder,"shiwu");
            AsposeUtils.setCell(builder,"1351234");
            AsposeUtils.setTitle(builder,"121424");
            builder.moveToDocumentEnd();
            builder.getFont().getPosition();//0.0;


            //添加一个标签名为fi。。。 设置颜色为lan，最后返回文本颜色
            builder.getFont().setColor(Color.blue);
            builder.startBookmark("firstBookmark");
            builder.writeln("My first bookmark");
            builder.endBookmark("firstBookmark");
            System.out.println(builder.getFont().getColor());//java.awt.Color[r=0,g=0,b=255]
            builder.getFont().setColor(Color.black);



            builder.writeln("新的颜色");


            //添加一个超链接；
            //先设置颜色；
            builder.getFont().setColor(Color.blue);
            builder.insertHyperlink("图片1","firstBookmark",true);
            builder.getFont().setColor(Color.black);
            builder.writeln();

            builder.writeln("链接以添加");





            FindReplaceOptions options = new FindReplaceOptions();
            doc.getRange().getText().indexOf("三个");
            System.out.println(builder.getCurrentParagraph());//java.awt.Color[r=0,g=0,b=255]
            System.out.println(doc.getRange().getText().indexOf("个"));//127

            builder.insertHyperlink("指定下标插入超链接","图1",true).getResult().indexOf("三");
//            builder.insertHyperlink("指定下标插入超链接","图1",true).getDisplayResult().indexOf("三个");
//            builder.insertHyperlink("指定下标插入超链接","图1",true).getFieldCode().indexOf("三");
//
//            builder.insertHyperlink("指定下标插入超链接","图1",true).getFieldCode(true);
//            builder.insertHyperlink("指定下标插入超链接","图1",true).getStart().getRange().getText().indexOf("下");
            Node sss = builder.getCurrentParagraph().getPreviousSibling();
            builder.writeln("从上图的结构和对应的Word文档，我们可以看到大概的DOM中相关对象的结构，有了这些基本概念，就可以很流程的操作Word文档了。Document, Section, Paragraph, Table, Shape, Run 以及图中的其他椭圆形的都是Aspose.Words对象，这些对象具有树形的层级结构，图中的注释同样说明这些文档对象树中的对象具有多个属性。\n" +
                    "Aspose.Words中的DOM有以下特点：\n" +
                    "\n" +
                    "1.所有的节点(node)类最终都继承于Node类，它是Aspose.Words DOM的基本类型。\n" +
                    "\n" +
                    "2.节点可以包含(嵌套)其他节点，例如Section和Paragraph都继承自CompositeNode类，而CompositeNode类来源与Node类。\n" +
                    "\n" +
                    "2.2 Node类型正则表达式\n" +
                    "正则表达式 - 教程\n" +
                    "正则表达式 - 简介\n" +
                    "正则表达式 - 语法\n" +
                    "正则表达式 - 修饰符\n" +
                    "正则表达式 - 元字符\n" +
                    "正则表达式 - 运算符优先级\n" +
                    "正则表达式 - 匹配规则\n" +
                    "正则表达式 - 示例\n" +
                    "正则表达式 - 在线工具\n" +
                    " 正则表达式 – 简介正则表达式 - 修饰符 \n");
            int n = sss.getNodeType();
            int m = NodeType.PARAGRAPH;

            System.out.println(sss);//Paragraph 10.0.0
            System.out.println(m);//8
            System.out.println(n);//8
            int l =doc.getSections().getCount();
            System.out.println(l);//1
            System.out.println(doc.getText().indexOf("三"));//103



            builder.moveToSection(0);
            System.out.println(doc.getRange().getText().indexOf("入超链接") + 1);//1099 + 1
            builder.writeln("dibiao");

            //建表
//            builder.moveToDocumentEnd();
//            Table table = builder.startTable();
//            builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
            builder.insertCell().isFirstCell();
//            NodeCollection allTables = doc.GetChildNodes(NodeType.Table, true);
            NodeCollection allTables = doc.getChildNodes(NodeType.TABLE, true);
            System.out.println(allTables);//com.aspose.words.NodeCollection@4b0b64cc
//            Table table = allTables[0];//
            //移动到第一个表，0000；
            builder.moveToCell(0,0,0,0);
            builder.writeln("yidong");
            System.out.println(builder.getCurrentNode());//Run 0.0.0.0.4.0.0
            System.out.println(doc.getRange().getText().indexOf("yidong"));//137
            builder.moveToCell(0,2,1,0);
            builder.writeln("表1移动位置");
            System.out.println(builder.getCurrentNode());//Run 0.1.1.2.4.0.0
            System.out.println(doc.getRange().getText().indexOf("表1"));//236

            builder.moveToCell(1,0,0,0);
            builder.writeln("表二移动位置");
            System.out.println(builder.getCurrentNode());//Run 0.1.0.0.7.0.0
            System.out.println(doc.getRange().getText().indexOf("表二"));//1148
            //来到第一节第一段
            builder.moveTo(doc.getFirstSection().getBody().getFirstParagraph().getRuns().get(0));
            builder.writeln("这是");
            builder.moveTo(doc.getFirstSection().getBody().getFirstParagraph().getChildNodes().get(0));
            builder.writeln("啥");

           //这里是分段的位置，
            builder.isAtEndOfParagraph();
            System.out.println(builder.getCurrentParagraph());//Paragraph 1.0.0
            builder.writeln("第二段");
            System.out.println(builder.getCurrentParagraph());//Paragraph 2.0.0
            builder.isAtStartOfParagraph();

            builder.moveToParagraph(177,0);
            System.out.println(builder.getCurrentParagraph());//Paragraph 15.0.0
            //builder.moveToParagraph(163,0);
            builder.getFont().setColor(Color.blue);
            builder.writeln("charact");
            builder.getFont().setColor(Color.black);
            System.out.println(builder.getCurrentParagraph());//Paragraph 16.0.0
//            Shape shape = new Shape(doc,ShapeType.TEXT_BOX);
//            shape.setTitle("we");
//            shape.setBehindText(true);
//            builder.insertNode(shape);
            builder.moveTo(doc.getFirstSection().getBody().getFirstParagraph().getRuns().get(0));
            Run.nodeTypeToString(5).length();
            System.out.println(Run.nodeTypeToString(5).length());//5
            System.out.println(doc.getLastSection().getBody().getFirstParagraph().getRuns().get(0));//Run 0.0.0.0
            System.out.println(doc.getSections().get(0).getBody().getParagraphs().get(10).getRuns().get(0));//Run 1.12.0.0
            builder.moveTo(doc.getSections().get(0).getBody().getParagraphs().get(10).getRuns().get(0));
            builder.writeln("节点");
            int a = doc.getText().indexOf("节点",2);
            System.out.println(a);//1212
            int b = builder.getDocument().getText().indexOf("节点");
            System.out.println(b);//1212


            String c = builder.getDocument().getRange().getText().replaceAll("节点","几点");
            System.out.println(c);
            String d = doc.getRange().getText().replaceAll("节点","jidian");
            System.out.println(d);
            int t =doc.getChildNodes().getCount();
            System.out.println(t);//1

            Table table2 = builder.startTable();
            builder.insertCell().isFirstCell();
            builder.write("表2");
            builder.insertCell();
            builder.write("表2测试");
            builder.endTable();
            //拿到所有表
            NodeCollection f = doc.getChildNodes(NodeType.TABLE, true);
            //获得index目标的表；
            Table table = (Table)doc.getChild(NodeType.TABLE, 2, true);
            builder.insertCell();
            builder.write("esssss");
            builder.write("esssss");




            builder.moveTo(doc.getFirstSection().getBody().getTables().get(0));
            builder.getFont().setColor(Color.red);
            builder.write("2表");
            builder.getFont().setColor(Color.black);
            System.out.println(builder.getCurrentNode());//Null
            //TextColumnCollection textColumnCollection =
            System.out.println(builder.getFont().getName());//等线
            System.out.println(builder.getFont().getPosition());//0.0
         System.out.println(builder.getFont().getSize());//10.5
         //FrameFormat frameFormat = new FrameFormat("essss");

         System.out.println("找坐标");

         //builder.moveTo();
         doc.getFirstSection().getPageSetup().getMultiplePages();
         System.out.println(doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(0));


         builder.writeln("找页码" );

         builder.writeln("yemei" );

         FindReplaceOptions options1 = new FindReplaceOptions();
         options1.setReplacingCallback(new ReplaceEvaluatorFindAndHighlight());

         System.out.println(options1.getReplacingCallback());
         options1.setDirection(FindReplaceDirection.BACKWARD) ;
         Regex regex = new Regex("接");
         doc.getRange().replace(String.valueOf(regex)," ",options1);

         //Regex regex = new Regex("your document", RegexOptions.IgnoreCase);
         //doc.getRange().replace(String.valueOf(regex), " shi斯" , options1);
            System.out.println(table);



            AsposeUtils.findString(builder,"1");




//            System.out.println(AsposeUtils.setCell(builder,"text"));
           builder.endTable();
            doc.save("C:\\Users\\86178\\Desktop\\test2.docx");//保存文件
        } catch (Exception e) {
            e.printStackTrace();
        }


        }

 private static class ReplaceEvaluatorFindAndHighlight implements IReplacingCallback {

  /// <summary>
  /// This method is called by the Aspose.Words find and replace engine for each match.
  /// This method highlights the match string, even if it spans multiple runs.
  /// </summary>

  public int replacing(ReplacingArgs e) throws Exception {
   // This is a Run node that contains either the beginning or the complete match.
   //   这里返回值为 整个匹配项的 第一个Node.
   Node currentNode = e.getMatchNode();
   System.out.println(currentNode);

   // The first (and may be the only) run can contain text before the match,
   // in this case it is necessary to split the run.
   //    作为匹配的第一个Run, 其中可能只有一部分属于我们匹配的. 所以我们需要将其拆开为两个.
   //    所以我们需要将该Run拆成两个Run(分别包含匹配项部分和非匹配项部分).
   //    这里 splitRun 返回值为 包含匹配部分的Run .
   if (e.getMatchOffset() > 0) {
    currentNode = splitRun((Run) currentNode, e.getMatchOffset());
   }

   // This array is used to store all nodes of the match for further highlighting.
   // 这个runs 数组里保存的将是所有将要进行高亮处理的Node.而且都是精确匹配的,不会出现只有部分内容匹配的Node.
   List<Node> runs = new ArrayList<Node>();

   // Find all runs that contain parts of the match string.
   // .NET中的实现:  int remainingLength = e.Match.Value.Length;
   //  end()返回匹配到的子字符串的后一个字符在字符串中的索引位置.
   //   TODO  int remainingLength = e.getMatch().end(); 会导致 查找的字符串及其后面的部分被高亮.
   //     整个匹配项的总长度, 这里的匹配项指代的是完全匹配项(即不包含前后可能的长度[出现这个长度的原因参见本类顶部的注释])
   //     例如我们意图匹配[a       ], 可能最终Aspose给我们的是 xx[a       ]yy, 这里的长度是[a       ]的长度, 而不是后者
   int remainingLength = e.getMatch().group().length();

   // 如果进行迭代的Run还包含在到达本次匹配的所有.
   while ((remainingLength > 0) && (currentNode != null)
           && (currentNode.getText().length() <= remainingLength)) {
    runs.add(currentNode);

    // 扣掉第一个匹配Run中真正的匹配部分长度之后的长度
    remainingLength = remainingLength - currentNode.getText().length();

    // Select the next Run node.
    // Have to loop because there could be other nodes such as BookmarkStart etc.
    //   迭代出下一个Run,推动匹配工作往下执行
    do {
     currentNode = currentNode.getNextSibling();
    } while ((currentNode != null) && (currentNode.getNodeType() != NodeType.RUN));
   }

   // split the last run that contains the match if there is any text left.
   //   上面的循环之后,可能出现这样一种情况: 循环之后的下一个Run里含有部分匹配项的内容,这样我们就需要将其分离出来.
   if ((currentNode != null) && (remainingLength > 0)) {
    splitRun((Run) currentNode, remainingLength);
    // 这里之所有可以直接添加,是因为在上面的splitRun里将currentNode里的内容进行了赋值
    runs.add(currentNode);
   }

   // Now highlight all runs in the sequence.
   //  然后我们就可以放心得进行高亮处理了, 引入runs里存放的都是完全匹配项.
   for (Node run : runs) {
    final Run currentRun = (Run) run;
    currentRun.getFont().setHighlightColor(Color.RED);
   }

   // Signal to the replace engine to do nothing because we have already done all what we wanted.
   return ReplaceAction.SKIP;
  }
 }


  private static Run splitRun(Run run, int position) {
   Run afterRun = (Run) run.deepClone(true);
   String a = run.getText().substring(position);
   afterRun.setText(a);

   String s = run.getText().substring(0, position);
   run.setText(s);
   run.getParentNode().insertAfter(afterRun, run);
   return afterRun;
  }
}


