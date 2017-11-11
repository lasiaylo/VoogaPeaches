package util.extensions;

import javafx.scene.web.WebView;

public class ExtensionWebView {
	
	public ExtensionWebView() {
		WebView w = new WebView();
		w.getEngine().load("www.google.com");
	}
	
	
	public static void main(String[] args) {
		ExtensionWebView s = new ExtensionWebView(); 
		
	}

}
