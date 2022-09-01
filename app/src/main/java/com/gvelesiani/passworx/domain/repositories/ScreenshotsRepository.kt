package com.gvelesiani.passworx.domain.repositories.local

interface ScreenshotsRepository {
    fun preventTakingScreenshots(prevent: Boolean)
    fun getTakingScreenshotsStatus(): Boolean
}