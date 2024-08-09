package com.xlw.demo.util.xml;

import com.ximpleware.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VTDXMLMessageBodyConver implements MessageConvert {
    public static final Charset CHARSET = Charset.forName("UTF-8");
        public static final String ROOT_ELEMENT_NAME = "Message";
//    public static final String ROOT_ELEMENT_NAME = "BOSEBankData";
    public static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    public static final String TYPE = "type";
    public static final String TYPE_MAP = "map";
    public static final String TYPE_LIST = "list";
    public static final String VALUE = "value";
    public static final String SUFFIX_LIST = "List";
    public static final String SUFFIX_S = "s";

    /**
     * 解码
     *
     * @param b
     * @param begin
     * @param end
     * @param message
     */
    public void decode(byte[] b, int begin, int end, Message message) {
        VTDGen vg = new VTDGen();
        vg.setDoc(b, begin, end);

        try {
            vg.parse(false);
        } catch (EncodingException e) {
            throw new DecoderException(e);
        } catch (EOFException e) {
            throw new DecoderException(e);
        } catch (EntityException e) {
            throw new DecoderException(e);
        } catch (ParseException e) {
            throw new DecoderException(e);
        }

        VTDNav vn = vg.getNav();

        try {
            if (vn.matchElement(ROOT_ELEMENT_NAME)) {
                if (vn.toElement(VTDNav.FIRST_CHILD)) {
                    do {
                        message.setData(vn.toString(vn.getCurrentIndex()), vn2Object(vn));
                    } while (vn.toElement(VTDNav.NEXT_SIBLING));
                }
            }
        } catch (NavException e) {
            throw new DecoderException(e);
        }
    }

    /**
     * 编码
     *
     * @param message
     * @return
     */
    public byte[] encode(Message message) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(ROOT_ELEMENT_NAME);
        Map<String, Object> data = message.getData();
        for (String name : data.keySet()) {
            Object o = data.get(name);
            root.add(object2Element(name, o));
        }

        String xmlString = document.asXML();
        return StringUtil.stringToBytes(xmlString, CHARSET);
    }

    private static Object vn2Object(VTDNav vn) {
        try {
            if (vn.getAttrVal("type") == -1) {
                return vn.toString(vn.getText());
            }
            String type = vn.toString(vn.getAttrVal("type"));
            int index = vn.getCurrentIndex();
            Object o = null;
            if ("map".equalsIgnoreCase(type)) {
                o = vn2Map(vn);
            } else if ("list".equalsIgnoreCase(type)) {
                o = (Object) vn2List(vn);
            }
            if (o != null) {
                vn.recoverNode(index);
                return o;
            }
        } catch (NavException e) {
            throw new DecoderException(e);
        }
        throw new DecoderException("can not convert vn: " + vn);
    }

    private static List<Object> vn2List(VTDNav vn) {
        ArrayList<Object> list = new ArrayList();
        try {
            if (vn.toElement(2)) {
                do {
                    list.add(vn2Object(vn));
                } while (vn.toElement(4));
            }
        } catch (NavException e) {
            throw new DecoderException(e);
        }
        return list;
    }


    private static Map<String, Object> vn2Map(VTDNav vn) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        try {
            if (vn.toElement(VTDNav.FIRST_CHILD)) {
                do {
                    map.put(vn.toString(vn.getCurrentIndex()), vn2Object(vn));
                } while (vn.toElement(VTDNav.NEXT_SIBLING));
            }
        } catch (NavException e) {
            throw new DecoderException(e);
        }
        return map;
    }

    private static Element object2Element(String name, Object o) {
        Element element = DocumentHelper.createElement(name);
        if (o instanceof String) {
            element.addText((String) o);
        } else if (o instanceof Map) {
            element.addAttribute("type", "map");
            Map map = (Map) o;
            for (Object n : map.keySet()) {
                element.add(object2Element(n.toString(), map.get(n)));
            }
        } else if (o instanceof List) {
            element.addAttribute("type", "list");
            List list = (List) o;
            if (list.size() > 0) {
                Object first = list.get(0);
                if (first instanceof String) {
                    String n = "value";
                    for (Object l : list) {
                        if (!(l instanceof String)) {
                            throw new EncoderException("Inconsistent type in a list.");
                        }
                        element.add(object2Element(n, l));
                    }
                } else if (first instanceof Map) {
                    String n = analysisListElementName(name);
                    for (Object l : list) {
                        if (!(l instanceof Map)) {
                            throw new EncoderException("Inconsistent type in a list.");
                        }
                        element.add(object2Element(n, l));
                    }
                } else {
                    throw new EncoderException("Not supported type in a list: " + first);
                }
            }
        } else {
            element.addText(to_string(o));
        }
        return element;
    }

    private static String to_string(Object o) {
        if (o == null)
            return "";
        return o.toString();
    }

    private static String analysisListElementName(String parentName) {
        if (parentName.endsWith("List")) {
            return parentName.substring(0, parentName.length() - "List".length());
        }
        if (parentName.endsWith("s")) {
            return parentName.substring(0, parentName.length() - "s".length());
        }
        return "value";
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        long l = System.currentTimeMillis();

        int count = 1;
        for (int i = 0; i < count; i++) {
            String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<Message>" +
                    "<item1 type=\"map\">" +
                    "<Pin>123456</Pin>" +
                    "<DeviceID>1004</DeviceID>" +
                    "<DeviceSn>000000000093</DeviceSn>" +
                    "<Prn>580477</Prn>" +
                    "<TransCont>123456</TransCont>" +
                    "</item1>" +
                    "</Message>";
//            String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
//                    "<BOSEBankData>" +
//                    "    <opReq>" +
//                    "        <serialNo>111</serialNo>" +
//                    "        <reqTime>22</reqTime>" +
//                    "        <ReqParam>" +
//                    "            <T24Id>T24客户号</T24Id>" +
//                    "            <CustomerNo>CRMS客户号</CustomerNo>" +
//                    "            <AgreementNo>授信协议流水号</AgreementNo>" +
//                    "            <SerRef>交易单据流水号</SerRef>" +
//                    "            <SerTy>放款条件类型</SerTy>" +
//                    "        </ReqParam>" +
//                    "    </opReq>" +
//                    "</BOSEBankData>";

            byte[] ws = str.getBytes("UTF-8");
            VTDXMLMessageBodyConver xml = new VTDXMLMessageBodyConver();
            Message message = new Message();
            xml.decode(ws, 0, ws.length, message);

            System.out.println(message.getData());

            xml.encode(message);
        }
        System.out.println((System.currentTimeMillis() - l) / 10000.0D);
    }
}