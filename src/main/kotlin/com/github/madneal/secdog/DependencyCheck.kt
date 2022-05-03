package com.github.madneal.secdog

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import java.time.Instant
import java.time.LocalDateTime


class DependencyCheck : DumbAwareAction() {

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
        var psiFileFactory = PsiFileFactory.getInstance(project)
        var curTime = Instant.now()
        val createFileFromText = psiFileFactory.createFileFromText("sca-$curTime.md", result)
        var directory = modFile.containingDirectory
        directory.add(createFileFromText)
}
}