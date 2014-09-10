package org.blogdemo.dashboard;

import java.util.LinkedList;

public class DisplayBean {
	
	private LinkedList<String> displayContent = new LinkedList<String>();
	
	public void add(String addedContent){	
		
		if(addedContent.startsWith("ascii: "))
			addedContent = addedContent.substring(7, addedContent.length());
		displayContent.add(addedContent);
	}
	
	
	public String getNext(){		
		String nextContent = displayContent.getFirst();
		displayContent.removeFirst();	
		return nextContent;
	}
	
	public String getAllForWebDisplay(){
		StringBuffer sbf = new StringBuffer();
		
		for(String content: displayContent){
			sbf.append(content+"<BR/>");
		}
		
		displayContent.clear();
		return sbf.toString();
	}
}
