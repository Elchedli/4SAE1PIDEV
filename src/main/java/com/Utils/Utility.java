package com.Utils;

import javax.servlet.http.HttpServletRequest;

public class Utility {

	public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }  

}
