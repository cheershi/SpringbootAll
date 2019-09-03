package com.flycode.utils;

import com.flycode.entity.Root;
import com.flycode.entity.Template;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XmlUtil {


	/**
	 * XML转换为javabean
	 * @param
	 * @param
	 * @param
	 * @return
	 * @throws JAXBException
	 */
	public static <Root> Root xmlToBean(File file, Root root) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(root.getClass());
		Unmarshaller um = context.createUnmarshaller();
		root = (Root) um.unmarshal(file);
		return root;
	}

	/**
	 * javabean转换为XML
	 * @param
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public static <T> StringWriter beanToXml(File file,Root root) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(root.getClass());
		Marshaller m = context.createMarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(root, sw);
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);//是否格式化
		m.marshal(root, file);
		return sw;
	}
	public static void main(String[] args) throws Exception {
		Root root=new Root();
		root.setRootName("test");
		List list=new ArrayList();
		list.add("code");
		list.add("name");
		beanToXml(new File("D:/root.xml"),root);

	}




}
