package org.jetbrains.plugins.template

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.codehaus.jettison.json.JSONTokener
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

data class Dependency(
        val dependencies: List<Package>,
        val dependencyCount: Int,
)

data class Package(
        val advisories: List<Advisory>
)

data class Advisory(
        val aliases: List<Any>,
        val description: String,
        val source: String,
        val sourceID: String,
        val sourceURL: String,
        val title: String,
        val referenceURLs: List<String>,
        val severity: String,
        val gitHubSeverity: String,
        val disclosedAt: Int,
        val observedAt: Int,
)

class Checker {

    fun Check(packageName: String, version: String): List<Advisory> {
        var packageNameNew = URLEncoder.encode(packageName, "utf-8")
        var result = ""
        var url = URL("https://deps.dev/_/s/go/p/$packageNameNew/v/$version/dependencies")
        try {
            result = url.readText()
            var gson = Gson()
            var dependency = gson.fromJson(result, Dependency::class.java)
            if (dependency.dependencies.isNotEmpty()) {
                for (d in dependency.dependencies) {
                    if (d.advisories.isNotEmpty()) {
                        return d.advisories
                    }
                }
            }
        } catch (e: Exception) {
            println(e)
        }
        return emptyList()
    }
}