<%@ include file="/init.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="com.liferay.layout.page.template.model.LayoutPageTemplateEntry" %>
<%@ page import="com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.model.Layout" %>
<%@ page import="com.liferay.portal.kernel.service.LayoutLocalServiceUtil" %>

<liferay-ui:success
	key="bulk-master-page-sucesso"
	message="master-page-tool.bulk.success"
/>

<liferay-ui:error
	key="bulk-master-page-erro-negocio"
	message="master-page-tool.bulk.business-error"
/>

<liferay-ui:error
	key="java.lang.Exception"
	message="master-page-tool.bulk.unexpected-error"
/>

<portlet:actionURL name="/apply_master_page" var="applyMasterPageURL" />

<%
	List<LayoutPageTemplateEntry> masterPages =
		LayoutPageTemplateEntryLocalServiceUtil.getLayoutPageTemplateEntries(
			themeDisplay.getSiteGroupId());
	List<Layout> pages = LayoutLocalServiceUtil.getLayouts(
		themeDisplay.getSiteGroupId(), false);
%>

<div class="container-fluid container-fluid-max-xl">
	<div class="master-page-bulk-wrapper">

		<h2 class="section-title">
			<liferay-ui:message key="master-page-tool.title" />
		</h2>
		<p class="section-subtitle">
			<liferay-ui:message key="master-page-tool.subtitle" />
		</p>

		<form action="<%= applyMasterPageURL %>" method="post">

			<div class="form-group">
				<label for="masterPages" class="font-weight-bold text-dark">
					<liferay-ui:message key="master-page-tool.select-master-page" />
				</label>
				<div class="input-group">
					<div class="input-group-item">
						<select
							class="form-control form-control-lg"
							id="masterPages"
							name="<portlet:namespace/>masterLayoutEntryId"
							required
						>
							<option value="" disabled selected>
								<liferay-ui:message key="master-page-tool.select-master-page-placeholder" />
							</option>
							<% for (LayoutPageTemplateEntry masterPage : masterPages) { %>
								<option value="<%= masterPage.getExternalReferenceCode() %>">
									<%= masterPage.getName() %>
								</option>
							<% } %>
						</select>
					</div>
				</div>
			</div>

			<div class="form-group mt-5">
				<label class="font-weight-bold text-dark mb-1">
					<liferay-ui:message key="master-page-tool.select-pages" />
				</label>
				<p class="text-muted small mb-3">
					<liferay-ui:message key="master-page-tool.select-pages-help" />
				</p>

				<div class="pages-scroll-container">
					<% for (Layout layoutPage : pages) { %>
						<div class="custom-control custom-checkbox page-checkbox-item">
							<label>
								<input
									class="custom-control-input"
									name="<portlet:namespace/>layoutPlids"
									type="checkbox"
									value="<%= layoutPage.getPlid() %>"
								>

								<span class="custom-control-label">
									<span class="custom-control-label-text">
										<strong><%= layoutPage.getName(themeDisplay.getLocale()) %></strong>
										<span class="text-muted ml-2">(PLID: <%= layoutPage.getPlid() %>)</span>
									</span>
								</span>
							</label>
						</div>
					<% } %>
				</div>
			</div>

			<div class="mt-5 text-right">
				<button
					class="btn btn-primary btn-apply-bulk d-inline-flex align-items-center"
					type="submit"
				>
					<svg class="lexicon-icon lexicon-icon-magic mr-2" focusable="false" role="presentation" viewBox="0 0 512 512">
						<use href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#magic"></use>
					</svg>
					<liferay-ui:message key="master-page-tool.apply-button" />
				</button>
			</div>
		</form>

	</div>
</div>
