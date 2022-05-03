package com.github.madneal.secdog.services

import com.intellij.openapi.project.Project
import com.github.madneal.secdog.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
