package com.gvelesiani.passworx.domain.repositories

interface IntroRepository {
    fun finishIntro()
    fun isIntroFinished(): Boolean
}