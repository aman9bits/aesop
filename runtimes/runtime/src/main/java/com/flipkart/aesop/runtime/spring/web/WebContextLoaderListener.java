/*
 * Copyright 2012-2015, the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flipkart.aesop.runtime.spring.web;

import com.flipkart.aesop.runtime.spring.RuntimeComponentContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;

/**
 * A custom {@link ContextLoaderListener} that uses the Runtime {@link RuntimeComponentContainer#getCommonRuntimeBeansContext()} as the parent ApplicationContext
 * 
 * @author Regunath B
 * @version 1.0, 12/05/2014
 */
public class WebContextLoaderListener extends ContextLoaderListener {

	/**
	 * Overriden template method.  Uses the {@link RuntimeComponentContainer#getCommonRuntimeBeansContext()} as the parent application context
	 * @see org.springframework.web.context.ContextLoader#loadParentContext(javax.servlet.ServletContext)
	 */
	protected ApplicationContext loadParentContext(ServletContext servletContext) {
		return RuntimeComponentContainer.getCommonRuntimeBeansContext();
	}
}
