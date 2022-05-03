package com.github.madneal.secdog

import com.intellij.psi.PsiFile
import org.apache.commons.lang.StringUtils
import java.util.*

class DependencyParser(val psFile: PsiFile?) {
    fun parseDependecies(): List<String> {
        if (StringUtils.isBlank(psFile!!.text)) {
            return Collections.emptyList()
        }
        var dependencies: List<String> = listOf(psFile.toString())
        return dependencies
    }
}