package org.jetbrains.plugins.template

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
import java.time.LocalDateTime


class DependencyCheck : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
//        var modFile: PsiFile? = e.getData(PlatformDataKeys.PSI_FILE)
        val project = e.getData(PlatformDataKeys.PROJECT) as Project
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
                    var result = checker.Check(packages[0], packages[1])
                    var psiFileFactory = PsiFileFactory.getInstance(project)
                    var curTime = LocalDateTime.now()
                    val createFileFromText = psiFileFactory.createFileFromText("sca.md", result)
                    var directory = modFile.containingDirectory
                    directory.add(createFileFromText)
                }
//                    var ads = checker.Check(packages[0], packages[1])
//                    if (ads.isNotEmpty()) {
//                        for (ad in ads) {
////                            println(ad.description)
////                                var browser = JBCefBrowser()
////                                browser.loadHTML("<html><body><p>dfasdfas</p></body></html>")
////                                var panel = JPanel(BorderLayout())
////                                panel.add(browser.component, BorderLayout.CENTER)
//
//                            var psiFileFactory = PsiFileFactory.getInstance(project)
//                            val createFileFromText = psiFileFactory.createFileFromText("soc.md", "fadsfdsaf")
//                            var directory = modFile.containingDirectory
//                            directory.add(createFileFromText)
//                        }
//                    }
//                }
            }
        }
}
}