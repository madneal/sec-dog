package com.github.madneal.secdog

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope


class DependencyCheck : DumbAwareAction() {
    val reportName = "SCA.md"
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PlatformDataKeys.PROJECT) as Project
        var result = ""
        var modFiles = FilenameIndex.getFilesByName(project, "go.mod", GlobalSearchScope.projectScope(project))
        var modFile: PsiFile? = null
        if (modFiles.isNotEmpty()) {
            modFile = modFiles[0]
        } else {
            return
        }
        var checker = Checker()
        var lines = modFile.text.split("\r?\n|\r".toRegex()).toTypedArray()
        for (line in lines) {
            if (line.contains("/") && !line.startsWith("module")) {
                var newLine = line.trimStart()
                var packages = newLine.split("\\s+".toRegex()).toTypedArray()
                if (packages.isNotEmpty()) {
                    result += checker.Check(packages[0], packages[1])
                }
            }
        }
        try {
            var psiFileFactory = PsiFileFactory.getInstance(project)
            val createFileFromText = psiFileFactory.createFileFromText(reportName, result)
            var directory = modFile.containingDirectory
            ApplicationManager.getApplication().runWriteAction() {
                directory.findFile(reportName)?.delete()
                directory.add(createFileFromText)
            }
        } catch (e: Exception) {
            println(e)
        }
    }
}

