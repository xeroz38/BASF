package com.basf.catalog.service;

public interface CategoryAssetsXml {

	public interface Assets
	{
		String ASSET_ROOT_URL = "file:///android_asset/";
		
		String XML_ROOT = "xml/";
		String XML_ROOT_DATA = "xml/data/";
		String XML_ROOT_QUIZ = "xml/quiz/";
		String XML_ROOT_URL = "file:///android_asset/xml/";
		
		String TOC = "toc/toc.html";
		
		String QUIZ = "xml/quiz.xml";

		String AUTOMOTIVE = "xml/categories/automotive.xml";
		String EXTERIOR = "xml/categories/exterior.xml";
		String INTERIOR = "xml/categories/interior.xml";
		String POWERTRAIN_CHASSIS = "xml/categories/powertrain_chassis.xml"; 
		
		String TROUBLESHOOTER = "troubleshooter/troubleshooter.xml";
		String TROUBLESHOOTER_HEADER = "troubleshooter/ts.header.html";
		String DATA_TROUBLESHOOTER = "troubleshooter/data/";
		
		String CAE_LABS_CAPABILITY = "technicalservices/1.html";
		String ULTRA_SIM_BASIC = "technicalservices/2.html";
		String ULTRA_SIM_FLOW = "technicalservices/3.html";
		String MATERIAL_TESTING_LABS = "technicalservices/4.html";
		String PARTS_TESTING_LABS = "technicalservices/5.html";
		String TECHNICAL_SERVICES = "technicalservices/6.html";
		String PRODUCT_DEVELOPMENT_LABS = "technicalservices/7.html";
		
		String ELECTRICAL = "electrical.html";
		String FLAMMABILITY = "flammability.html";
		String MECHANICAL = "mechanical.html";
		String PROCESSING = "processing.html";
		String PROPERTIES = "properties.html";
		String THERMAL = "thermal.html";
		
		String CONTACT_US = "contactus/1.html";
	}
	
	public interface Web
	{
		String TOC_WEB = "http://www.plasticsportal.net/wa/plasticsAP/portal/show/content/mobileApp_TC.html";
	}
}
