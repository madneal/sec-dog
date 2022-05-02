package org.jetbrains.plugins.template

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.psi.PsiFile

class DependencyCheck : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        var modFile: PsiFile? = e.getData(PlatformDataKeys.PSI_FILE)
//        var modFile = FilenameIndex.getFilesByName("go.mod")
        var checker = Checker()
        if (modFile != null) {
//            println(message = modFile.text)
            var lines = modFile.text.split("\r?\n|\r".toRegex()).toTypedArray()
            for (line in lines) {
                if (line.contains("/") && !line.startsWith("module")) {
                    var newLine = line.trimStart()
                    var packages = newLine.split("\\s+".toRegex()).toTypedArray()
                    if (packages.isNotEmpty()) {
                        checker.Check(packages[0], packages[1])
                    }
                }
            }
        } else {
            println("psfile is null")
        }
    }
}