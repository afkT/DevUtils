package dev.standard;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import dev.utils.DevFinal;
import dev.utils.common.CollectionUtils;
import dev.utils.common.ColorUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: Color 排序方法
 * @author Ttt
 * <pre>
 *     可用于 colors.xml 颜色排序, 并且统一整个项目 color 规范替换命名等等
 * </pre>
 */
public class ColorSortMain {

    public static void main(String[] args) throws Exception {
        // colors.xml 文件地址
        String xmlPath = "app/src/main/res/values/colors.xml";

        // 解析 colors.xml
        new SAXXml(xmlPath).analysisColorsXml(new SAXXml.DocumentListener() {
            @Override
            public void onEnd(List<ColorUtils.ColorInfo> lists) {
                if (CollectionUtils.isEmpty(lists)) {
                    System.out.println(" list is empty");
                    return;
                }
                // 根据色调排序
                ColorUtils.sortHSB(lists);
                // 生成 XML 文件内容
                String content = Builder.createXML(lists);
                // 覆盖处理
                boolean result = FileUtils.saveFile(xmlPath, StringUtils.getBytes(content));
                // 获取结果
                System.out.println("result: " + result);
            }
        });
    }

    /**
     * detail: XML 创建处理
     * @author Ttt
     */
    static final class Builder {

        // XML 内容
        private static final String XML_CONTENT = "%s\t<color name=\"%s\">%s</color>";

        /**
         * 创建 XML
         * @param lists Color 信息集合
         * @return XML 文件内容
         */
        public static String createXML(final List<ColorUtils.ColorInfo> lists) {
            StringBuilder builder = new StringBuilder();
            builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            builder.append(DevFinal.NEW_LINE_STR);
            builder.append("<resources>");
            // 解析数据
            for (ColorUtils.ColorInfo colorInfo : lists) {
                builder.append(String.format(XML_CONTENT, DevFinal.NEW_LINE_STR, colorInfo.getKey(), colorInfo.getValue()));
            }
            builder.append(DevFinal.NEW_LINE_STR);
            builder.append("</resources>");
            return builder.toString();
        }
    }

    /**
     * detail: SAX 解析 XML 内部类
     * @author Ttt
     */
    static final class SAXXml {

        // colors.xml 文件地址
        private String xmlPath;

        private SAXXml(String xmlPath) {
            this.xmlPath = xmlPath;
        }

        /**
         * 解析 Colors.xml 内容
         * @param listener 监听事件
         * @throws Exception
         */
        public void analysisColorsXml(final DocumentListener listener) throws Exception {
            // 获取 SAXParserFactory 实例
            SAXParserFactory factory = SAXParserFactory.newInstance();
            // 获取 SAXParser 实例
            SAXParser saxParser = factory.newSAXParser();
            // 创建 Handler 对象并进行解析
            saxParser.parse(xmlPath, new SAXDemoHandel(listener));
        }

        /**
         * detail: 解析 Handler
         * @author Ttt
         */
        class SAXDemoHandel extends DefaultHandler {

            private String                     colorKey;
            private String                     colorValue;
            // 解析事件
            private DocumentListener           listener;
            // 解析集合
            private List<ColorUtils.ColorInfo> lists = new ArrayList<>();

            public SAXDemoHandel(DocumentListener listener) {
                this.listener = listener;
            }

            @Override
            public void startDocument() throws SAXException {
                super.startDocument(); // SAX 解析开始
            }

            @Override
            public void endDocument() throws SAXException {
                super.endDocument(); // SAX 解析结束
                // 触发回调
                if (listener != null) {
                    listener.onEnd(lists);
                }
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                super.startElement(uri, localName, qName, attributes);
                if (qName.equals("color")) {
                    this.colorKey = attributes.getValue("name");
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                super.endElement(uri, localName, qName);
                if (qName.equals("color")) {
                    lists.add(new ColorUtils.ColorInfo(colorKey, colorValue));
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                super.characters(ch, start, length);
                String value = new String(ch, start, length).trim();
                if (!value.equals("")) {
                    this.colorValue = value;
                }
            }
        }

        /**
         * detail: Xml Document 解析监听事件
         * @author Ttt
         */
        public interface DocumentListener {

            /**
             * 解析结束触发
             * @param lists 解析集合
             */
            void onEnd(List<ColorUtils.ColorInfo> lists);
        }
    }
}