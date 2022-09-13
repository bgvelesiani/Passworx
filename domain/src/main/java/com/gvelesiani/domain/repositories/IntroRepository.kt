package com.gvelesiani.domain.repositories

interface IntroRepository {
    fun finishIntro()
    fun isIntroFinished(): Boolean
}