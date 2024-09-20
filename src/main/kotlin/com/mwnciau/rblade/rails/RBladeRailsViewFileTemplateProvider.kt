package com.mwnciau.rblade.rails

import com.intellij.ide.actions.newclass.CreateWithTemplatesDialogPanel
import com.intellij.openapi.project.Project
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.mwnciau.rblade.RBladeFileType
import org.jetbrains.plugins.ruby.RBundle
import org.jetbrains.plugins.ruby.rails.templates.RailsViewFileTemplateProvider

class RBladeRailsViewFileTemplateProvider : RailsViewFileTemplateProvider {
  override fun getDefaultTemplateLanguageExtensions(): MutableMap<String, String> {
    return mutableMapOf(Pair("RBlade File", "html"))
  }

  override fun getTemplates(project: Project): MutableList<CreateWithTemplatesDialogPanel.TemplatePresentation> {
    if (FileTypeIndex.containsFileOfType(RBladeFileType.INSTANCE, GlobalSearchScope.projectScope(project))) {
      val presentation = CreateWithTemplatesDialogPanel.TemplatePresentation(
        "RBlade",
        RBladeFileType.INSTANCE.getIcon(),
        "RBlade File",
      )

      return mutableListOf(presentation)
    }

    return mutableListOf<CreateWithTemplatesDialogPanel.TemplatePresentation>()
  }
}