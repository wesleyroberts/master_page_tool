package com.revisandocodigo.portlet;

import com.revisandocodigo.constants.MasterPageToolPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;

/**
 * @author wesleyroberts
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=MasterPageTool",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + MasterPageToolPortletKeys.MASTERPAGETOOL,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class MasterPageToolPortlet extends MVCPortlet {
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        super.doView(renderRequest, renderResponse);
    }
}