package com.github.madneal.secdog

import com.google.gson.Gson
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

    fun Check(packageName: String, version: String): String {
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
                        return convertToMarkDown(d.advisories, "$packageName  $version")
                    }
                }
            }
        } catch (e: Exception) {
            println(e)
        }
        return ""
    }

    fun convertToMarkDown(ads: List<Advisory>, packageName: String): String {
        var result = StringBuilder().append("# $packageName\n")
        for (ad in ads) {
            val aliases = ad.aliases.joinToString(",")
            val refs = ad.referenceURLs.map { it -> "* $it " }.joinToString("\n")
            var adString = """
## ${ad.title}
### Overview
Source: ${ad.source}

ID: ${ad.sourceID}

Aliases: $aliases

### Description
${ad.description}

### Impact
Severity: ${ad.severity.replace("UNKNOWN", "NO RATING")}

### References:
$refs

"""
            result.append(adString)
        }
        return result.toString()
    }
}