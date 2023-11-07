package com.bs.Service;

import com.aspose.words.*;
import com.bs.CreateWord;

import java.util.Iterator;

public class AsposeService {

    public static Document insertDocumentAfterBookMark(Document mainDoc, Document tobeInserted, String bookmark)
            throws Exception {
        if (mainDoc == null) {
            return null;
        } else if (tobeInserted == null) {
            return mainDoc;
        } else {
            //构建新文档
            DocumentBuilder mainDocBuilder = new DocumentBuilder(mainDoc);
            if (bookmark != null && bookmark.length() > 0) {
                //获取到书签
                BookmarkCollection bms = mainDoc.getRange().getBookmarks();
                Bookmark bm = bms.get(bookmark);
                if (bm != null) {
                    mainDocBuilder.moveToBookmark(bookmark, true, false);
                    mainDocBuilder.writeln();
                    //获取到插入的位置
                    Node insertAfterNode = mainDocBuilder.getCurrentParagraph().getPreviousSibling();
                    insertDocumentAfterNode(insertAfterNode, mainDoc, tobeInserted);
                }
            } else {
                appendDoc(mainDoc, tobeInserted, true);
            }

            return mainDoc;
        }
    }


    public static void insertDocumentAfterNode(Node insertAfterNode, Document mainDoc, Document srcDoc)
            throws Exception {
        if (insertAfterNode.getNodeType() != 8 & insertAfterNode.getNodeType() != 5) {
            throw new Exception("The destination node should be either a paragraph or table.");
        } else {
            CompositeNode dstStory = insertAfterNode.getParentNode();

            while (null != srcDoc.getLastSection().getBody().getLastParagraph()
                    && !srcDoc.getLastSection().getBody().getLastParagraph().hasChildNodes()) {
                srcDoc.getLastSection().getBody().getLastParagraph().remove();
            }

            NodeImporter importer = new NodeImporter(srcDoc, mainDoc, 1);
            int sectCount = srcDoc.getSections().getCount();

            for (int sectIndex = 0; sectIndex < sectCount; ++sectIndex) {
                Section srcSection = srcDoc.getSections().get(sectIndex);
                int nodeCount = srcSection.getBody().getChildNodes().getCount();

                for (int nodeIndex = 0; nodeIndex < nodeCount; ++nodeIndex) {
                    Node srcNode = srcSection.getBody().getChildNodes().get(nodeIndex);
                    Node newNode = importer.importNode(srcNode, true);
                    dstStory.insertAfter(newNode, insertAfterNode);
                    insertAfterNode = newNode;
                }
            }

        }
    }

    public static void appendDoc(Document dstDoc, Document srcDoc, boolean includeSection) throws Exception {
        if (includeSection) {
            Iterator<Section> var3 = srcDoc.getSections().iterator();
            while (var3.hasNext()) {
                Section srcSection = (Section) var3.next();
                Node dstNode = dstDoc.importNode(srcSection, true, 0);
                dstDoc.appendChild(dstNode);
            }
        } else {
            Node node = dstDoc.getLastSection().getBody().getLastParagraph();
            if (node == null) {
                node = new Paragraph(srcDoc);
                dstDoc.getLastSection().getBody().appendChild(node);
            }

            if (node.getNodeType() != 8 & node.getNodeType() != 5) {
                throw new Exception("Use appendDoc(dstDoc, srcDoc, true) instead of appendDoc(dstDoc, srcDoc, false)");
            }

            insertDocumentAfterNode(node, dstDoc, srcDoc);
        }


    }
}
