package com.revisandocodigo.commands;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import com.revisandocodigo.constants.MasterPageToolPortletKeys;
import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {
                "jakarta.portlet.name=" + MasterPageToolPortletKeys.MASTERPAGETOOL, // Deve ser o ID do seu portlet
                "mvc.command.name=/apply_master_page"
        },
        service = MVCActionCommand.class
)
public class ApplyMasterPageMVCActionCommand extends BaseMVCActionCommand {

    private static final Log _log = LogFactoryUtil.getLog(ApplyMasterPageMVCActionCommand.class);

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        // Pega o ID do Master Page selecionado no JSP
        String masterPageERC = ParamUtil.getString(actionRequest, "masterLayoutEntryId");

        // Pega os PLIDs (IDs únicos das páginas) selecionados
        long[] layoutPlids = ParamUtil.getLongValues(actionRequest, "layoutPlids");

        if (masterPageERC.isBlank()) {
            _log.warn("Nenhum Master Page ou Página foi selecionado.");
            return;
        }

        int successCount = 0;

        for (long plid : layoutPlids) {
            try {
                // Recupera a página
                Layout layout = _layoutLocalService.getLayout(plid);

                // Define o novo Master Page para esta página
                layout.setMasterLayoutPageTemplateEntryERC(masterPageERC);

                // Persiste a alteração
                _layoutLocalService.updateLayout(layout);
                successCount++;

            } catch (Exception e) {
                SessionErrors.add(actionRequest, "bulk-master-page-erro-negocio");
                _log.error("Erro ao atualizar o layout com PLID: " + plid, e);
            }
        }
        SessionMessages.add(actionRequest, "bulk-master-page-sucesso");
        _log.info("Processo concluído. " + successCount + " páginas foram atualizadas com sucesso.");
    }

    @Reference
    private LayoutLocalService _layoutLocalService;

}
