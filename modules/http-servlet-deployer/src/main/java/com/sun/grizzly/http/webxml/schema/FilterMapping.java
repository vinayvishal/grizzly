/**
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER. *
 * Copyright 2007-2008 Sun Microsystems, Inc. All rights reserved. *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 *
 */
package com.sun.grizzly.http.webxml.schema;

import java.util.List;


public class FilterMapping {
	public String filterName; 
	public List<String> urlPattern;
    public List<String> servletName;
    public List<String> dispatcher;
    
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public List<String> getUrlPattern() {
		return urlPattern;
	}
	public void setUrlPattern(List<String> urlPattern) {
		this.urlPattern = urlPattern;
	}
	public List<String> getServletName() {
		return servletName;
	}
	public void setServletName(List<String> servletName) {
		this.servletName = servletName;
	}
	public List<String> getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(List<String> dispatcher) {
		this.dispatcher = dispatcher;
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<FilterMapping>").append("\n");
		buffer.append("<filterName>").append(filterName).append("</filterName>").append("\n");
		
		
		if(servletName!=null && servletName.size()>0){
			List<String> list = servletName;
			
			for (String item : list) {
				buffer.append("<servletName>").append(item).append("</servletName>").append("\n");
			}
		} 
		
		if(urlPattern!=null && urlPattern.size()>0){
			List<String> list = urlPattern;
			
			for (String item : list) {
				buffer.append("<urlPattern>").append(item).append("</urlPattern>").append("\n");
			}
		} 
		
		if(dispatcher!=null && dispatcher.size()>0){
			for (String item : dispatcher) {
				buffer.append("<dispatcher>").append(item).append("</dispatcher>").append("\n");
			}
		}
		buffer.append("</FilterMapping>");
		return buffer.toString();
	}
	
}