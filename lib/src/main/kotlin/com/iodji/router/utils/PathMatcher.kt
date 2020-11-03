package com.iodji.router.utils;

import java.util.regex.Pattern

class PathMatcher(path: String) {

    companion object {
        private const val PARAM = "([a-zA-Z][a-zA-Z0-9_-]*)"
        private const val PARAM_REGEX = "\\{$PARAM\\}"
        private const val PARAM_VALUE = "([a-zA-Z0-9_#'!+%~,\\-\\.\\@\\$\\:]+)"

        private val PARAM_PATTERN = Pattern.compile(PARAM_REGEX)
    }

    private val pathReplaced = path.replace(PARAM_REGEX.toRegex(), PARAM_VALUE)
    private val regex: Pattern = Pattern.compile("^$pathReplaced\$")
    private val pathParams: List<String> = path.valuesOf(PARAM_PATTERN)

    fun matches(url: String): Boolean {
        return try {
            regex.matcher(url).find()
        } catch (t: Throwable) {
            t.printStackTrace()
            false
        }
    }

    fun parametersValues(url: String): Map<String, String> {
        val paramsMap = mutableMapOf<String, String>()

        val matcher = regex.matcher(url)

        if (matcher.matches()) {
            for (i in 0 until matcher.groupCount()) {
                val value = matcher.group(i + 1)
                val name = pathParams[i]
                if (value != null && "" != value.trim { it <= ' ' }) {
                    paramsMap[name] = value
                }
            }
        }
        return paramsMap
    }

    private fun String.valuesOf(pattern: Pattern): List<String> {
        val paramsNames = mutableListOf<String>()
        val matcher = pattern.matcher(this)

        while (matcher.find()) {
            val value = matcher.group()
            if (!value.isNullOrBlank()) {
                val name = value.substring(1, value.length - 1) //removes the { }
                paramsNames.add(name)
            }
        }
        return paramsNames
    }
}
