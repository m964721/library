package com.app.networklibrary.xml;

import com.app.toolboxlibrary.LogUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class XmlParser {
	private String application = "";
	private String resultCode = "", resultMsg = "";
	private HashMap<String, String> resultHashMap = new HashMap<String, String>();

	public String getApplication() {
		return application;
	}

	public String getResultCode() {
		return this.resultCode;
	}

	public String getResultMsg() {
		return this.resultMsg;
	}

	public HashMap<String, String> getHashMap() {
		return resultHashMap;
	}

	public void parseResultXML(String resultStr) throws Exception {

		InputStream inStream = new ByteArrayInputStream(resultStr.getBytes("UTF-8"));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document dom = builder.parse(inStream);
		Element root = (Element) dom.getDocumentElement();
		parse(root);
		LogUtil.printInfo("parse success");
	}

	/*
    * @param element 将要进行遍历的节点
    */
	private void parse(Element element) {
		if (null == element) {
			return;
		}
		NodeList nodelist = element.getChildNodes();
		int size = nodelist.getLength();
		for (int i = 0; i < size; i++) {
			// 获取特定位置的node
			Node element2 = (Node) nodelist.item(i);
			/* getNodeName获取tagName，例如<book>thinking in android</book>这个Element的getNodeName返回book
			 * getNodeType返回当前节点的确切类型，如Element、Attr、Text等
			 * getNodeValue 返回节点内容，如果当前为Text节点，则返回文本内容；否则会返回null
			 * getTextContent 返回当前节点以及其子代节点的文本字符串，这些字符串会拼成一个字符串给用户返回。例如
			 * 对<book><name>thinking in android</name><price>12.23</price></book>调用此方法，则会返回“thinking in android12.23”
			 */
			String tagName = element2.getNodeName();

			if (tagName.equals("respCode")) {
				resultCode = element2.getTextContent();
				LogUtil.printInfo("respCode=" + resultCode);
			} else if (tagName.equals("respDesc")) {
				resultMsg = element2.getTextContent();
				LogUtil.printInfo("respDesc=" + resultMsg);
			} else {
				resultHashMap.put(tagName, element2.getTextContent());
				//LogUtil.printInfo("key="+tagName+",value="+element2.getTextContent());
			}
		}

		Document dc =  element.getOwnerDocument();
		Element em = dc.getDocumentElement();
		NamedNodeMap ss = em.getAttributes();
		for (int l = 0 ; l < ss.getLength(); l++){
			Node item = ss.item(l);
			String tagName = item.getNodeName();
			if ("application".equals(tagName)) {
				application = item.getTextContent();
				break;
			}
		}
	}

	public String getResponseFromHashMap(String tagName) {

		Set set = resultHashMap.entrySet();
		Iterator it = resultHashMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			// entry.getKey() 返回与此项对应的键
			// entry.getValue() 返回与此项对应的值
			if (tagName.equals(entry.getKey())) {
				return (String) entry.getValue();
			}
		}

		return null;
	}
}