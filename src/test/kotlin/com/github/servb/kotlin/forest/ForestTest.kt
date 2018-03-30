package com.github.servb.kotlin.forest

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.ShouldSpec

class ForestTests : ShouldSpec() {
    init {
    	var trees = ArrayList<AbstractTree>()
        val tree = Fir(trees)
        trees.add(tree)
        
        "animals" {
            should("match treir children") {
                Squirrel(tree, Gender.MALE).create(tree, Gender.MALE)::class shouldBe Squirrel::class
                Chipmunk(tree, Gender.MALE).create(tree, Gender.MALE)::class shouldBe Chipmunk::class
                Badger(tree, Gender.MALE).create(tree, Gender.MALE)::class shouldBe Badger::class
                FlyingSquirrel(tree, Gender.MALE).create(tree, Gender.MALE)::class shouldBe FlyingSquirrel::class
                Woodpecker(tree, Gender.MALE).create(tree, Gender.MALE)::class shouldBe Woodpecker::class
            }
        }
        
        "trees" {
            // Nothing to test ):
        }
    }
}
