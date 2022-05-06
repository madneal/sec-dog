package com.github.madneal.secdog

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CheckerTest {

    @Test
    fun check() {
        var checker = Checker()
        checker.Check("github.com/madneal/sdasdfasdf", "v0.2")
    }
}